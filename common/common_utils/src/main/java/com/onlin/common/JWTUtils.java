package com.onlin.common;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
public class JWTUtils {

    public static final long EXPIRE = 1000 * 60 * 60 * 24;  //令牌有效时间
    public static final String APP_SECRET = "ukc8BDbRigUDaY6pZFfWus2";  //随便写的密钥


    //根据用户信息生成token
    public static String getJwtToker(String id,String nickName){
        String jwtToken = Jwts.builder()
                     .setHeaderParam("typ", "JWT")
                     .setHeaderParam("alg", "HS256")
                     .setSubject("online-school")
                     .setIssuedAt(new Date())
                     .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
                     .claim("id", id)
                     .claim("name", nickName)
                     .signWith(SignatureAlgorithm.HS256, APP_SECRET)
                     .compact();
        return jwtToken;
    }

    //判断 token 是否有效
    public static boolean checkToken(String token){
        if(StringUtils.isEmpty(token)) return false;
        try {
            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(token);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    //判断 token 是否存在与有效
    public static boolean checkToken(HttpServletRequest request){
        try {
            String jwtToken = request.getHeader("token");
            if (StringUtils.isEmpty(jwtToken)) return false;
            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    //根据 token 获取存入的内容
    public static String getMemberIdByJwtToken(HttpServletRequest request){
        String jwtToken = request.getHeader("token");
        if(StringUtils.isEmpty(jwtToken)) return "";
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        Claims claims = claimsJws.getBody();
        return (String)claims.get("id");
    }
}
