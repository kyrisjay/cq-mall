package club.banyuan.mgt.service;

import club.banyuan.mgt.common.ResponsePages;
import club.banyuan.mgt.dao.entity.UmsMenu;


import club.banyuan.mgt.dao.entity.UmsRole;
import club.banyuan.mgt.dto.UmsRoleReq;
import club.banyuan.mgt.dto.UmsRoleResp;

import java.util.List;

public interface UmsRoleService {

    ResponsePages<UmsRoleResp> listByPages(Integer pageNum, Integer pageSize, String keyword);

    Long create(UmsRoleReq umsRoleReq);

    Long update(UmsRoleReq umsRoleReq);

    Long delete(Long ids);

    List<UmsMenu> listMenu(Long id);

    void allocMenu(Long roleId, List<Long> menuIds);

    List<UmsRole> listAll();
}
