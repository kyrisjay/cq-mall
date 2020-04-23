package club.banyuan.mgt.service;

import club.banyuan.mgt.common.ResponsePages;
import club.banyuan.mgt.dao.entity.UmsMenu;
import club.banyuan.mgt.dto.UmsRoleRep;
import club.banyuan.mgt.dto.UmsRoleResp;

import java.util.List;

public interface UmsRoleService {

    ResponsePages<UmsRoleResp> listByPages(Integer pageNum, Integer pageSize, String keyword);

    Long create(UmsRoleRep umsRoleRep);

    Long update(UmsRoleRep umsRoleRep,Long id);

    Long delete(long ids);

    List<UmsMenu> listMenu(Long id);

    void allocMenu(Long roleId, List<Long> menuIds);
}
