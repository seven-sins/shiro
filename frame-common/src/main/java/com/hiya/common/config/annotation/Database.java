package com.hiya.common.config.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

import com.hiya.common.config.mybatis.DruidConfiguration;
import com.hiya.common.config.mybatis.MyBatisConfig;
import com.hiya.common.config.mybatis.MyBatisMapperScannerConfig;


/**
 * 数据源
 * 
 * @author seven sins
 * @date 2018年1月1日 下午7:29:15
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
@Import({ DruidConfiguration.class, MyBatisConfig.class, MyBatisMapperScannerConfig.class })
public @interface Database {

}
