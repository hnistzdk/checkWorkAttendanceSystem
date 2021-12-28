package com.zdk.controllers.controller;


import com.github.pagehelper.PageInfo;
import com.zdk.controllers.BaseController;
import com.zdk.interceptor.PermissionInfo;
import com.zdk.model.User;
import com.zdk.model.dto.*;
import com.zdk.service.UserService;
import com.zdk.utils.ApiResponse;
import com.zdk.utils.HashKit;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author zdk
 * @since 2021-12-25
 */
@Api("用户模块")
@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;


    @ApiOperation("登录接口")
    @PermissionInfo
    @PostMapping("/login")
    public ApiResponse login(@RequestBody LoginDto loginDto){
        if (notOk(loginDto.getUsername())||notOk(loginDto.getPassword())){
            return ApiResponse.fail("用户名或密码格式错误");
        }
        ApiResponse login = userService.login(loginDto.getUsername(), loginDto.getPassword());
        if (login.isFail()){
            return login;
        }
        User user = (User) login.getData();
        setCookie("userInfo", HashKit.md5(login.getData().toString()),60*60 );
        putInfoSession(user);
        return login;
    }

    @ApiOperation("员工列表接口")
    @PermissionInfo
    @GetMapping("/list")
    public ApiResponse userList(PageDto pageDto){
        PageInfo<User> userList = userService.getUserPage(pageDto);
        return ApiResponse.success(userList);
    }

    @ApiOperation("添加员工接口")
    @PermissionInfo
    @PostMapping("/add")
    public ApiResponse addUser(@RequestBody AddUserDto addUserDto){
        if (notOk(addUserDto)){
            return ApiResponse.fail("参数错误");
        }
        return userService.addUser(addUserDto);
    }

    @ApiOperation("修改员工信息接口")
    @PermissionInfo
    @PostMapping("/update")
    public ApiResponse updateUser(@RequestBody UpdateUserDto updateUserDto){
        if (notOk(updateUserDto)){
            return ApiResponse.fail("参数错误");
        }
        return userService.updateUser(updateUserDto);
    }

    @ApiOperation("删除员工信息接口")
    @PermissionInfo
    @PostMapping("/delete/{id}")
    public ApiResponse deleteUser(@PathVariable Integer id){
        if (notOk(id)){
            return ApiResponse.fail("参数错误");
        }
        boolean remove = userService.removeById(id);
        return ApiResponse.result(remove, "删除员工成功","删除员工失败");
    }


    @ApiOperation("修改员工账号状态接口")
    @PermissionInfo
    @PostMapping("/changeState")
    public ApiResponse changeState(@RequestBody ChangeStateDto changeStateDto){
        if (notOk(changeStateDto)){
            return ApiResponse.fail("参数错误");
        }
        return userService.changeState(changeStateDto);
    }

    @ApiOperation("返回指定员工信息接口")
    @PermissionInfo
    @GetMapping("/edit/{id}")
    public ApiResponse getUser(@PathVariable Integer id){
        if (notOk(id)){
            return ApiResponse.fail("参数错误");
        }
        User user = userService.getById(id);
        if (user == null){
            return ApiResponse.fail("获取用户信息失败");
        }
        return ApiResponse.success(user);
    }

    @ApiOperation("注册接口")
    @PermissionInfo
    @PostMapping("/register")
    public ApiResponse register(@RequestBody AddUserDto registerDto){
        if (notOk(registerDto)){
            return ApiResponse.fail("参数错误");
        }
        return userService.register(registerDto);
    }

    @ApiOperation("重置密码接口")
    @PermissionInfo
    @PostMapping("/resetPwd")
    public ApiResponse resetPwd(@RequestBody PwdDto PwdDto){
        return null;
    }
}

