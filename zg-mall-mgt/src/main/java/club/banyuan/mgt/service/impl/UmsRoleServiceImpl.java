package club.banyuan.mgt.service.impl;


import club.banyuan.mgt.common.RequestFailException;
import club.banyuan.mgt.common.ResponsePages;
import club.banyuan.mgt.dao.UmsMenuDao;
import club.banyuan.mgt.dao.UmsRoleDao;
import club.banyuan.mgt.dao.entity.UmsMenu;
import club.banyuan.mgt.dao.entity.UmsRole;
import club.banyuan.mgt.dao.entity.UmsRoleExample;
import club.banyuan.mgt.dao.entity.UmsRoleMenuRelation;
import club.banyuan.mgt.dto.UmsMenuTreeNode;
import club.banyuan.mgt.dto.UmsRoleReq;
import club.banyuan.mgt.dto.UmsRoleResp;
import club.banyuan.mgt.service.UmsMenuService;
import club.banyuan.mgt.service.UmsRoleService;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static club.banyuan.mgt.common.FailReason.*;

@Service
public class UmsRoleServiceImpl implements UmsRoleService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UmsRoleServiceImpl.class);

    @Autowired
    private UmsRoleDao umsRoleDao;

    @Autowired
    private UmsMenuDao umsMenuDao;

    @Autowired
    private UmsMenuService umsMenuService;

    @Override
    public ResponsePages<UmsRoleResp> listByPages(Integer pageNum, Integer pageSize, String keyword) {
        PageHelper.startPage(pageNum, pageSize);
        UmsRoleExample umsRoleExample = new UmsRoleExample();
        if (keyword != null) {
            UmsRoleExample.Criteria criteria = umsRoleExample.createCriteria();
            criteria.andNameLike(StrUtil.concat(false, "%", keyword, "%"));
        }

        List<UmsRole> umsRoleList = umsRoleDao.selectByExample(umsRoleExample);
        PageInfo<UmsRole> pageInfo = new PageInfo<>(umsRoleList);

        List<UmsRoleResp> umsRoleRespList = umsRoleList.stream().map(t -> {
            UmsRoleResp umsRoleResp = new UmsRoleResp();
            BeanUtil.copyProperties(t, umsRoleResp);
            return umsRoleResp;
        }).collect(Collectors.toList());
        return ResponsePages.setPages(pageInfo, umsRoleRespList);
    }

    @Transactional
    @Override
    public Long create(UmsRoleReq umsRoleReq) {
        UmsRoleExample umsRoleExample = new UmsRoleExample();
        umsRoleExample.createCriteria().andNameEqualTo(umsRoleReq.getName());

        if (umsRoleDao.countByExample(umsRoleExample) > 0) {
            LOGGER.warn("用户名冲突，{}", umsRoleReq.getName());
            throw new RequestFailException(UMS_ROLE_NAME_DUPLICATE);
        }

        UmsRole umsRole = new UmsRole();
        BeanUtil.copyProperties(umsRoleReq, umsRole);
        umsRole.setCreateTime(new Date());
        umsRole.setSort(0);
        umsRoleDao.insert(umsRole);
        return umsRole.getId();
    }

    @Override
    public Long update(UmsRoleReq umsRoleReq) {

        UmsRoleExample umsRoleExample = new UmsRoleExample();
        umsRoleExample.createCriteria().andNameEqualTo(umsRoleReq.getName())
                .andIdNotEqualTo(umsRoleReq.getId());

        if (umsRoleDao.countByExample(umsRoleExample) > 0) {
            throw new RequestFailException(UMS_ROLE_NAME_DUPLICATE);
        }

        UmsRole umsRole = new UmsRole();
        umsRole.setName(umsRoleReq.getName());
        umsRole.setId(umsRoleReq.getId());
        umsRole.setDescription(umsRoleReq.getDescription());
        umsRole.setStatus(umsRoleReq.getStatus());
        umsRoleDao.updateByPrimaryKeySelective(umsRole);
        return umsRole.getId();
    }

    @Override
    public Long delete(Long ids) {
        if (umsRoleDao.deleteByPrimaryKey(ids) <= 0) {
            throw new RequestFailException(UMS_ADMIN_ROLE_NOT_EXIST);
        }

        return ids;
    }

    @Override
    public List<UmsMenu> listMenu(Long roleId) {
        return umsMenuDao.selectByRoleIds(Collections.singletonList(roleId));
    }

    @Transactional
    @Override
    public void allocMenu(Long roleId, List<Long> menuIds) {
        UmsRoleExample umsRoleExample = new UmsRoleExample();
        umsRoleExample.createCriteria().andIdEqualTo(roleId);
        if (umsRoleDao.countByExample(umsRoleExample) <= 0) {
            throw new RequestFailException(UMS_ADMIN_ROLE_NOT_EXIST);
        }
        List<UmsMenuTreeNode> list = umsMenuService.treeList();

        Set<Long> menuIdSet = new HashSet<>(menuIds);
        for (UmsMenuTreeNode umsMenuTreeNode : list) {
            if (menuIdSet.contains(umsMenuTreeNode.getId())) {
                menuIdSet
                        .removeAll(umsMenuTreeNode.getChildren().stream().map(UmsMenuTreeNode::getId).collect(
                                Collectors.toList()));
                menuIdSet.remove(umsMenuTreeNode.getId());
            }
        }
        if (CollUtil.isNotEmpty(menuIdSet)) {
            throw new RequestFailException(UMS_ROLE_MENU_REL_ILLEGAL);
        }
        umsRoleDao.deleteRoleMenuRelationByRoleId(roleId);
        for (Long one : menuIds) {
            UmsRoleMenuRelation umsRoleMenuRelation = new UmsRoleMenuRelation();
            umsRoleMenuRelation.setMenuId(one);
            umsRoleMenuRelation.setRoleId(roleId);
            umsRoleDao.insertRoleMenuRelation(umsRoleMenuRelation);
        }

        // for (Long menuId : menuIds) {
        //   for (UmsMenuTreeNode umsMenuTreeNode : list) {
        //     if (umsMenuTreeNode.getId().longValue() == menuId) {
        //       break;
        //     } else {
        //       for (UmsMenuTreeNode child : umsMenuTreeNode.getChildren()) {
        //         if (child.getId().longValue() == menuId) {
        //           if (!menuIds.contains(umsMenuTreeNode.getId())) {
        //             throw new RequestFailException(UMS_ROLE_MENU_REL_ILLEGAL);
        //           } else {
        //             break;
        //           }
        //         }
        //       }
        //       throw new RequestFailException(UMS_ROLE_MENU_REL_ILLEGAL);
        //     }
        //   }
        // }
    }

    @Override
    public List<UmsRole> listAll() {

        List<UmsRole>  umsRoleRespList=umsRoleDao.listAll();

        return umsRoleRespList.stream().map(t->{
            UmsRole umsRole=new UmsRole();
            BeanUtil.copyProperties(t,umsRole);
            return umsRole;
        }).collect(Collectors.toList());
    }
}
