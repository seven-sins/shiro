package com.hiya.shiro.config;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.data.redis.core.RedisTemplate;

import com.hiya.shiro.utils.SpringUtil;

@SuppressWarnings("unchecked")
public class CustomRedisCache<K, V> implements Cache<K, V> {
	
	static final String SESSION_PREFIX = "shiro_session_";
	long expireTime = 120000;
	RedisTemplate<String, V> redisTemplate;
	
	public CustomRedisCache() {
		
	}
	
	@Override
	public void clear() throws CacheException {
		if(redisTemplate == null) {
			redisTemplate = (RedisTemplate<String, V>) SpringUtil.getBean("redisTemplate");
		}
        // 这里不用connection.flushDb(), 以免Session等其他缓存数据被连带删除
        Set<String> redisKeys = redisTemplate.keys(SESSION_PREFIX + "*");
        for (String redisKey : redisKeys) {
            redisTemplate.delete(redisKey);
        }
	}

	@Override
	public V get(K key) throws CacheException {
		if(redisTemplate == null) {
			redisTemplate = (RedisTemplate<String, V>) SpringUtil.getBean("redisTemplate");
		}
		return redisTemplate.opsForValue().get(SESSION_PREFIX + key);
	}

	@Override
	public Set<K> keys() {
		if(redisTemplate == null) {
			redisTemplate = (RedisTemplate<String, V>) SpringUtil.getBean("redisTemplate");
		}
		Set<String> redisKeys = redisTemplate.keys(SESSION_PREFIX + "*");
        Set<K> keys = new HashSet<K>();
        for (String redisKey : redisKeys) {
            keys.add((K) redisKey.substring(SESSION_PREFIX.length()));
        }
        return keys;
	}

	@Override
	public V put(K key, V value) throws CacheException {
		if(redisTemplate == null) {
			redisTemplate = (RedisTemplate<String, V>) SpringUtil.getBean("redisTemplate");
		}
		V previos = get(key);
        redisTemplate.opsForValue().set(SESSION_PREFIX + key, value, this.expireTime, TimeUnit.MILLISECONDS);
        return previos;
	}

	@Override
	public V remove(K key) throws CacheException {
		if(redisTemplate == null) {
			redisTemplate = (RedisTemplate<String, V>) SpringUtil.getBean("redisTemplate");
		}
		V previos = get(key);
        redisTemplate.delete(SESSION_PREFIX + key);
        return previos;
	}

	@Override
	public int size() {
		if (keys() == null)
            return 0;
        return keys().size();
	}

	@Override
	public Collection<V> values() {
		if(redisTemplate == null) {
			redisTemplate = (RedisTemplate<String, V>) SpringUtil.getBean("redisTemplate");
		}
		Set<String> redisKeys = redisTemplate.keys(SESSION_PREFIX + "*");
        Set<V> values = new HashSet<V>();
        for (String redisKey : redisKeys) {
            V value = redisTemplate.opsForValue().get(redisKey);
            values.add(value);
        }
        return values;
	}

}
