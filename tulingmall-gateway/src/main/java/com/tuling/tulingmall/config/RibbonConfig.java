package com.tuling.tulingmall.config;

import com.tuling.tulingmall.component.TulingRestTemplate;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author WeiRun
 * @package com.tuling.tulingmall.config
 * @date 2020/10/15 9:37
 */
@Configuration
public class RibbonConfig {

    @Bean
    public TulingRestTemplate restTemplate(DiscoveryClient discoveryClient) {
        return new TulingRestTemplate(discoveryClient);
    }
}
