package com.tuling.tulingmall.service.impl;

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tuling.tulingmall.domain.CartProduct;
import com.tuling.tulingmall.mapper.PmsProductMapper;
import com.tuling.tulingmall.model.PmsProduct;
import com.tuling.tulingmall.service.PmsProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author WeiRun
 * @package com.tuling.tulingmall.service.impl
 * @date 2020/10/20 20:55
 */
@Service
public class PmsProductServiceImpl extends ServiceImpl<PmsProductMapper, PmsProduct> implements PmsProductService {

//    @Autowired
    private PmsProductMapper pmsProductMapper;
    
    @Override
    public CartProduct getCartProduct(Long productId) {
        PmsProduct pmsProduct = pmsProductMapper.selectByPrimaryKey(10L);
        System.out.println(pmsProduct.toString());
        return null;
    }
    
    public void setPmsProductMapper(PmsProductMapper pmsProductMapper) {
        this.pmsProductMapper = pmsProductMapper;
    }
}
