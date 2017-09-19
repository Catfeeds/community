package com.tongwii.util;

import com.tongwii.domain.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: Zeral
 * Date: 2017/6/29
 */
public class TokenUtil {
    /**
     * 签名密钥
     */
    public static final String SECRET = "tongwii";
    /**
     * token有效时间
     */
    public static final Integer EXPIRATE = 72*60*60*1000;
    /**
     * token用户账号key
     */
    private static final String CLAIM_KEY_USERACCOUNT  = "account";
    /**
     * token用户idkey
     */
    private static final String CLAIM_KEY_USERID  = "userId";
    /**
     * token用户请求Host key
     */
    private static final String CLAIM_KEY_HOST  = "host";

    /**
     * 生成过期时间
     *
     * @return
     */
    private static Date generateExpirationDate () {
        Calendar instance = Calendar.getInstance();
        instance.setTime( new Date() );
        instance.add( Calendar.SECOND, EXPIRATE);
        return instance.getTime();
    }

    /**
     * 构建token
     *
     * @param host       the host
     * @param userEntity the user entity
     * @return string string
     */
    public static String generateToken (String host, UserEntity userEntity) {
        Map< String, Object > claims = new HashMap<>();
        claims.put( CLAIM_KEY_USERACCOUNT, userEntity.getAccount());
        claims.put( CLAIM_KEY_USERID, userEntity.getId());
        claims.put( CLAIM_KEY_HOST, host);
        return generateToken( claims );
    }

    /**
     * 构建token
     *
     * @param claims
     * @return
     */
    private static String generateToken ( Map< String, Object > claims ) {
        return Jwts.builder()
                .setClaims( claims )
                .setExpiration( generateExpirationDate() )
                .signWith( SignatureAlgorithm.HS512, SECRET )
                .compact();
    }

    /**
     * 验证token
     *
     * @param token       the token
     * @param userEntity  the user entity
     * @param requestHost the request host
     * @return boolean
     */
    public static Boolean validateToken ( String token, UserEntity userEntity, String requestHost) {
        final String username = getUserAccountFromToken(token);
        final String userId = getUserIdFromToken(token);
        final String host = getHostFromToken(token);
        return ( username.equals( userEntity.getAccount() ) // 用户名校验
                && userId.equals(userEntity.getId())
                && ! isTokenExpired( token )           // token有效期校验
                &&  host.startsWith(requestHost)
        );
    }


    /**
     * token是否过期
     *
     * @param token
     * @return expiration > 当前时间
     */
    private static Boolean isTokenExpired ( String token ) {
        final Date expiration = getExpirationDateFromToken( token );
        return expiration.before( new Date() );
    }


    /**
     * @param token
     * @return
     */
    private static Claims getClaimsFromToken ( String token ) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey( SECRET ) // 签名密钥
                    .parseClaimsJws( token ) // token
                    .getBody();
        } catch ( Exception e ) {
            claims = null;
        }
        return claims;
    }

    /**
     * 根据token得到用户账户
     *
     * @param token
     * @return
     */
    public static String getUserAccountFromToken ( String token ) {
        String userAccount;
        try {
            final Claims claims = getClaimsFromToken( token );
            userAccount = claims.get(CLAIM_KEY_USERACCOUNT).toString();
        } catch ( Exception e ) {
            userAccount = null;
        }
        return userAccount;
    }

    /**
     * 根据token得到用户host
     *
     * @param token
     * @return
     */
    private static String getHostFromToken ( String token ) {
        String host;
        try {
            final Claims claims = getClaimsFromToken( token );
            host = claims.get(CLAIM_KEY_HOST).toString();
        } catch ( Exception e ) {
            host = null;
        }
        return host;
    }

    /**
     * 根据token得到用户id
     *
     * @param token
     * @return
     */
    public static String getUserIdFromToken ( String token ) {
        String userId;
        try {
            final Claims claims = getClaimsFromToken( token );
            userId = claims.get(CLAIM_KEY_USERID).toString();
        } catch ( Exception e ) {
            userId = null;
        }
        return userId;
    }


    /**
     * 得到token过期时间
     *
     * @param token
     * @return
     */
    public static Date getExpirationDateFromToken ( String token ) {
        Date expiration;
        try {
            final Claims claims = getClaimsFromToken( token );
            expiration = claims.getExpiration();
        } catch ( Exception e ) {
            expiration = null;
        }
        return expiration;
    }
}
