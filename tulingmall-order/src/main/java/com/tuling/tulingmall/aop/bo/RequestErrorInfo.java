package com.tuling.tulingmall.aop.bo;

import lombok.Data;

/**
 * @author WeiRun
 * @package com.tuling.tulingmall.aop.bo
 * @date 2020/10/20 19:50
 */
@Data
public class RequestErrorInfo {

    private String ip;
    private String url;
    private String httpMethod;
    private String classMethod;
    private Object requestParams;
    private RuntimeException exception;
}
