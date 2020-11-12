package com.tuling.tulingmall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tuling.tulingmall.domain.CartProduct;
import com.tuling.tulingmall.model.PmsProduct;

/**
 * @author WeiRun
 * @package com.tuling.tulingmall.service
 * @date 2020/10/20 20:54
 */
public interface PmsProductService extends IService<PmsProduct> {

    CartProduct getCartProduct(Long productId);
}
