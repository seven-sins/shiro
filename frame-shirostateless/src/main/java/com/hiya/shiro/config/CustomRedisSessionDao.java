package com.hiya.shiro.config;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.springframework.data.redis.core.RedisTemplate;

@SuppressWarnings({"rawtypes", "unchecked"})
public class CustomRedisSessionDao extends AbstractSessionDAO {

	static final String SESSION_PREFIX = "shiro_session_";
	long expireTime = 120000;  
	
    RedisTemplate redisTemplate;
	
	public CustomRedisSessionDao() {  
        super();  
    } 
	
	public CustomRedisSessionDao(long expireTime, RedisTemplate redisTemplate) {  
        super();  
        this.expireTime = expireTime;  
        this.redisTemplate = redisTemplate;  
    } 
	
	@Override
	public void delete(Session session) {
		if (null == session) {  
            return;  
        }  
        redisTemplate.delete(SESSION_PREFIX + session.getId());  
	}

	@Override
	public void update(Session session) throws UnknownSessionException {
		System.out.println("===============update================");  
        if (session == null || session.getId() == null) {  
            return;  
        }  
        session.setTimeout(expireTime);  
        redisTemplate.opsForValue().set(SESSION_PREFIX + session.getId(), session, expireTime, TimeUnit.MILLISECONDS); 
	}

	@Override
	protected Serializable doCreate(Session session) {
		System.out.println("===============doCreate================");  
        Serializable sessionId = this.generateSessionId(session);  
        this.assignSessionId(session, sessionId);  
  
        redisTemplate.opsForValue().set(SESSION_PREFIX + session.getId(), session, expireTime, TimeUnit.MILLISECONDS);  
        return sessionId;  
	}

	@Override
	protected Session doReadSession(Serializable sessionId) {
		System.out.println("==============doReadSession=================");  
        if (sessionId == null) {  
            return null;  
        }  
        return (Session) redisTemplate.opsForValue().get(SESSION_PREFIX + sessionId);  
	}

	/**
	 * 获取活跃的session，可以用来统计在线人数，如果要实现这个功能，
	 * 可以在将session加入redis时指定一个session前缀，
	 * 统计的时候则使用keys("session-prefix*")的方式来模糊查找redis中所有的session集合  
	 */
	@Override
    public Collection<Session> getActiveSessions() {  
        System.out.println("==============getActiveSessions=================");  
        Set<String> keys = redisTemplate.keys(SESSION_PREFIX + "*");  
        Set<Session> sessions = new HashSet<>();
        for(String key: keys) {
        	sessions.add((Session) redisTemplate.opsForValue().get(key));
        }
        return sessions;
    } 
	
	public void setRedisTemplate(RedisTemplate<String, ?> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}
	
}
