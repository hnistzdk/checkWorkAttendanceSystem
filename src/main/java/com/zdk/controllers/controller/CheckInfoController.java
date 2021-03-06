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
    @PermissionInfo("考勤信息列表")
    @GetMapping("/list")
    public ApiResponse checkInfoList(PageDto pageDto){
        PageInfo<CheckInfo> checkInfoList = checkInfoService.getCheckInfoPage(pageDto,getLoginUser());
        JSONObject data = new JSONObject();
        data.set("page", checkInfoList);
        data.set("date", pageDto.getDate());
        return ApiResponse.success(data);
    }

    @ApiOperation("员工打卡")
    @PermissionInfo("员工打卡")
    @PostMapping("/clockIn")
    public ApiResponse clockIn(@RequestBody ClockInDto clockInDto){
        return checkInfoService.clockIn(clockInDto);
    }

    @ApiOperation("管理员帮助打卡")
    @PermissionInfo("管理员打卡")
    @PostMapping("/adminClockIn")
    public ApiResponse adminClockIn(@RequestBody ClockInDto clockInDto){
        return checkInfoService.adminClockIn(clockInDto);
    }

    @ApiOperation("获取用户当天打卡信息")
    @PermissionInfo("用户当天打卡信息")
    @GetMapping("/{id}")
    public ApiResponse getCheckInfo(@PathVariable Integer id){
        if (notOk(id)){
            return ApiResponse.fail("参数错误");
        }
        return checkInfoService.getCheckInfoToday(id);
    }

    @ApiOperation("删除打卡信息")
    @PermissionInfo("删除打卡信息")
    @GetMapping("/delete/{id}")
    public ApiResponse deleteCheckInfo(@PathVariable Integer id){
        if (notOk(id)){
            return ApiResponse.fail("参数错误");
        }
        boolean remove = checkInfoService.removeById(id);
        return ApiResponse.result(remove, "删除考勤信息成功", "删除考勤信息失败");
    }

    @ApiOperation("管理员修改打卡信息")
    @PermissionInfo("修改打卡信息")
    @PostMapping("/update")
    public ApiResponse update(@RequestBody ClockInDto clockInDto){
        return checkInfoService.adminClockIn(clockInDto);
    }
}

