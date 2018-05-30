package com.hiya.shiro.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hiya.object.sys.po.User;
import com.hiya.shiro.mapper.UserMapper;
import com.hiya.shiro.service.UserService;

/**
 * @author seven sins
 * @date 2017年12月30日 下午4:42:21
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserMapper userMapper;

	@Override
	public List<User> find(User entity) {
		return userMapper.find(entity);
	}

	@Override
	public int count(User entity) {
		return userMapper.count(entity);
	}

	@Override
	public User get(Serializable id) {
		return userMapper.get(id);
	}

	@Override
	public void insert(User entity) {
		userMapper.insert(entity);
	}

	@Override
	public void update(User entity) {
		userMapper.update(entity);
	}

	@Override
	public void deleteById(Serializable id) {
		userMapper.deleteById(id);
	}

	@Override
	public void delete(Serializable[] ids) {
		userMapper.delete(ids);
	}

	@Override
	public User findByUsername(String username) {
		User query = new User();
		query.setUsername(username);
		List<User> user = this.find(query);
		if(user != null && !user.isEmpty()) {
			return user.get(0);
		}
		return null;
	}

}
