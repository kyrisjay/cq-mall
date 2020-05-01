package club.banyuan.mgt.service.impl;

import club.banyuan.mgt.common.RequestFailException;
import club.banyuan.mgt.common.ResponsePages;
import club.banyuan.mgt.dao.UmsMenuDao;
import club.banyuan.mgt.dao.entity.UmsMenu;
import club.banyuan.mgt.dto.UmsMenuResp;
import club.banyuan.mgt.dto.UmsMenuTreeNode;
import club.banyuan.mgt.service.UmsMenuService;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static club.banyuan.mgt.common.FailReason.*;

@Service
public class UmsMenuServiceImpl implements UmsMenuService {

    @Autowired
    private UmsMenuDao umsMenuDao;


    @Override
    public List<UmsMenuTreeNode> treeList() {
        List<UmsMenu> umsMenus = umsMenuDao.selectAll();
        List<UmsMenuTreeNode> rlt = new ArrayList<>();

        umsMenus.forEach(t -> {
            if (t.getParentId() == 0) {
                UmsMenuTreeNode umsMenuTreeNode = new UmsMenuTreeNode();
                BeanUtil.copyProperties(t, umsMenuTreeNode);
                umsMenuTreeNode.setChildren(new ArrayList<>());
                rlt.add((umsMenuTreeNode));
            }
        });
        rlt.forEach(parent -> {
            umsMenus.forEach(subNode -> {
                if (parent.getId().longValue() == subNode.getParentId()) {
                    UmsMenuTreeNode umsMenuTreeNode = new UmsMenuTreeNode();
                    BeanUtil.copyProperties(subNode, umsMenuTreeNode);
                    parent.getChildren().add(umsMenuTreeNode);
                }
            });
        });

        return rlt;
    }

    @Override
    public ResponsePages list(Integer pageNum, Integer pageSize, Long menuParentId) {
        PageHelper.startPage(pageNum, pageSize);
        List<UmsMenu> umsMenus = umsMenuDao.selectByParentId(menuParentId);
        PageInfo<UmsMenu> pageInfo = new PageInfo<>(umsMenus);
        List<UmsMenuResp> collect = umsMenus.stream().map(t -> {
            UmsMenuResp umsMenuResp = new UmsMenuResp();
            BeanUtil.copyProperties(t, umsMenuResp);
            return umsMenuResp;
        }).collect(Collectors.toList());
        return ResponsePages.setPages(pageInfo, collect);

    }

    @Override
    public UmsMenuResp getMenu(Long menuId) {
        UmsMenu umsMenu = umsMenuDao.selectByPrimaryKey(menuId);
        if (ObjectUtil.isEmpty(umsMenu)) {
            throw new RequestFailException(UMS_ROLE_MENU_REL_ILLEGAL);
        }
        UmsMenuResp umsMenuResp = new UmsMenuResp();
        BeanUtil.copyProperties(umsMenu, umsMenuResp);
        return umsMenuResp;
    }

    @Override
    public long update(Long menuId, UmsMenu umsMenu) {
        UmsMenu umsMenu1 = umsMenuDao.selectByPrimaryKey(menuId);
        if (ObjectUtil.isEmpty(umsMenu1)) {
            throw new RequestFailException(UMS_ROLE_MENU_REL_ILLEGAL);
        }
        umsMenu.setId(menuId);
        return umsMenuDao.updateByPrimaryKey(umsMenu);
    }

    @Override
    public long create(UmsMenu umsMenu) {
        List<UmsMenu> umsMenuList = umsMenuDao.selectAll();
        umsMenuList.forEach(t -> {
            if (t.getTitle().equals(umsMenu.getTitle()) || t.getName().equals(umsMenu.getName())) {
                throw new RequestFailException(UMS_MENU_NAME_DUPLICATE);
            }
        });

        umsMenu.setCreateTime(new Date());
        if (umsMenu.getParentId() == 0) {
            umsMenu.setLevel(0);
        } else {
            umsMenu.setLevel(1);
        }

        return umsMenuDao.insert(umsMenu);
    }

    @Override
    public long delete(Long menuId) {
        UmsMenu umsMenu = new UmsMenu();
        if (ObjectUtil.isEmpty(umsMenu)) {
            throw new RequestFailException(UMS_ADMIN_MENU_NOT_EXIST);
        }

        return umsMenuDao.deleteByPrimaryKey(menuId);
    }

}
