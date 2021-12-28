package com.zdk.service.serviceImpl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zdk.mapper.CheckInfoMapper;
import com.zdk.model.CheckInfo;
import com.zdk.model.User;
import com.zdk.model.dto.ClockInDto;
import com.zdk.model.dto.PageDto;
import com.zdk.service.CheckInfoService;
import com.zdk.service.UserService;
import com.zdk.utils.ApiResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zdk
 * @since 2021-12-25
 */
@Service
public class CheckInfoServiceImpl extends ServiceImpl<CheckInfoMapper, CheckInfo> implements CheckInfoService {

    @Autowired
    private UserService userService;

    @Override
    public PageInfo<CheckInfo> getCheckInfoPage(PageDto pageDto, User loginUser) {
        Integer pageNumber = pageDto.getPageNumber();
        Integer pageSize = pageDto.getPageSize();
        String keywords = pageDto.getKeywords();
        PageHelper.startPage(pageNumber, pageSize);
        List<CheckInfo> checkInfoList;
        if (StringUtils.isBlank(pageDto.getDate())){
            pageDto.setDate(DateUtil.now());
        }
        if (loginUser != null){
            checkInfoList = lambdaQuery().eq("普通用户".equals(loginUser.getRole()),CheckInfo::getStaffId,loginUser.getId())
                    .like(CheckInfo::getInfoTime,pageDto.getDate().substring(0, 10).trim())
                    .like(StringUtils.isNotBlank(keywords), CheckInfo::getStaffName, keywords)
                    .list();
        }else{
            checkInfoList = lambdaQuery()
                    .like(CheckInfo::getInfoTime,pageDto.getDate().substring(0, 10).trim())
                    .like(StringUtils.isNotBlank(keywords), CheckInfo::getStaffName, keywords)
                    .list();
        }
        return new PageInfo<>(checkInfoList);
    }

    @Override
    public void generateCheckInfo() {
        List<CheckInfo> checkInfoList = new ArrayList<>();
        List<User> userList = userService.lambdaQuery().eq(User::getState, true).list();
        for (User user : userList) {
            CheckInfo checkInfo = new CheckInfo();
            checkInfo.setStaffId(user.getId())
                    .setStaffName(user.getTrueName())
                    .setInfoTime(DateUtil.now())
                    .setAbsent(false);
            checkInfoList.add(checkInfo);
        }
        boolean saveBatch = saveBatch(checkInfoList);
        log.debug("批量生成考勤信息结果->"+saveBatch);
    }

    @Override
    public ApiResponse adminClockIn(ClockInDto clockInDto) {
        if (clockInDto==null || clockInDto.getId()==null){
            return ApiResponse.fail("参数错误");
        }
        String checkTime = clockInDto.getCheckTime();
        String leaveTime = clockInDto.getLeaveTime();
        LambdaUpdateChainWrapper<CheckInfo> lambdaUpdate = lambdaUpdate().eq(CheckInfo::getId, clockInDto.getId());
        lambdaUpdate
                .set(StringUtils.isNotBlank(checkTime),CheckInfo::getCheckTime,checkTime)
                .set(StringUtils.isNotBlank(leaveTime),CheckInfo::getLeaveTime,leaveTime);
        if (StringUtils.isNotBlank(checkTime)){
            DateTime checkDateTime = DateUtil.parseDate(checkTime);
            if (checkDateTime.getHours()>9){
                lambdaUpdate.set(CheckInfo::getIsLate, true);
            }else {
                lambdaUpdate.set(CheckInfo::getIsLate, false);
            }
        }
        if (StringUtils.isNotBlank(leaveTime)){
            DateTime leaveDateTime = DateUtil.parseDate(leaveTime);
            if (leaveDateTime.getHours()<21){
                lambdaUpdate.set(CheckInfo::getIsLeaveEarly, true);
            }else {
                lambdaUpdate.set(CheckInfo::getIsLeaveEarly, false);
            }
        }
        boolean update = lambdaUpdate.update();
        return ApiResponse.result(update, "打卡成功", "打卡失败");
    }

    @Override
    public ApiResponse clockIn(ClockInDto clockInDto) {
        if (clockInDto==null || clockInDto.getId()==null){
            return ApiResponse.fail("参数错误");
        }
        String checkTime = clockInDto.getCheckTime();
        String leaveTime = clockInDto.getLeaveTime();
        LambdaUpdateChainWrapper<CheckInfo> lambdaUpdate = lambdaUpdate().eq(CheckInfo::getStaffId, clockInDto.getId());
        lambdaUpdate
                .set(StringUtils.isNotBlank(checkTime),CheckInfo::getCheckTime,checkTime)
                .set(StringUtils.isNotBlank(leaveTime),CheckInfo::getLeaveTime,leaveTime);
        if (StringUtils.isNotBlank(checkTime)){
            DateTime checkDateTime = DateUtil.parseDate(checkTime);
            if (checkDateTime.getHours()>9){
                lambdaUpdate.set(CheckInfo::getIsLate, true);
            }else {
                lambdaUpdate.set(CheckInfo::getIsLate, false);
            }
        }
        if (StringUtils.isNotBlank(leaveTime)){
            DateTime leaveDateTime = DateUtil.parseDate(leaveTime);
            if (leaveDateTime.getHours()<21){
                lambdaUpdate.set(CheckInfo::getIsLeaveEarly, true);
            }else {
                lambdaUpdate.set(CheckInfo::getIsLeaveEarly, false);
            }
        }
        boolean update = lambdaUpdate.update();
        return ApiResponse.result(update, "打卡成功", "打卡失败");
    }

    @Override
    public ApiResponse getCheckInfoToday(Integer id) {
        CheckInfo checkInfo = lambdaQuery().eq(CheckInfo::getStaffId, id)
                .like(CheckInfo::getInfoTime, DateUtil.now().substring(0, 10).trim()).one();
        return checkInfo==null ? ApiResponse.fail("没有记录") : ApiResponse.success(checkInfo);
    }

    @Override
    public void checkAbsent() {
        String yesterday = DateUtil.yesterday().toString().substring(0, 10);
        List<CheckInfo> list = lambdaQuery().like(CheckInfo::getInfoTime, yesterday).list();
        for (CheckInfo checkInfo : list) {
            if (StringUtils.isBlank(checkInfo.getCheckTime())
            &&StringUtils.isBlank(checkInfo.getLeaveTime())){
                checkInfo.setAbsent(true);
            }
        }
        boolean updateBatchById = updateBatchById(list);
        log.debug("更新缺勤信息->"+updateBatchById);
    }
}
