package com.tuling.tulingmall.filter;

import com.alibaba.fastjson.JSON;
import com.tuling.tulingmall.common.api.ResultCode;
import com.tuling.tulingmall.common.exception.GateWayException;
import com.tuling.tulingmall.component.TulingRestTemplate;
import com.tuling.tulingmall.properties.NotAuthUrlProperties;
import com.tuling.tulingmall.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.security.PublicKey;
import java.util.Map;

/**
 * @author WeiRun
 * @package com.tuling.tulingmall.filter
 * @date 2020/10/15 10:25
 */
@Slf4j
@Component
@EnableConfigurationProperties(value = NotAuthUrlProperties.class)
public class AuthorizationFilter implements GlobalFilter, Ordered, InitializingBean {

    @Autowired
    private TulingRestTemplate restTemplate;

    /**
     * 请求各个微服务 不需要用户认证的URL
     */
    @Autowired
    private NotAuthUrlProperties notAuthUrlProperties;

    /**
     * jwt的公钥,需要网关启动,远程调用认证中心去获取公钥
     */
    private PublicKey publicKey;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        String currentUrl = exchange.getRequest().getPath().toString();
        if (shouldSkip(currentUrl)) {
            return chain.filter(exchange);
        }

        String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");

        if (StringUtils.isEmpty(authHeader)) {
            log.warn("需要认证的url,请求头为空");
            throw new GateWayException(ResultCode.AUTHORIZATION_HEADER_IS_EMPTY);
        }

        //第三步 校验我们的jwt 若jwt不对或者超时都会抛出异常
        Claims claims = JwtUtils.validateJwtToken(authHeader, publicKey);

        //第四步 把从jwt中解析出来的 用户登陆信息存储到请求头中
        ServerWebExchange webExchange = wrapHeader(exchange, claims);
        
        return chain.filter(webExchange);
    }

    private ServerWebExchange wrapHeader(ServerWebExchange exchange, Claims claims) {

        String loginUserInfo = JSON.toJSONString(claims);

        log.info("jwt的用户信息:{}", loginUserInfo);

        String memberId = claims.get("additionalInfo", Map.class).get("memberId").toString();

        String nickName = claims.get("additionalInfo", Map.class).get("nickName").toString();

        ServerHttpRequest request = exchange.getRequest().mutate()
                .header("username", claims.get("user_name", String.class))
                .header("memberId", memberId)
                .header("nickName", nickName)
                .build();
        
        return exchange.mutate().request(request).build();

    }

    private boolean shouldSkip(String currentUrl) {
        PathMatcher pathMatcher = new AntPathMatcher();
        for (String skiPath : notAuthUrlProperties.getShouldSkipUrls()) {
            if (pathMatcher.match(skiPath, currentUrl)) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        PathMatcher pathMatcher = new AntPathMatcher();
        System.out.println(pathMatcher.match("/sso/*","/sso/login"));
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.publicKey = JwtUtils.getPublicKey(restTemplate);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
