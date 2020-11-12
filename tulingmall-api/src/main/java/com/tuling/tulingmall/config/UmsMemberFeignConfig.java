package com.tuling.tulingmall.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author WeiRun
 * @package com.tuling.tulingmall.clientapi.config
 * @date 2020/10/13 18:18
 */
@Configuration
public class UmsMemberFeignConfig {
    
    @Bean
    Logger.Level level() {
        return Logger.Level.FULL;
    }
}
