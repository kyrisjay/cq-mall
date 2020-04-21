package club.banyuan.mgt.service.impl;


import club.banyuan.mgt.common.ResponsePages;
import club.banyuan.mgt.dao.UmsRoleDao;
import club.banyuan.mgt.dao.entity.UmsRole;
import club.banyuan.mgt.dao.entity.UmsRoleExample;
import club.banyuan.mgt.dto.UmsRoleResp;
import club.banyuan.mgt.service.UmsRoleService;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;


public class UmsRoleServiceImpl implements UmsRoleService {

    @Autowired
    private UmsRoleDao umsRoleDao;

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
        return  ResponsePages.setPages(pageInfo,umsRoleRespList);
    }
}
