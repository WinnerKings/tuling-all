package com.tuling.component;

import com.tuling.tulingmall.domain.MemberDetails;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.Map;

/**
 * @author WeiRun
 * @package com.tuling.component
 * @date 2020/10/13 20:06
 */
public class TulingTokenEnhancer implements TokenEnhancer {

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken, OAuth2Authentication oAuth2Authentication) {
        MemberDetails memberDetails = (MemberDetails) oAuth2Authentication.getPrincipal();

        Map<String, Object> additionalInfo = new HashMap<>();
        Map<String, Object> retMap = new HashMap<>();

        additionalInfo.put("memberId", memberDetails.getUmsMember().getId());
        additionalInfo.put("nickName", memberDetails.getUmsMember().getNickname());
        additionalInfo.put("integration", memberDetails.getUmsMember().getIntegration());

        retMap.put("additionalInfo", additionalInfo);

        ((DefaultOAuth2AccessToken) oAuth2AccessToken).setAdditionalInformation(retMap);
        return oAuth2AccessToken;
    }
}
