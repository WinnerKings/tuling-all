package com.tuling.tulingmall.controller;

import com.tuling.tulingmall.common.api.CommonResult;
import com.tuling.tulingmall.common.api.TokenInfo;
import com.tuling.tulingmall.service.UmsMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author WeiRun
 * @package com.tuling.tulingmall.controller
 * @date 2020/10/15 11:28
 */
@Controller
@Api(tags = "UmsMemberController", description = "会员登录注册管理")
@RequestMapping("/sso")
@Slf4j
public class UmsMemberController {
    
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    
    @Autowired
    private UmsMemberService umsMemberService;

    @ResponseBody
    @ApiOperation("会员登录")
    @PostMapping(value = "/login")
    public CommonResult login(@RequestParam String username, @RequestParam String password) {

        TokenInfo tokenInfo = umsMemberService.login(username, password);
        if (tokenInfo == null) {
            return CommonResult.validateFailed("用户名或密码错误");
        }
        
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", tokenInfo.getAccess_token());
        tokenMap.put("tokenHead", tokenHead);
        tokenMap.put("refreshToken",tokenInfo.getRefresh_token());
        return CommonResult.success(tokenMap);
    }
}
