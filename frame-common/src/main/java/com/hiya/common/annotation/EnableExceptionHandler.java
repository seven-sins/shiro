package com.hiya.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

import com.hiya.common.aop.RequestInfoAOP;
import com.hiya.common.exception.CustomExceptionHandler;

/**
 * 引入全局异常处理
 * @author seven sins
 * @date 2018年6月2日 下午2:37:53
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE })
@Inherited
@Import({ EnableExceptionHandler.ExceptionHandlerImportSelector.class, RequestInfoAOP.class })
public @interface EnableExceptionHandler {

	static class ExceptionHandlerImportSelector implements ImportSelector {

		@Override
		public String[] selectImports(AnnotationMetadata importingClassMetadata) {
			
			return new String[] { CustomExceptionHandler.class.getName() };
		}
	}
}
