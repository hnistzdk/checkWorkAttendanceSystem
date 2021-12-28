package com.zdk.service;

import com.github.pagehelper.PageInfo;
import com.zdk.model.CheckInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zdk.model.User;
import com.zdk.model.dto.PageDto;

/**
 * @author zdk
 * @since 2021-12-25
 */
public interface CheckInfoService extends IService<CheckInfo> {
    /**
     * 打卡分页
     * @param pageDto
     * @param loginUser
     * @return
     */
    PageInfo<CheckInfo> getCheckInfoPage(PageDto pageDto, User loginUser);

    /**
     * 生成考勤初始数据
     */
    void generateCheckInfo();
}
