package com.zdk.controllers;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zdk.model.Permission;
import com.zdk.model.Role;
import com.zdk.model.User;
import com.zdk.service.PermissionService;
import com.zdk.service.RoleService;
import com.zdk.utils.IpKit;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.Cookie;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zdk
 * @date 2021/12/25 13:57
 */
public class BaseController extends CommonController{
    public BaseController() {
    }

    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;


    /**
     * 获取当前登录用户
     * @return
     */
    public User getLoginUser(){
        Object user = request.getSession().getAttribute(IpKit.getIpAddressByRequest(request)+":userInfo");
        if (user != null){
            return JSONUtil.toBean(JSONUtil.parseObj(user), User.class);
        }
        return null;
    }

    /**
     * 设置cookie
     * @param name
     * @param value
     * @param maxAge
     */
    public void setCookie(String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(maxAge);
        cookie.setSecure(false);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    /**
     * 将用户信息和权限信息放入session
     * @param user
     */
    public void putInfoSession(User user){
        //根据用户id查到该用户角色,然后查到对应的权限,把权限放入session
        //权限需要在访问的不同的方法的时候不断在拦截器中判断是否具有权限,所以放入session
        request.getSession().setAttribute(IpKit.getIpAddressByRequest(request)+":userInfo", user);
        request.getSession().setAttribute("user", user);
        request.getSession().setAttribute(user.getUsername(), user);
        request.getSession().setAttribute("loginUser", user);
        request.getSession().setAttribute("role", user.getRole());
        //获取用户的角色id
        String roleName =user.getRole();
        //通过用户的角色id获取用户对应的角色对象
        Role role = roleService.getOne(new QueryWrapper<Role>().eq("role_name", roleName));
        //获取该种角色的所有的权限id
        String permissionId = role.getPermissionId();
        if (isOk(permissionId)){
            //数据库存储形式是1,2,3
            String[] permissionIdArr=permissionId.split(",");
            //获取所有的权限,与该角色所拥有的权限id进行匹配
            List<Permission> permissions = permissionService.list();
            List<Permission> functions = new ArrayList<>();
            //把用户所有的权限都添加进集合
            for (Permission permission : permissions) {
                for (String s : permissionIdArr) {
                    if (Integer.parseInt(s) == permission.getId()) {
                        functions.add(permission);
                        break;
                    }
                }
            }
            //再把集合放入session
            request.getSession().setAttribute("permissions", functions);
        }
    }
}
