package com.tuling.tulingmall.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author WeiRun
 * @package com.tuling.tulingmall.config
 * @date 2020/10/20 21:36
 */
@Configuration
//@EnableTransactionManagement
@MapperScan({"com.tuling.tulingmall.mapper",/*"com.tuling.tulingmall.portal.dao",*/"com.tuling.tulingmall.dao"})
public class MyBatisConfig {
}
