package com.tuling.tulingmall.config;

import com.tuling.tulingmall.service.PmsProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author WeiRun
 * @package com.tuling.tulingmall.config
 * @date 2020/10/24 14:28
 */
@Slf4j
@Configuration
public class BloomFilterConfig {

    @Autowired
    private PmsProductService productService;

    @Autowired
    private RedisTemplate redisTemplate;
    
//    @Bean
//    public BloomFilterHelper
}
