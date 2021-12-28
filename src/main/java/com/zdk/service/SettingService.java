package com.zdk.service;

import com.zdk.model.Setting;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zdk.model.dto.WorkTimeDto;
import com.zdk.utils.ApiResponse;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zdk
 * @since 2021-12-28
 */
public interface SettingService extends IService<Setting> {
    /**
     * 修改工作时间
     * @param workTimeDto
     * @return
     */
    ApiResponse updateWorkTime(WorkTimeDto workTimeDto);
}
