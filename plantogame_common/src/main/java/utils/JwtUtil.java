package utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Date;
import java.util.Map;

/**
 * Created by Administrator on 2018/4/11.
 */
@ConfigurationProperties("jwt.config")
public class JwtUtil {

    private String key;

    private long ttl;//一个小时

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public long getTtl() {
        return ttl;
    }

    public void setTtl(long ttl) {
        this.ttl = ttl;
    }

    /**
     * 生成JWT
     *
     * @param id
     * @param map
     * @return
     */
    public String createJWT(String id, Map map, String roles) {
        long nowMillis = System.currentTimeMillis();
        long exp = nowMillis+1000*60;
        JwtBuilder builder = Jwts.builder().setId(id)
                .setClaims(map)
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, key).claim("roles", roles)
                .setExpiration(new Date(exp));
        if (ttl > 0) {
            builder.setExpiration(new Date(nowMillis + ttl));
        }
        return builder.compact();
    }

    /**
     * 解析JWT
     *
     * @param jwtStr
     * @return
     */
    public Claims parseJWT(String jwtStr) {
        return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(jwtStr)
                .getBody();
    }
}
