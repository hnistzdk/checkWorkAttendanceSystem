package com.zdk.controllers.controller;


import cn.hutool.json.JSONObject;
import com.github.pagehelper.PageInfo;
import com.zdk.controllers.BaseController;
import com.zdk.interceptor.PermissionInfo;
import com.zdk.model.CheckInfo;
import com.zdk.model.dto.ClockInDto;
import com.zdk.model.dto.PageDto;
import com.zdk.service.CheckInfoService;
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
@Api("考勤信息")
@RestController
@RequestMapping("/checkInfo")
@CrossOrigin
public class CheckInfoController extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(CheckInfoController.class);

    @Autowired
    private CheckInfoService checkInfoService;

    @ApiOperation("考勤信息列表")
    @PermissionInfo
    @GetMapping("/list")
    public ApiResponse checkInfoList(PageDto pageDto){
        PageInfo<CheckInfo> checkInfoList = checkInfoService.getCheckInfoPage(pageDto,getLoginUser());
        JSONObject data = new JSONObject();
        data.set("page", checkInfoList);
        data.set("date", pageDto.getDate());
        return ApiResponse.success(data);
    }

    @ApiOperation("打卡接口")
    @PermissionInfo
    @PostMapping("/clockIn")
    public ApiResponse clockIn(@RequestBody ClockInDto clockInDto){
        return checkInfoService.clockIn(clockInDto);
    }

}

