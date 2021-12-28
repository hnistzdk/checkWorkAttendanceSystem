package com.zdk.controllers.controller;


import cn.hutool.core.bean.BeanUtil;
import com.github.pagehelper.PageInfo;
import com.zdk.controllers.BaseController;
import com.zdk.interceptor.PermissionInfo;
import com.zdk.model.Permission;
import com.zdk.model.Role;
import com.zdk.model.dto.PageDto;
import com.zdk.model.dto.PermissionDistributeDto;
import com.zdk.model.vo.PermissionDistributeVo;
import com.zdk.service.PermissionService;
import com.zdk.service.RoleService;
import com.zdk.utils.ApiResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @Autowired
    private RoleService roleService;

    @ApiOperation("权限列表接口")
    @PermissionInfo("权限列表")
    @GetMapping("/list")
    public ApiResponse roleList(PageDto pageDto){
        PageInfo<Permission> roleList = permissionService.getPermissionPage(pageDto);
        return ApiResponse.success(roleList);
    }

    @ApiOperation("分配权限列表")
    @PermissionInfo("分配权限列表")
    @GetMapping("/distributeList/{id}")
    public ApiResponse distributeList(@PathVariable Integer id){
        Role role = roleService.getById(id);
        String permissionId = role.getPermissionId();
        if (notOk(permissionId)){
            List<Permission> permissionList = permissionService.list();
            List<PermissionDistributeVo> permissionDistributeVos = BeanUtil.copyToList(permissionList, PermissionDistributeVo.class);
            return ApiResponse.success(permissionDistributeVos);
        }
        String[] permissionIds = permissionId.split(",");
        List<Permission> permissionList = permissionService.list();
        List<PermissionDistributeVo> permissionDistributeVos = BeanUtil.copyToList(permissionList, PermissionDistributeVo.class);
        for (String pid : permissionIds) {
            for (PermissionDistributeVo permissionDistributeVo : permissionDistributeVos) {
                if (permissionDistributeVo.getId().equals(Integer.parseInt(pid))) {
                    permissionDistributeVo.setChecked(true);
                    break;
                }
            }
        }
        return ApiResponse.success(permissionDistributeVos);
    }

    @ApiOperation("进行权限分配")
    @PermissionInfo("权限分配")
    @PostMapping("/distribute")
    public ApiResponse distribute(@RequestBody PermissionDistributeDto permissionDistributeDto){
        logger.debug("数据->{}", permissionDistributeDto);
        return roleService.permissionDistribute(permissionDistributeDto);
    }
}

