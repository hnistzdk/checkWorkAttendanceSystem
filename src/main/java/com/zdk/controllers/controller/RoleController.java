package com.zdk.controllers.controller;


import com.github.pagehelper.PageInfo;
import com.zdk.controllers.BaseController;
import com.zdk.interceptor.PermissionInfo;
import com.zdk.model.Role;
import com.zdk.model.User;
import com.zdk.model.dto.AddUserDto;
import com.zdk.model.dto.PageDto;
import com.zdk.model.dto.UpdateUserDto;
import com.zdk.service.RoleService;
import com.zdk.utils.ApiResponse;
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
@Api("角色模块")
@RestController
@RequestMapping("/role")
public class RoleController extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private RoleService roleService;

    @ApiOperation("角色列表接口")
    @PermissionInfo("角色列表")
    @GetMapping("/list")
    public ApiResponse roleList(PageDto pageDto){
        PageInfo<Role> roleList = roleService.getRolePage(pageDto);
        return ApiResponse.success(roleList);
    }

    @ApiOperation("添加角色接口")
    @PermissionInfo("添加角色")
    @PostMapping("/add")
    public ApiResponse addRole(@RequestBody Role role){
        if (notOk(role)){
            return ApiResponse.fail("参数错误");
        }
        return roleService.addRole(role);
    }

    @ApiOperation("修改角色信息接口")
    @PermissionInfo("删除角色")
    @PostMapping("/update")
    public ApiResponse updateRole(@RequestBody Role role){
        if (notOk(role)){
            return ApiResponse.fail("参数错误");
        }
        return roleService.updateRole(role);
    }

    @ApiOperation("获取角色信息")
    @PermissionInfo("角色信息")
    @PostMapping("/edit/{id}")
    public ApiResponse editRole(@PathVariable Integer id){
        if (notOk(id)){
            return ApiResponse.fail("参数错误");
        }
        return ApiResponse.success(roleService.getById(id));
    }

    @ApiOperation("删除角色信息接口")
    @PermissionInfo("删除角色")
    @PostMapping("/delete/{id}")
    public ApiResponse deleteRole(@PathVariable Integer id){
        if (notOk(id)){
            return ApiResponse.fail("参数错误");
        }
        boolean remove = roleService.removeById(id);
        return ApiResponse.result(remove, "删除角色成功","删除角色失败");
    }
}

