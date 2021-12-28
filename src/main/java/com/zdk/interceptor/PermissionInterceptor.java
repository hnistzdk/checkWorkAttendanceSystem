package com.zdk.interceptor;

import cn.hutool.json.JSONUtil;
import com.zdk.model.Permission;
import com.zdk.model.User;
import com.zdk.utils.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author zdk
 * @date 2021/12/25 13:46
 */
@Component
public class PermissionInterceptor implements HandlerInterceptor {

    private final Logger logger = LoggerFactory.getLogger(PermissionInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if(request.getMethod().equals("options")){
            return true;
        }
        List<Permission> permissions= (List<Permission>) request.getSession().getAttribute("functions");
        User user= (User) request.getSession().getAttribute("loginUser");
        if(user != null && permissions !=null){
            if(handler instanceof HandlerMethod){
                HandlerMethod handlerMethod=(HandlerMethod) handler;
                PermissionInfo methodAnnotation = handlerMethod.getMethodAnnotation(PermissionInfo.class);
                //没有注解 不需权限
                if (methodAnnotation == null){
                    return true;
                }
                String value = methodAnnotation.value();
                logger.debug("用户要执行的操作-> {}",value);
                boolean flag=false;
                if(value.equals("")){
                    //没有权限,说明任何人都能访问
                    flag=true;
                }else{
                    for (Permission permission : permissions) {
                        if(permission.getPermissionName().equals(value)){
                            flag=true;
                            break;
                        }
                    }
                }
                if(flag){
                    return true;
                }
            }
        }
        try {
            response.getWriter().write(JSONUtil.parseObj(ApiResponse.failWidthDiyCode(403,"无权访问")).toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
