package com.hiya.common.aop;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSONObject;
import com.hiya.common.exception.HiyaException;

/**
 * @author seven sins
 * @datetime 2018年7月1日 下午12:09:57
 */
@Aspect
@Component
@Order(1)
public class AopHandleException {
	
	private static final Logger LOG = Logger.getLogger(AopHandleException.class);
	
	@Pointcut("execution(* com.hiya..*.*Controller.*(..))")
	public void init() {
	}
	
	@Before("init()")
	public void beforeAdvice(JoinPoint joinPoint) {
		logParams();
	}
	
	@AfterThrowing(pointcut = "init()", throwing = "e")  
    public void afterThrowing(JoinPoint joinPoint, Throwable e) {  
		// 请求触发异常
		logParams();
		LOG.info("=============请求触发异常");
		/**
    	 * 主动抛出的异常
    	 */
        if(e instanceof HiyaException){
        	
        }else{ // 未捕获异常
        	throw new HiyaException(-1, "未捕获异常: " + e);
        }
	}
	
	private void logParams() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		// 输出url
		String url = request.getRequestURI();
		if(StringUtils.isNotBlank(url)) {
			LOG.info("=============url: " + url);
		}
		// 输出参数
		Map<String, String[]> parameterMap = request.getParameterMap();
		if(parameterMap != null && parameterMap.size() > 0) {
			LOG.info("=============params: " + JSONObject.toJSON(parameterMap));
		}
	}
}
