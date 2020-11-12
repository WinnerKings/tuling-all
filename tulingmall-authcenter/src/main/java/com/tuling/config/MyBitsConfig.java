package com.tuling.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author WeiRun
 * @package com.tuling.config
 * @date 2020/10/13 19:30
 */
@Configuration
@MapperScan({"com.tuling.tulingmall.mapper"/*,"com.tuling.tulingmall.portal.dao"*/})
public class MyBitsConfig {
}
