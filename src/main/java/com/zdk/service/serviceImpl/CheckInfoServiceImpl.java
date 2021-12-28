package com.zdk.service.serviceImpl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zdk.mapper.CheckInfoMapper;
import com.zdk.model.CheckInfo;
import com.zdk.model.User;
import com.zdk.model.dto.PageDto;
import com.zdk.service.CheckInfoService;
import com.zdk.service.UserService;
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
                    .like(CheckInfo::getInfoTime,pageDto.getDate().substring(0, 11).trim())
                    .like(StringUtils.isNotBlank(keywords), CheckInfo::getStaffName, keywords)
                    .list();
        }else{
            checkInfoList = lambdaQuery()
                    .like(CheckInfo::getInfoTime,pageDto.getDate().substring(0, 11).trim())
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
                    .setInfoTime(DateUtil.now());
            checkInfoList.add(checkInfo);
        }
        boolean saveBatch = saveBatch(checkInfoList);
        log.debug("批量生成考勤信息结果->"+saveBatch);
    }
}
