package com.tuling.tulingmall.controller;

import com.tuling.tulingmall.common.api.CommonResult;
import com.tuling.tulingmall.model.OmsCartItem;
import com.tuling.tulingmall.service.OmsCartItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author WeiRun
 * @package com.tuling.tuingmall.Controller
 * @date 2020/10/20 17:40
 */
@Api(tags = "OmsCartItemController", description = "购物车管理")
@Controller
@RequestMapping("/cart")
public class OmsCartController {

    @Autowired
    private OmsCartItemService cartItemService;

    @ApiOperation(value = "添加商品到购物车",notes = "杨过修改购物逻辑,数据不必全都从前台传")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult add(@RequestBody OmsCartItem cartItem,
                            @RequestHeader("memberId")Long memberId,
                            @RequestHeader("nickName") String nickName) {
        int count = cartItemService.add(cartItem,memberId,nickName);
        if (count > 0) {
            return CommonResult.success(cartItemService.cartItemCount());
            //return CommonResult.success(count);
        }
        return CommonResult.failed();
    }
}
