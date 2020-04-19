package club.banyuan.demo.jwt.service.impl;

import club.banyuan.demo.TokenService;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
public class TokenServiceImpl implements TokenService {

    @Value("${token.secretKey}")
    private String SECRET_KEY;

    @Value("${token.expireSec}")
    private long EXPIRE_SEC;

    @Override
    public String generateToken(String subject) {
        return Jwts.builder()
                .setSubject(subject)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_SEC * 1000))
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    @Override
    public String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_SEC * 1000))
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    /**
     * 从令牌中获取用户名
     *
     * @param token 令牌
     * @return 用户名
     */
    @Override
    public String parseSubject(String token) {
        Claims claims = getBody(token);
        validateBody(claims);
        return claims.getSubject();
    }
    /**
     * 验证令牌
     *
     * @param
     * @return 是否有效
     */
    private void validateBody(Claims claims) {
        if (claims.getExpiration() == null || claims.getExpiration().getTime() <= System
                .currentTimeMillis()) {
            throw new IllegalArgumentException("token过期");
        }
    }

    /**
     * 从令牌判断是否过期,返回true为过期
     *
     * @param token 令牌
     * @return 是否过期
     */
    @Override
    public boolean isExpired(String token) {
        return getExpireSec(token) <= 0;
    }

    /**
     * 从令牌中获取令牌剩余时间，小于等于0即是过期
     *
     * @param token 令牌
     * @return 过期剩余时间
     */
    @Override
    public long getExpireSec(String token) {
        return (getBody(token).getExpiration().getTime() - System.currentTimeMillis()) / 1000;
    }

    /**
     * 刷新令牌
     *
     * @param token 原令牌
     * @return 新令牌
     */
    @Override
    public String refreshExpireDate(String token) {
        Claims claims = getBody(token);
        validateBody(claims);

        return generateToken(claims);
    }

    // TODO
    @Override
    public Map<String, Object> parseMap(String token) {

        return getBody(token);
    }
    /**
     * 从令牌中获取数据声明,过期也强行获取
     *
     * @param token 令牌
     * @return 数据声明
     */
    private Claims getBody(String token) {
        try {
            return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            // 过期也解析body
            return e.getClaims();
        } catch (UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
