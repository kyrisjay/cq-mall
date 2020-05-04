package club.banyuan.mgt.security;


import club.banyuan.mgt.service.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String AUTH_KEY = "Authorization";

    private static final String SCHEMA = "Bearer";

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Autowired
    private AdminService adminService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse, FilterChain filterChain)
            throws ServletException, IOException {
        String authHead = httpServletRequest.getHeader(AUTH_KEY);
        if (authHead != null && authHead.startsWith(SCHEMA)) {
            // token 认证
            String token = authHead.substring(SCHEMA.length());
            try {
                UserDetails userDetails = adminService.getUserDetailsByToken(token);

                if (userDetails != null) {
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
                    authenticationToken.setDetails(userDetails);
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            } catch (Exception e) {
                LOGGER.warn("认证异常", e);
            }
        }

        filterChain.
                doFilter(httpServletRequest, httpServletResponse);
    }
}
