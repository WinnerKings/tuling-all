package com.tuling.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;

/**
 * @author WeiRun
 * @package com.tuling.properties
 * @date 2020/10/13 20:02
 */
@Data
@ConfigurationProperties(prefix = "tuling.jwt")
public class JwtProperties {

    /**
     * 证书名称
     */
    private String keyPairName;


    /**
     * 证书别名
     */
    private String keyPairAlias;

    /**
     * 证书私钥
     */
    private String keyPairSecret;

    /**
     * 证书存储密钥
     */
    private String keyPairStoreSecret;
}
