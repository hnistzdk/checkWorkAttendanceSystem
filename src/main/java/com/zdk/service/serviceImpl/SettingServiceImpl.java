package com.zdk.service.serviceImpl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.zdk.model.CheckInfo;
import com.zdk.model.Setting;
import com.zdk.mapper.SettingMapper;
import com.zdk.model.dto.WorkTimeDto;
import com.zdk.service.CheckInfoService;
import com.zdk.service.SettingService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zdk.utils.ApiResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zdk
 * @since 2021-12-28
 */
@Service
public class SettingServiceImpl extends ServiceImpl<SettingMapper, Setting> implements SettingService {

    @Autowired
    private CheckInfoService checkInfoService;

    @Override
    public ApiResponse updateWorkTime(WorkTimeDto workTimeDto) {
        boolean workTimeUpdate = lambdaUpdate().eq(Setting::getSettingName, "workTime")
                .set(Setting::getSettingValue, workTimeDto.getWorkTime())
                .update();
        boolean closingTimeUpdate = lambdaUpdate().eq(Setting::getSettingName, "closingTime")
                .set(Setting::getSettingValue, workTimeDto.getClosingTime())
                .update();
        Setting workTime = lambdaQuery().eq(Setting::getSettingName, "workTime").one();
        String workTimeSettingValue = workTime.getSettingValue();
        Setting closingTime = lambdaQuery().eq(Setting::getSettingName, "closingTime").one();
        String closingTimeSettingValue = closingTime.getSettingValue();
        if (workTimeUpdate&&closingTimeUpdate){
            List<CheckInfo> list = checkInfoService.list();
            for (CheckInfo checkInfo : list) {
                String checkTime = checkInfo.getCheckTime();
                String leaveTime = checkInfo.getLeaveTime();
                if (StringUtils.isNotBlank(checkTime)){
                    DateTime checkDateTime = DateUtil.parse(checkTime);
                    DateTime workTimeSetting = DateUtil.parse(workTimeSettingValue, "HH:mm");
                    if (checkDateTime.getHours()>workTimeSetting.getHours()){
                        checkInfo.setIsLate(true);
                    }else if (checkDateTime.getHours()==workTimeSetting.getHours()){
                        checkInfo.setIsLate(checkDateTime.getMinutes() > workTimeSetting.getMinutes());
                    }else{
                        checkInfo.setIsLate(false);
                    }
                }
                if (StringUtils.isNotBlank(leaveTime)){
                    DateTime leaveDateTime = DateUtil.parse(leaveTime);
                    DateTime closingTimeSetting = DateUtil.parse(closingTimeSettingValue, "HH:mm");
                    if (leaveDateTime.getHours()<closingTimeSetting.getHours()){
                        checkInfo.setIsLeaveEarly(true);
                    }else if (leaveDateTime.getHours()==closingTimeSetting.getHours()){
                        checkInfo.setIsLeaveEarly(leaveDateTime.getMinutes() < closingTimeSetting.getMinutes());
                    }else{
                        checkInfo.setIsLeaveEarly(false);
                    }
                }
            }
            boolean updateBatchById = checkInfoService.updateBatchById(list);
            return ApiResponse.result(updateBatchById, "更新工作时间成功", "更新工作时间失败");
        }
        return ApiResponse.fail("更新工作时间失败");
    }
}
