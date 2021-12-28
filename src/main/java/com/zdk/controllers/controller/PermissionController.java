package com.zdk.controllers.controller;


import com.github.pagehelper.PageInfo;
import com.zdk.controllers.BaseController;
import com.zdk.interceptor.PermissionInfo;
import com.zdk.model.Permission;
import com.zdk.model.Role;
import com.zdk.model.dto.PageDto;
import com.zdk.service.PermissionService;
import com.zdk.utils.ApiResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * @author zdk
 * @since 2021-12-25
 */
@Api("权限模块")
@RestController
@RequestMapping("/permission")
public class PermissionController extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(PermissionController.class);

    @Autowired
    private PermissionService permissionService;

    @ApiOperation("权限列表接口")
    @PermissionInfo
    @GetMapping("/list")
    public ApiResponse roleList(PageDto pageDto){
        PageInfo<Permission> roleList = permissionService.getPermissionPage(pageDto);
        return ApiResponse.success(roleList);
    }
}

