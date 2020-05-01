package club.banyuan.mgt.service;


import club.banyuan.mgt.common.ResponsePages;
import club.banyuan.mgt.dao.entity.UmsAdmin;
import club.banyuan.mgt.dto.*;
import org.springframework.security.core.userdetails.UserDetails;


import java.text.ParseException;
import java.util.List;

public interface AdminService {

    AdminLoginResp login(AdminLoginReq adminLoginReq);

    UserDetails getUserDetailsByToken(String token);

    AdminInfoResp getInfo(long adminId);

    ResponsePages list(Integer pageNum, Integer pageSize, String keyword);

    List<UmsRoleResp> getRoleByAdminId(Long adminId);

    int update(UmsAdmin adminLoginReq, long adminId) throws ParseException;

    Long delete(Long adminId);

    Integer updateRole(Long adminId, List<Long> roleIds);


    UmsAdminResp register(UmsAdmin admin);
}
