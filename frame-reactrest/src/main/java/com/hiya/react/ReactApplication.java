package com.hiya.react;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

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
	
	private CorsConfiguration buildConfig() {  
        CorsConfiguration corsConfiguration = new CorsConfiguration();  
        corsConfiguration.addAllowedOrigin("*"); // 1  
        corsConfiguration.addAllowedHeader("*"); // 2  
        corsConfiguration.addAllowedMethod("*"); // 3  
        return corsConfiguration;  
    }  
  
    @Bean  
    public CorsFilter corsFilter() {  
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();  
        source.registerCorsConfiguration("/**", buildConfig());
        return new CorsFilter(source);  
    }
}
