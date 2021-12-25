package com.zdk.service;

import com.github.pagehelper.PageInfo;
import com.zdk.model.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zdk.model.dto.*;
import com.zdk.utils.ApiResponse;

/**
 * @author zdk
 * @since 2021-12-25
 */
public interface UserService extends IService<User> {
    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    ApiResponse login(String username,String password);

    /**
     * 员工信息分页
     * @param pageDto
     * @return
     */
    PageInfo<User> getUserPage(PageDto pageDto);

    /**
     * 添加员工
     * @param addUserDto
     * @return
     */
    ApiResponse addUser(AddUserDto addUserDto);


    /**
     * 更新员工信息
     * @param updateUserDto
     * @return
     */
    ApiResponse updateUser(UpdateUserDto updateUserDto);

    /**
     * 修改员工账号状态:(离职、禁用) 可用
     * @param changeStateDto
     * @return
     */
    ApiResponse changeState(ChangeStateDto changeStateDto);

    /**
     * 注册
     * @param registerDto
     * @return
     */
    ApiResponse register(AddUserDto registerDto);
}
