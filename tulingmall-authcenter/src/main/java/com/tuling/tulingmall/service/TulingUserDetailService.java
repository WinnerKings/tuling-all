package com.tuling.tulingmall.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tuling.tulingmall.domain.MemberDetails;
import com.tuling.tulingmall.mapper.UmsMemberMapper;
import com.tuling.tulingmall.model.UmsMember;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @author WeiRun
 * @package com.tuling.tulingmall.domall.service
 * @date 2020/10/13 20:42
 */
@Slf4j
@Component
public class TulingUserDetailService implements UserDetailsService {

    @Autowired
    private ClientDetailsService clientDetailsService;

    @Autowired
    private UmsMemberMapper memberMapper;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        // ===================客户端信息认证start(授权码认证)===================
        // 取出身份，如果身份为空说明没有认证（Authentication 存入的是用户的令牌信息）
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 没有认证统一采用httpBasic认证，httpBasic中存储了client_id和clie  nt_secret，开始认证client_id和client_secret
        if (authentication == null) {
            //数据库查询，查询oauth_client_details这个表
            ClientDetails clientDetails = clientDetailsService.loadClientByClientId(userName);
            
            if (clientDetails != null) {
                // 客户端密钥，username：客户端ID
                // AuthorityUtils.commaSeparatedStringToAuthorityList("")：写入权限
                String clientSecret = clientDetails.getClientSecret();
                return new User(userName, clientSecret, AuthorityUtils.commaSeparatedStringToAuthorityList("Test"));
            }
        }
        // ===================客户端信息认证end===================

        // ===================用户账号信息认证start(密码模式认证)===================
        if (StringUtils.isEmpty(userName)) {
            log.error("用户登录用户名为空:{}", userName);
            throw new UsernameNotFoundException("用户名不能为空");
        }
        UmsMember umsMember = memberMapper.selectOne(new QueryWrapper<UmsMember>().eq("username", userName));
        if (StringUtils.isEmpty(userName)) {
            log.warn("根据用户名没有查询到对应的用户信息:{}", userName);
        }
        log.info("根据用户名:{}获取用户登陆信息:{}", userName, umsMember);

//        MemberDetails memberDetails = new MemberDetails(userName, umsMember.getPassword(), Arrays.asList(new SimpleGrantedAuthority("Test")));
        MemberDetails memberDetails = new MemberDetails(umsMember);
        return memberDetails;
    }
}
