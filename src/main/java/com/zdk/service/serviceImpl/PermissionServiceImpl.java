package com.zdk.service.serviceImpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zdk.model.Permission;
import com.zdk.mapper.PermissionMapper;
import com.zdk.model.Role;
import com.zdk.model.dto.PageDto;
import com.zdk.service.PermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zdk
 * @since 2021-12-25
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

    @Override
    public PageInfo<Permission> getPermissionPage(PageDto pageDto) {
        Integer pageNumber = pageDto.getPageNumber();
        Integer pageSize = pageDto.getPageSize();
        String keywords = pageDto.getKeywords();
        PageHelper.startPage(pageNumber, pageSize);
        List<Permission> permissionList = lambdaQuery().like(StringUtils.isNotBlank(pageDto.getKeywords()), Permission::getPermissionName, keywords).list();
        return new PageInfo<>(permissionList);
    }
}
