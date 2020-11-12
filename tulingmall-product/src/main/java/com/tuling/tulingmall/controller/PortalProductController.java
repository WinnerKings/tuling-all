package com.tuling.tulingmall.controller;

import com.tuling.tulingmall.common.api.CommonResult;
import com.tuling.tulingmall.dao.PortalProductDao;
import com.tuling.tulingmall.domain.CartProduct;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author WeiRun
 * @package com.tuling.tulingmall.controller
 * @date 2020/10/20 20:48
 */
@RestController
@Api(tags = "PortalProductController", description = "商品查询查看#杨过添加")
@RequestMapping("/pms")
public class PortalProductController {
    
    @Autowired
    private PortalProductDao portalProductDao;

    @ApiOperation(value = "根据商品Id获取购物车商品的信息")
    @RequestMapping(value = "/cartProduct/{productId}", method = RequestMethod.GET)
    public CommonResult<CartProduct> getCartProduct(@PathVariable("productId") Long productId){
        CartProduct cartProduct = portalProductDao.getCartProduct(productId);
        return CommonResult.success(cartProduct);
    }
}
