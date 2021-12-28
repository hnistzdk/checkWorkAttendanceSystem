package com.zdk.service.serviceImpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zdk.mapper.UserMapper;
import com.zdk.model.Role;
import com.zdk.mapper.RoleMapper;
import com.zdk.model.User;
import com.zdk.model.dto.PageDto;
import com.zdk.model.dto.PermissionDistributeDto;
import com.zdk.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zdk.utils.ApiResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zdk
 * @since 2021-12-25
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public PageInfo<Role> getRolePage(PageDto pageDto) {
        Integer pageNumber = pageDto.getPageNumber();
        Integer pageSize = pageDto.getPageSize();
        String keywords = pageDto.getKeywords();
        PageHelper.startPage(pageNumber, pageSize);
        List<Role> roleList = lambdaQuery().like(StringUtils.isNotBlank(pageDto.getKeywords()), Role::getRoleName, keywords).list();
        return new PageInfo<>(roleList);
    }

    @Override
    public ApiResponse addRole(Role role) {
        String roleName = role.getRoleName();
        if (lambdaQuery().eq(Role::getRoleName, roleName).one() != null){
            return ApiResponse.fail("该角色已存在,请重新输入");
        }
        int insert = roleMapper.insert(new Role().setRoleName(role.getRoleName()).setRoleDescription(role.getRoleDescription()));
        return ApiResponse.result(insert>0,"添加角色成功","添加角色失败");
    }

    @Override
    public ApiResponse updateRole(Role role) {
        if (role.getId() == null){
            return ApiResponse.fail("参数异常");
        }
        boolean update = lambdaUpdate().eq(Role::getId, role.getId())
                .set(Role::getRoleName, role.getRoleName())
                .set(Role::getRoleDescription, role.getRoleDescription())
                .set(Role::getPermissionId, role.getPermissionId()).update();
        return ApiResponse.result(update,"更新角色信息成功","更新角色信息失败");
    }

    @Override
    public ApiResponse permissionDistribute(PermissionDistributeDto permissionDistributeDto) {
        Integer id = permissionDistributeDto.getId();
        if (id == null){
            return ApiResponse.fail("角色id不能为空");
        }
        boolean update = lambdaUpdate().eq(Role::getId, id)
                .set(Role::getPermissionId, permissionDistributeDto.getPermissionIds())
                .update();
        return ApiResponse.result(update,"更新权限成功","更新权限失败");
    }
}
