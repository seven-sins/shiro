package com.hiya.shiro.config;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import com.hiya.shiro.service.UserService;

/**
 * @author seven sins
 * @date 2018年5月30日 下午7:54:36
 */
@SuppressWarnings({"rawtypes", "unchecked"})
@Configuration
public class ShiroConfiguration {
	
	@Autowired
	UserService userService;
	@Autowired
	RedisTemplate redisTemplate;
	
	/**
	 * 配置授权登录
	 * @param matcher
	 * @return
	 */
	@Bean("authRealm")
	public AuthRealm authRealm() {
		AuthRealm authRealm = new AuthRealm();
		authRealm.setUserService(userService);
		
		return authRealm;
	}
	
	@Bean
	public DefaultWebSessionManager sessionManager() {
		/**
		 * 重写DefaultWebSessionManager获取session方法, 解决单次请求多次访问redis读取session的问题
		 */
		DefaultWebSessionManager sessionManager = new CustomShiroSessionManager();
		CustomRedisSessionDao redisSessionDao = new CustomRedisSessionDao();
		redisSessionDao.setRedisTemplate(redisTemplate);
		sessionManager.setSessionDAO(redisSessionDao);
		return sessionManager;
	}
	
	@Bean("securityManager")
	public SecurityManager securityManager(@Qualifier("authRealm") AuthRealm authRealm) {
		DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
		manager.setRealm(authRealm);

		CustomRedisCacheManager redisCacheManager = new CustomRedisCacheManager();
		redisCacheManager.setRedisTemplate(redisTemplate);
		manager.setCacheManager(redisCacheManager);
        manager.setSessionManager(sessionManager());
		
		return manager;
	}
	
	@Bean("shiroFilter")
	public ShiroFilterFactoryBean shiroFilter(@Qualifier("securityManager") SecurityManager manager) {
		ShiroFilterFactoryBean factory = new ShiroFilterFactoryBean();
		/**
		 * 设置filter
		 */
		Map<String, Filter> filterMap = new HashMap<>();
		filterMap.put("jwt", new JWTFilter());
		factory.setFilters(filterMap);
		/**
		 * 设置securityManager
		 */
		factory.setSecurityManager(manager);
		
		/**
		 * 自定义url规则
		 */
		LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
		// 必须登录才能访问
		filterChainDefinitionMap.put("index", "authc");
		
		// 必须有admin角色才能访问接口/admin
		filterChainDefinitionMap.put("/admin", "roles[admin]");
		
		// 不需要登录也能访问
		filterChainDefinitionMap.put("/login", "anon");
		filterChainDefinitionMap.put("/loginUser", "anon");
		filterChainDefinitionMap.put("/401", "anon");
		filterChainDefinitionMap.put("/**", "jwt");
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
