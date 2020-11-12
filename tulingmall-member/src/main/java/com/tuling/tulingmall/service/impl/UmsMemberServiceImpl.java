package com.tuling.tulingmall.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tuling.tulingmall.common.api.TokenInfo;
import com.tuling.tulingmall.constant.MDA;
import com.tuling.tulingmall.mapper.UmsMemberMapper;
import com.tuling.tulingmall.model.UmsMember;
import com.tuling.tulingmall.service.UmsMemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * @author WeiRun
 * @package com.tuling.tulingmall.service.impl
 * @date 2020/10/15 18:03
 */
@Service
public class UmsMemberServiceImpl extends ServiceImpl<UmsMemberMapper, UmsMember> implements UmsMemberService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UmsMemberServiceImpl.class);

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public TokenInfo login(String username, String password) {

        ResponseEntity<TokenInfo> response = null;
        try {

            response = restTemplate.exchange(MDA.OAUTH_LOGIN_URL, HttpMethod.POST, wrapOauthTokenRequest(username, password), TokenInfo.class);

            TokenInfo tokenInfo = response.getBody();

            LOGGER.info("根据用户名:{}登陆成功:TokenInfo:{}", username, tokenInfo);

            return tokenInfo;
        } catch (Exception e) {
            LOGGER.error("根据用户名:{}登陆异常:{}", username, e.getMessage());

            return null;
        }
    }

    private HttpEntity<MultiValueMap<String, String>> wrapOauthTokenRequest(String username, String password) {

        //封装oauth2 请求头 clientId clientSecret
        HttpHeaders httpHeaders = wrapHttpHeaders();
        
        MultiValueMap<String,String> reqParams = new LinkedMultiValueMap<>();
        reqParams.add(MDA.USER_NAME,username);
        reqParams.add(MDA.PASS,password);
        reqParams.add(MDA.GRANT_TYPE,MDA.PASS);
        reqParams.add(MDA.SCOPE,MDA.SCOPE_AUTH);

        HttpEntity<MultiValueMap<String,String>> entity = new HttpEntity<>(reqParams, httpHeaders);
        
        return entity;
    }

    private HttpHeaders wrapHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBasicAuth(MDA.CLIENT_ID,MDA.CLIENT_SECRET);
        
        return headers;
    }
}
