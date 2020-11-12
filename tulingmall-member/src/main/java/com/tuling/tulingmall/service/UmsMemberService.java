package com.tuling.tulingmall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tuling.tulingmall.common.api.TokenInfo;
import com.tuling.tulingmall.model.UmsMember;

/**
 * @author WeiRun
 * @package com.tuling.tulingmall.service
 * @date 2020/10/15 18:02
 */
public interface UmsMemberService extends IService<UmsMember> {

    /**
     * 登录后获取token
     */
    TokenInfo login(String username, String password);
}
