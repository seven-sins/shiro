package com.hiya.common.service.impl;

import javax.annotation.Resource;

import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.hiya.common.service.RedisService;

/**
 * redis工具类
 * @author seven sins
 * @date 2018年6月2日 下午3:32:06
 */
@Service("redisService")
public class RedisServiceImpl implements RedisService {

	@Resource
	RedisTemplate<String, Object> redisTemplate;

	@Override
	public void add(String id, Object obj) {
		BoundValueOperations<String, Object> boundOps = redisTemplate.boundValueOps(id);
		boundOps.set(obj);
	}

	@Override
	public Object get(String key) {
		BoundValueOperations<String, Object> boundOps = redisTemplate.boundValueOps(key);
		return boundOps.get();
	}

	@Override
	public void delete(String key) {
		redisTemplate.delete(key);
	}

	@Override
	public void put(String key, String hashKey, Object obj) {
		redisTemplate.opsForHash().put(key, hashKey, obj);
	}

	public Object get(String key, String hashKey) {
		return redisTemplate.opsForHash().get(key, hashKey);
	}
}
