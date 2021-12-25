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
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author zdk
 * @since 2021-12-25
 */
@RestController
@RequestMapping("/role")
public class RoleController extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private RoleService roleService;

    @ApiOperation("角色列表接口")
    @PermissionInfo
    @GetMapping("/list")
    public ApiResponse roleList(PageDto pageDto){
        PageInfo<Role> roleList = roleService.getRolePage(pageDto);
        return ApiResponse.success(roleList);
    }

    @ApiOperation("添加角色接口")
    @PermissionInfo
    @PostMapping("/add")
    public ApiResponse addUser(@RequestBody Role role){
        if (notOk(role)){
            return ApiResponse.fail("参数错误");
        }
        return roleService.addRole(role);
    }

    @ApiOperation("修改角色信息接口")
    @PermissionInfo
    @PostMapping("/update")
    public ApiResponse updateUser(@RequestBody Role role){
        if (notOk(role)){
            return ApiResponse.fail("参数错误");
        }
        return roleService.updateRole(role);
    }

    @ApiOperation("删除角色信息接口")
    @PermissionInfo
    @PostMapping("/delete/{id}")
    public ApiResponse deleteUser(@PathVariable Integer id){
        if (notOk(id)){
            return ApiResponse.fail("参数错误");
        }
        boolean remove = roleService.removeById(id);
        return ApiResponse.result(remove, "删除角色成功","删除角色失败");
    }
}

