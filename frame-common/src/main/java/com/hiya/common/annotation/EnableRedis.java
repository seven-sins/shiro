package com.hiya.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

import com.hiya.common.service.impl.RedisServiceImpl;

/**
 * 引入redis
 * @author seven sins
 * @date 2018年6月2日 下午2:37:53
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE })
@Inherited
@EnableSpringUtil
@Import({ EnableRedis.RedisServiceImportSelector.class })
public @interface EnableRedis {

	static class RedisServiceImportSelector implements ImportSelector {

		@Override
		public String[] selectImports(AnnotationMetadata importingClassMetadata) {
			
			return new String[] { RedisServiceImpl.class.getName() };
		}
	}
}
