package com.hiya.react;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.hiya.common.annotation.EnableShiro;
import com.hiya.common.config.annotation.Database;

/**
 * 前端项目react-fullstack api接口
 * @author seven sins
 * 2018年6月2日 下午11:34:23
 */
@EnableShiro
@Database
@SpringBootApplication
@MapperScan("com.hiya.react.mapper")
public class ReactApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(ReactApplication.class, args);
	}
	
}
