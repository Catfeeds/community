package com.tongwii.util;

import com.tongwii.bean.TongWIIResult;
import com.tongwii.constant.ResultConstants;
import com.tongwii.po.UserEntity;
import io.jsonwebtoken.*;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

/**
 * Author: Zeral
 * Date: 2017/6/29
 */
public class TokenUtil {
    public static final String SECRET = "tongwii";
    public static final long EXPIRATE = 72*60*60*1000;

    private static TongWIIResult tongWIIResult = new TongWIIResult();

    /**
     *
     * @param issuer 请求域名
     * @param subject 请求体
     * @return Token
     */
    //Sample method to construct a JWT
    public static String createToken(String issuer, String subject) {

        // 使用HS256加密算法
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        // 当前时间
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        // 使用定制的加密数据
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        // token生成
        JwtBuilder builder = Jwts.builder()
                .setIssuedAt(now)
                .setIssuer(issuer)
                .setSubject(subject)
                .signWith(signatureAlgorithm, signingKey);

        // 添加token有效期
        long expMillis = nowMillis + EXPIRATE;
        Date exp = new Date(expMillis);
        builder.setExpiration(exp);

        //序列化token得到字符串
        return builder.compact();
    }

    /**
     * 检查token是否有效
     * @param token
     * @return
     */
    public static TongWIIResult checkToken(String token) {
        try {
            if (StringUtils.isNotEmpty(token)) {
                Claims claims = Jwts.parser()
                        .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET))
                        .parseClaimsJws(token).getBody();
                //得到body后我们可以从body中获取我们需要的信息
                tongWIIResult.setStatus(ResultConstants.SUCCESS);
                tongWIIResult.setInfo("验证成功");
                tongWIIResult.setData(claims);
            } else {
                tongWIIResult.setStatus(ResultConstants.ERROR);
                tongWIIResult.setInfo("token不存在");
            }
        } catch (SignatureException | MalformedJwtException e) {
            tongWIIResult.setStatus(ResultConstants.ERROR);
            tongWIIResult.setInfo("Token无效");
            // jwt 解析错误
        } catch (ExpiredJwtException e) {
            tongWIIResult.setStatus(ResultConstants.ERROR);
            tongWIIResult.setInfo("Token失效");
            // jwt 已经过期，在设置jwt的时候如果设置了过期时间，这里会自动判断jwt是否已经过期，如果过期则会抛出这个异常，我们可以抓住这个异常并作相关处理。
        }
        return tongWIIResult;
    }


    /**
     * 从token中解析出用户账户和id
     *
     * @param token
     * @return UserEntity 解析出的用户信息，包含账号和id，不存在返回null
     */
    public static UserEntity getUserInfoFormToken(String token) {
        TongWIIResult tongWIIResult = checkToken(token);
        if(tongWIIResult.getStatus() == ResultConstants.SUCCESS) {
            Claims claims = (Claims) tongWIIResult.getData();
            String userInfo = claims.getSubject();
            JSONObject object = JSONObject.fromObject(userInfo);
            UserEntity userEntity = new UserEntity();
            userEntity.setAccount(object.get("account").toString());
            userEntity.setId(object.get("userId").toString());
            return userEntity;
        }
        return null;
    }

}
