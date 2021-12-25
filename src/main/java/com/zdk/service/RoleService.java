package com.zdk.service;

import com.github.pagehelper.PageInfo;
import com.zdk.model.Role;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zdk.model.User;
import com.zdk.model.dto.AddUserDto;
import com.zdk.model.dto.PageDto;
import com.zdk.model.dto.UpdateUserDto;
import com.zdk.utils.ApiResponse;

/**
 * @author zdk
 * @since 2021-12-25
 */
public interface RoleService extends IService<Role> {
    /**
     * 角色信息分页
     * @param pageDto
     * @return
     */
    PageInfo<Role> getRolePage(PageDto pageDto);

    /**
     * 添加角色
     * @param role
     * @return
     */
    ApiResponse addRole(Role role);


    /**
     * 更新角色信息
     * @param role
     * @return
     */
    ApiResponse updateRole(Role role);

}
