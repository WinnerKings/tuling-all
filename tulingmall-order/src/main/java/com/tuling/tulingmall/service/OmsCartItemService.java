package com.tuling.tulingmall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tuling.tulingmall.model.OmsCartItem;

/**
 * @author WeiRun
 * @package com.tuling.tulingmall.service
 * @date 2020/10/20 19:56
 */
public interface OmsCartItemService extends IService<OmsCartItem> {

    /**
     * 查询购物车中是否包含该商品，有增加数量，无添加到购物车
     */
    int add(OmsCartItem cartItem, Long memberId, String nickName);

    /**
     * 购物车产品数量
     */
    Long cartItemCount();
}
