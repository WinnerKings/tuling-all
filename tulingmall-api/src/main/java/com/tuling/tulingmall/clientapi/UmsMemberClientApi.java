package com.tuling.tulingmall.clientapi;

import com.tuling.tulingmall.common.api.CommonResult;
import com.tuling.tulingmall.config.UmsMemberFeignConfig;
import com.tuling.tulingmall.model.UmsMember;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author WeiRun
 * @package com.tuling.tulingmall.clientapi
 * @date 2020/10/13 18:16
 */
@FeignClient(name = "tulingmall-member", configuration = UmsMemberFeignConfig.class)
public interface UmsMemberClientApi {

    @ResponseBody
    @GetMapping(value = "/getCurrentMember")
    CommonResult<UmsMember> getCurrentMember();
}
