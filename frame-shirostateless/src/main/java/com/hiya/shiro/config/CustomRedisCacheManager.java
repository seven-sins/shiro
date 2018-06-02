package com.hiya.shiro.config;

import java.util.Set;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.util.Destroyable;
import org.springframework.data.redis.core.RedisTemplate;

import com.hiya.shiro.utils.SpringUtil;

public class CustomRedisCacheManager implements CacheManager, Destroyable {

	static final String SESSION_PREFIX = "shiro_session_";
	
	RedisTemplate<String, ?> redisTemplate;
	
	@SuppressWarnings("unchecked")
	@Override
	public void destroy() throws Exception {
		if (redisTemplate == null) {
			redisTemplate = (RedisTemplate<String, ?>) SpringUtil.getBean("redisTemplate");
		}
		Set<String> keys = redisTemplate.keys(SESSION_PREFIX + "*");
		for (String key : keys) {
			redisTemplate.delete(key);
		}
	}

	@Override
	public <K, V> Cache<K, V> getCache(String name) throws CacheException {
		return new CustomRedisCache<>();
	}

	public void setRedisTemplate(RedisTemplate<String, ?> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

}
