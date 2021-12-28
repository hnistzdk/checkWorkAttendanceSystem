package com.zdk.service.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zdk.mapper.UserMapper;
import com.zdk.model.User;
import com.zdk.model.dto.*;
import com.zdk.service.UserService;
import com.zdk.utils.ApiResponse;
import com.zdk.utils.HashKit;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zdk
 * @since 2021-12-25
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public ApiResponse login(String username, String password) {
        User user = lambdaQuery().eq(User::getUsername,username).one();
        if (user == null){
            return ApiResponse.fail("该账号不存在");
        }
        if (!user.getState()){
            return ApiResponse.fail("该员工已离职或账号被禁用");
        }
        if (user.getPassword().equals(password)){
            return ApiResponse.success(user, "重新登录成功");
        }
        if (!user.getPassword().equals(HashKit.md5(password))){
            return ApiResponse.fail("账号或密码错误");
        }
        return ApiResponse.success(user, "登录验证成功");
    }

    @Override
    public PageInfo<User> getUserPage(PageDto pageDto) {
        Integer pageNumber = pageDto.getPageNumber();
        Integer pageSize = pageDto.getPageSize();
        String keywords = pageDto.getKeywords();
        PageHelper.startPage(pageNumber, pageSize);
        List<User> userList = lambdaQuery().like(StringUtils.isNotBlank(pageDto.getKeywords()), User::getTrueName, keywords).list();
        return new PageInfo<>(userList);
    }

    @Override
    public ApiResponse addUser(AddUserDto addUserDto) {
        String username = addUserDto.getUsername();
        if (lambdaQuery().eq(User::getUsername,username).one()!= null){
            return ApiResponse.fail("该用户名已存在,请重新输入");
        }
        User user = new User();
        user.setUsername(username).setPassword(HashKit.md5(addUserDto.getPassword()))
                .setTrueName(addUserDto.getTrueName()).setGender(addUserDto.getGender())
                .setEmail(addUserDto.getEmail()).setRole("普通用户")
                .setState(true);
        return ApiResponse.result(userMapper.insert(user)>0,"添加员工成功","添加员工失败");
    }

    @Override
    public ApiResponse updateUser(UpdateUserDto updateUserDto) {
        boolean update = lambdaUpdate().eq(User::getId, updateUserDto.getId())
                .set(User::getTrueName,updateUserDto.getTrueName())
                .set(User::getGender, updateUserDto.getGender())
                .set(User::getEmail, updateUserDto.getEmail())
                .update();
        return ApiResponse.result(update,"更新员工信息成功","更新员工信息失败");
    }

    @Override
    public ApiResponse changeState(ChangeStateDto changeStateDto) {
        return ApiResponse.result(lambdaUpdate().eq(User::getId, changeStateDto.getId()).set(User::getState, changeStateDto.getState()).update(),"更新状态成功","更新状态失败");
    }

    @Override
    public ApiResponse register(AddUserDto registerDto) {
        ApiResponse apiResponse = addUser(registerDto);
        if (apiResponse.isSuccess()){
            apiResponse.setMsg("注册成功");
        }else{
            apiResponse.setMsg("注册失败");
        }
        return apiResponse;
    }

    @Override
    public ApiResponse resetPwd(PwdDto pwdDto) {
        Integer id = pwdDto.getId();
        if (pwdDto == null || id == null){
            return ApiResponse.fail("参数错误");
        }
        String oldPassword = pwdDto.getOldPassword();
        String newPassword = pwdDto.getNewPassword();
        if (StringUtils.isBlank(oldPassword) || StringUtils.isBlank(newPassword)){
            return ApiResponse.fail("密码不能为空");
        }
        User user = getById(id);
        if (!user.getPassword().equals(HashKit.md5(oldPassword))){
            return ApiResponse.fail("旧密码错误");
        }
        boolean update = lambdaUpdate().eq(User::getId, pwdDto.getId())
                .set(User::getPassword, HashKit.md5(pwdDto.getNewPassword())).update();
        return ApiResponse.result(update,"重置成功","重置失败");
    }
}
