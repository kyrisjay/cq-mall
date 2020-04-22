package club.banyuan.mgt.service.impl;


import club.banyuan.mgt.common.RequestFailException;
import club.banyuan.mgt.common.ResponsePages;
import club.banyuan.mgt.dao.UmsMenuDao;
import club.banyuan.mgt.dao.UmsRoleDao;
import club.banyuan.mgt.dao.entity.UmsMenu;
import club.banyuan.mgt.dao.entity.UmsRole;
import club.banyuan.mgt.dao.entity.UmsRoleExample;
import club.banyuan.mgt.dto.UmsRoleRep;
import club.banyuan.mgt.dto.UmsRoleResp;
import club.banyuan.mgt.service.UmsRoleService;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


import static club.banyuan.mgt.common.FailReason.UMS_ADMIN_ROLE_NOT_EXIST;
import static club.banyuan.mgt.common.FailReason.UMS_ROLE_NAME_DUPLICATE;


public class UmsRoleServiceImpl implements UmsRoleService {

    @Autowired
    private UmsRoleDao umsRoleDao;

    @Autowired
    private UmsMenuDao umsMenuDao;

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

    @Override
    public Long create(UmsRoleRep umsRoleRep) {
        UmsRoleExample umsRoleExample = new UmsRoleExample();
        umsRoleExample.createCriteria().andNameEqualTo((umsRoleRep.getName()))
                .andIdNotEqualTo(umsRoleRep.getId());

        if (umsRoleDao.countByExample(umsRoleExample) > 0) {
            throw new RequestFailException(UMS_ROLE_NAME_DUPLICATE);
        }


        UmsRole umsRole = new UmsRole();
        BeanUtil.copyProperties(umsRoleRep, umsRole);

        umsRoleDao.insert(umsRole);
        return umsRole.getId();
    }

    @Override
    public Long update(UmsRoleRep umsRoleRep,Long id) {
        UmsRole umsRole = new UmsRole();
        umsRole.setName(umsRoleRep.getName());
        umsRole.setId(umsRoleRep.getId());
        umsRole.setDescription(umsRoleRep.getDescription());
        umsRole.setStatus(umsRoleRep.getStatus());
        umsRoleDao.updateByPrimaryKeySelective(umsRole);
        return umsRole.getId();
    }

    @Override
    public Long delete(long ids) {
        if (umsRoleDao.deleteByPrimaryKey(ids)<=0){
         throw  new RequestFailException(UMS_ADMIN_ROLE_NOT_EXIST);
        }
        return ids;
    }

    @Override
    public List<UmsMenu> listMenu(Long id) {
        return umsMenuDao.selectByRoleIds(Collections.singletonList(id));
    }
}
