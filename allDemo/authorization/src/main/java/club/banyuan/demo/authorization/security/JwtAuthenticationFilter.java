package club.banyuan.demo.authorization.security;

import club.banyuan.demo.authorization.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String AUTH_KEY = "Authorization";

    private static final String SCHEMA = "Bearer";

    @Autowired
    private TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse, FilterChain filterChain)
            throws ServletException, IOException {
        String authHead = httpServletRequest.getHeader(AUTH_KEY);
        if (authHead != null && authHead.startsWith(SCHEMA)) {
            // token 认证
            String token = authHead.substring(SCHEMA.length());
            try {
                String subject = tokenService.parseSubject(token);
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        subject, null, null);

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}