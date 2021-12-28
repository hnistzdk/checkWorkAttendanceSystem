package com.zdk.controllers.controller;


import com.zdk.interceptor.PermissionInfo;
import com.zdk.model.dto.WorkTimeDto;
import com.zdk.service.SettingService;
import com.zdk.utils.ApiResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author zdk
 * @since 2021-12-28
 */
@Api("系统设置")
@RestController
@RequestMapping("/setting")
public class SettingController {
    @Autowired
    private SettingService settingService;

    @ApiOperation("系统设置列表")
    @PermissionInfo("系统设置列表")
    @GetMapping("/list")
    public ApiResponse list(){
        return ApiResponse.success(settingService.list());
    }

    @ApiOperation("修改上下班时间")
    @PermissionInfo("修改上下班时间")
    @PostMapping("/updateWorkTime")
    public ApiResponse updateWorkTime(@RequestBody WorkTimeDto workTimeDto){
        return settingService.updateWorkTime(workTimeDto);
    }
}

