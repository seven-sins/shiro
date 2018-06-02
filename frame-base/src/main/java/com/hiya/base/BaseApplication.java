package com.hiya.base;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.hiya.common.annotation.EnableShiro;

@EnableShiro
@SpringBootApplication
public class BaseApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(BaseApplication.class, args);
	}
}
