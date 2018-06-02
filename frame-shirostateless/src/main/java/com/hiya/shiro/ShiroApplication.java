package com.hiya.shiro;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.hiya.common.annotation.EnableShiro;
import com.hiya.common.config.annotation.Database;

/**
 * @author seven sins
 * @date 2018年5月29日 下午9:05:40
 */
@EnableShiro
@Database
@SpringBootApplication
@MapperScan("com.hiya.shiro.mapper")
public class ShiroApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(ShiroApplication.class, args);
	}
}
