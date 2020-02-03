package com.hd.common.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Token工具类
 *
 * @author cbl
 */
public class TokenUtils {

    private static final long EXPIRE_TIME = 60 * 60 * 1000;

    private static final String TOKEN_SECRET = "com.hd.private.key";

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static Set<String> tokenSet = new TreeSet<>();

    /**
     * 生成Token
     */
    public Map<String, Object> sign(String userId) {
        Map<String, Object> tokenMap = new HashMap<>();
        try {
            Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            Map<String, Object> header = new HashMap<>(2);
            header.put("Type", "Jwt");
            header.put("alg", "HS256");
            String token = JWT.create().withHeader(header).withClaim("userId", userId).withExpiresAt(date).sign(algorithm);
            tokenMap.put("token", token);
            tokenMap.put("overTime", sdf.format(date));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tokenMap;
    }


    /**
     * 解密
     */
    public static String decode(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            return jwt.getClaim("userId").asString();
        } catch (Exception e) {
            return "";
        }
    }

}
