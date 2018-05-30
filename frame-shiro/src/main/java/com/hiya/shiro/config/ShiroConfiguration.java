package com.hiya.shiro.config;

import java.util.LinkedHashMap;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hiya.shiro.service.UserService;

/**
 * @author seven sins
 * @date 2018年5月30日 下午7:54:36
 */
@Configuration
public class ShiroConfiguration {
	
	@Autowired
	UserService userService;
	
	/**
	 * 配置密码匹配器
	 * @return
	 */
	@Bean("credentialMatcher")
	public CredentialMatcher credentialMatcher() {
		return new CredentialMatcher();
	}
	
	/**
	 * 配置授权登录
	 * @param matcher
	 * @return
	 */
	@Bean("authRealm")
	public AuthRealm authRealm(@Qualifier("credentialMatcher") CredentialMatcher matcher) {
		AuthRealm authRealm = new AuthRealm();
		authRealm.setUserService(userService);
		authRealm.setCredentialsMatcher(matcher);
		
		return authRealm;
	}
	
	@Bean("securityManager")
	public SecurityManager securityManager(@Qualifier("authRealm") AuthRealm authRealm) {
		DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
		manager.setRealm(authRealm);
		
		return manager;
	}
	
	@Bean("shiroFilter")
	public ShiroFilterFactoryBean shiroFilter(@Qualifier("securityManager") SecurityManager manager) {
		ShiroFilterFactoryBean factory = new ShiroFilterFactoryBean();
		factory.setSecurityManager(manager);
		
		// 登录URL
		factory.setLoginUrl("/login");
		// 登录成功后跳转的URL
		factory.setSuccessUrl("/index");
		// 登录失败跳转的URL
		factory.setUnauthorizedUrl("/unauthorized");
		
		LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
		// 必须登录才能访问
		filterChainDefinitionMap.put("index", "authc");
		// 不需要登录也能访问
		filterChainDefinitionMap.put("/login", "anon");
		
		factory.setFilterChainDefinitionMap(filterChainDefinitionMap);
		
		return factory;
	}
	
	/**
	 * 整合shiro到spring
	 * @param manager
	 * @return
	 */
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Qualifier("securityManager") SecurityManager manager) {
		AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
		advisor.setSecurityManager(manager);
		
		return advisor;
	}
	
	@Bean
	public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
		creator.setProxyTargetClass(true);
		
		return creator;
	}
}
