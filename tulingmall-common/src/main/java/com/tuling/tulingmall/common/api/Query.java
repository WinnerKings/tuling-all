package com.tuling.tulingmall.common.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tuling.tulingmall.common.constant.Constant;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * @author WeiRun
 * @package com.tuling.tulingmall.common.api
 * @date 2020/10/13 16:56
 */
public class Query<T> {
    
    public IPage<T> getPage(Map<String, Object> params) {
        return this.getPage(params,null,false);
    }

    public IPage<T> getPage(Map<String, Object> params, String defaultOrderField, boolean isAsc) {
        long curPage = 1;
        long limit = 10;
        
        if(params.get(Constant.PAGE) != null) {
            curPage = Long.parseLong((String) params.get(Constant.PAGE));
        }
        if(params.get(Constant.LIMIT) != null) {
            limit = Long.parseLong((String) params.get(Constant.LIMIT));
        }

        Page<T> page = new Page(curPage, limit);
        params.put(Constant.PAGE,page);
        
        if(StringUtils.isEmpty(defaultOrderField)) {
            return page;
        }
        if(isAsc) {
            page.addOrder(OrderItem.asc(defaultOrderField));
        } else {
            page.addOrder(OrderItem.desc(defaultOrderField));
        }
        return page;
    }
}
