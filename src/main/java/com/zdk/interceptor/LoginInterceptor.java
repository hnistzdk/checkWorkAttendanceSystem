package com.zdk.interceptor;

import com.zdk.utils.IpKit;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zdk
 * @date 2021/12/25 17:09
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        for (Cookie cookie : request.getCookies()) {
            String name = cookie.getName();
            if (name.equals("userInfo")){
                IpKit.getIpAddressByRequest(request);
                Object loginUser = request.getSession().getAttribute("userInfo");
                if (loginUser == null){
                }
            }
        }
        Object loginUser = request.getSession().getAttribute("loginUser");
        return loginUser != null;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
