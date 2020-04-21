package club.banyuan.mgt.service;

import club.banyuan.mgt.common.ResponsePages;
import club.banyuan.mgt.dto.UmsRoleResp;

public interface UmsRoleService {

    ResponsePages<UmsRoleResp> listByPages(Integer pageNum, Integer pageSize, String keyword);
}
