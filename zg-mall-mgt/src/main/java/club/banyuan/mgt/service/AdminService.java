package club.banyuan.mgt.service;


import club.banyuan.mgt.dto.AdminInfoResp;
import club.banyuan.mgt.dto.AdminLoginReq;
import club.banyuan.mgt.dto.AdminLoginResp;
import org.springframework.security.core.userdetails.UserDetails;

public interface AdminService {

    AdminLoginResp login(AdminLoginReq adminLoginReq);
    UserDetails getUserDetailsByToken(String token);
    AdminInfoResp getInfo(long adminId);
}
