package com.tuling.tulingmall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tuling.tulingmall.domain.CartProduct;
import com.tuling.tulingmall.feignapi.pms.PmsProductFeignApi;
import com.tuling.tulingmall.mapper.OmsCartItemMapper;
import com.tuling.tulingmall.model.OmsCartItem;
import com.tuling.tulingmall.service.OmsCartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * @author WeiRun
 * @package com.tuling.tulingmall.service.impl
 * @date 2020/10/20 19:58
 */
@Service
public class OmsCartItemServiceImpl extends ServiceImpl<OmsCartItemMapper, OmsCartItem> implements OmsCartItemService {

    @Autowired
    private OmsCartItemMapper cartItemMapper;
    
    @Autowired
    private PmsProductFeignApi pmsProductFeignApi;

    @Transactional
    @Override
    public int add(OmsCartItem cartItem, Long memberId, String nickName) {
        int count = 0;
        cartItem.setMemberId(memberId);
        cartItem.setMemberNickname(nickName);
        cartItem.setDeleteStatus(0);
        OmsCartItem existCartItem = getCartItem(cartItem);
        // 创建购物车
        if (existCartItem == null) {
            cartItem.setCreateDate(new Date());
            //todo 查询产品信息 需要远程调用商品服务获取，这里暂时写死
            CartProduct cartProduct = pmsProductFeignApi.getCartProduct(cartItem.getProductId()).getData();
            cartItem.setProductName(cartProduct.getName());
            cartItem.setPrice(cartProduct.getPrice());
            cartItem.setProductPic(cartProduct.getPic());
            cartItem.setProductBrand(cartProduct.getBrandName());
            cartItem.setProductCategoryId(cartProduct.getProductCategoryId());
            cartItem.setProductSn(cartProduct.getProductSn());
            cartItem.setProductSubTitle(cartProduct.getSubTitle());
            cartItem.setPrice(cartProduct.getPrice());
            //遍历产品sku，设置购买规格
            cartProduct.getSkuStockList().stream().forEach((skuItem)->{
                if(cartItem.getProductSkuId() == skuItem.getId()){
                    cartItem.setSp1(skuItem.getSp1());
                    cartItem.setSp2(skuItem.getSp2());
                    cartItem.setSp3(skuItem.getSp3());
                    cartItem.setProductPic(skuItem.getPic());
                    cartItem.setPrice(skuItem.getPrice());
                    cartItem.setProductSkuCode(skuItem.getSkuCode());
                    //product_attr,此处有设计缺陷，不太好拿，暂时留空
                }
            });
            count = cartItemMapper.insert(cartItem);
        } else {
            cartItem.setModifyDate(new Date());
            existCartItem.setQuantity(existCartItem.getQuantity() + cartItem.getQuantity());
            count = cartItemMapper.updateById(existCartItem);
        }
        return count;
    }

    private OmsCartItem getCartItem(OmsCartItem cartItem) {
        QueryWrapper<OmsCartItem> queryWrap = new QueryWrapper<>();
        queryWrap.eq("member_id", cartItem.getMemberId())
                .eq("product_id", cartItem.getProductId())
                .eq("delete_status", 0);
        if (!StringUtils.isEmpty(cartItem.getSp1())) {
            queryWrap.eq("sp1", cartItem.getSp1());
        }
        if (!StringUtils.isEmpty(cartItem.getSp2())) {
            queryWrap.eq("sp2", cartItem.getSp2());
        }
        if (!StringUtils.isEmpty(cartItem.getSp3())) {
            queryWrap.eq("sp3", cartItem.getSp3());
        }
        OmsCartItem omsCartItem = cartItemMapper.selectOne(queryWrap);
        if (!ObjectUtils.isEmpty(omsCartItem)) {
            return omsCartItem;
        }
        return null;
    }

    @Override
    public Long cartItemCount() {
        return null;
    }
}
