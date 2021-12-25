package com.zdk.service;

import com.github.pagehelper.PageInfo;
import com.zdk.model.Permission;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zdk.model.Role;
import com.zdk.model.dto.PageDto;

/**
 * @author zdk
 * @since 2021-12-25
 */
public interface PermissionService extends IService<Permission> {
    /**
     * 权限信息分页
     * @param pageDto
     * @return
     */
    PageInfo<Permission> getPermissionPage(PageDto pageDto);
}
