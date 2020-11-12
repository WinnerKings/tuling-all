package com.tuling.tulingmall.utils;

import cn.hutool.core.codec.Base64;
import com.tuling.tulingmall.common.api.ResultCode;
import com.tuling.tulingmall.common.exception.GateWayException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author WeiRun
 * @package com.tuling.tulingmall.utils
 * @date 2020/10/15 9:46
 */
@Slf4j
public class JwtUtils {

    /**
     * 认证服务器许可我们的网关的clientId(需要在oauth_client_details表中配置)
     */
    private static final String CLIENT_ID = "api-gateway";

    /**
     * 认证服务器许可我们的网关的client_secret(需要在oauth_client_details表中配置)
     */
    private static final String CLIENT_SECRET = "smlz";

    /**
     * 认证服务器暴露的获取token_key的地址
     */
    private static final String AUTH_TOKEN_KEY_URL = "http://tulingmall-authcenter/oauth/token_key";

    /**
     * 请求头中的 token的开始
     */
    private static final String AUTH_HEADER = "bearer ";

    public static PublicKey getPublicKey(RestTemplate restTemplate) {
        // 获取公钥
        String tokenKey = getTokenKeyByRemoteCall(restTemplate);
        
        try {
            //把获取的公钥开头和结尾替换掉
            String dealTokenKey = tokenKey.replaceAll("\\-*BEGIN PUBLIC KEY\\-*", "").replaceAll("\\-*END PUBLIC KEY\\-*", "").trim();
            X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(Base64.decode(dealTokenKey));
            KeyFactory rsa = KeyFactory.getInstance("RSA");
            PublicKey publicKey = rsa.generatePublic(pubKeySpec);
            log.info("生成公钥:{}", publicKey);

            return publicKey;
        } catch (Exception e) {
            log.info("生成公钥异常:{}", e.getMessage());

            throw new GateWayException(ResultCode.GET_TOKEN_KEY_ERROR);
        }
    }

    private static String getTokenKeyByRemoteCall(RestTemplate restTemplate) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        httpHeaders.setBasicAuth(CLIENT_ID, CLIENT_SECRET);
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(null, httpHeaders);

        try {
            ResponseEntity<Map> response = restTemplate.exchange(AUTH_TOKEN_KEY_URL, HttpMethod.GET, httpEntity, Map.class);

            String tokenKey = response.getBody().get("value").toString();

            log.info("去认证服务器获取Token_Key:{}", tokenKey);

            return tokenKey;
        } catch (RestClientException e) {
            log.error("远程调用认证服务器获取Token_Key失败:{}", e.getMessage());

            throw new GateWayException(ResultCode.GET_TOKEN_KEY_ERROR);
        }
    }

    public static Claims validateJwtToken(String authHeader, PublicKey publicKey) {
        String token = null;

        try {
            token = StringUtils.applyRelativePath(authHeader, AUTH_HEADER);
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(publicKey).parseClaimsJws(token);
            Claims claims = claimsJws.getBody();
            return claims;
        } catch (Exception e) {

            log.error("校验token异常:{},异常信息:{}", token, e.getMessage());

            throw new GateWayException(ResultCode.JWT_TOKEN_EXPIRE);
        }
    }
}
