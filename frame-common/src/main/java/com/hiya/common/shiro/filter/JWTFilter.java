package com.hiya.common.shiro.filter;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;

import com.hiya.common.shiro.token.JWTToken;

/**
 * @author seven sins
 * @date 2018年5月31日 下午7:37:53
 */
public class JWTFilter extends BasicHttpAuthenticationFilter {
	private static final Logger LOGGER = Logger.getLogger(JWTFilter.class);
	/**
     * 判断用户是否想要登入。
     * 检测header里面是否包含Authorization字段即可
     */
    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        HttpServletRequest req = (HttpServletRequest) request;
        String authorization = req.getHeader("Authorization");
        if(StringUtils.isBlank(authorization)) {
        	this.response401(request, response);
            return false;  
        }
        
        return true;
    }
    
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String authorization = httpServletRequest.getHeader("Authorization");

        JWTToken token = new JWTToken(authorization);
        // 提交给realm进行登入，如果错误他会抛出异常并被捕获
        getSubject(request, response).login(token);
        // 如果没有抛出异常则代表登入成功，返回true
        
        return true;
    }
    
    /**
     * 为什么最终返回的都是true，即允许访问
     * 例如我们提供一个地址 GET /article
     * 登入用户和游客看到的内容是不同的
     * 如果在这里返回了false，请求会被直接拦截，用户看不到任何东西
     * 所以我们在这里返回true，Controller中可以通过 subject.isAuthenticated() 来判断用户是否登入
     * 如果有些资源只有登入用户才能访问，我们只需要在方法上面加上 @RequiresAuthentication 注解即可
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if (isLoginAttempt(request, response)) {
            try {
                executeLogin(request, response);
            } catch (Exception e) {
            	this.response401(request, response, e.getMessage());
            	return false;
            }
        }
        
        return true;
    }
    
    /**
     * 对跨域提供支持
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
    	/**
    	 * 改为在nginx中处理跨域
    	 */
		//        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		//        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		//        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
		//        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
		//        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
		//        // 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
		//        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
		//            httpServletResponse.setStatus(HttpStatus.OK.value());
		//            return false;
		//        }
    	
        return super.preHandle(request, response);
    }
    
    /**
     * 将非法请求跳转到 /401
     */
    private void response401(ServletRequest req, ServletResponse resp) {
        try {
            HttpServletResponse httpServletResponse = (HttpServletResponse) resp;
            httpServletResponse.sendRedirect("/401");
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }
    
    private void response401(ServletRequest req, ServletResponse resp, String errorMsg) {
        try {
            HttpServletResponse httpServletResponse = (HttpServletResponse) resp;
            httpServletResponse.sendRedirect("/401?errorMsg=" + errorMsg);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }
}
