package com.zdk.service.serviceImpl;

import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zdk.model.CheckInfo;
import com.zdk.mapper.CheckInfoMapper;
import com.zdk.model.User;
import com.zdk.model.dto.PageDto;
import com.zdk.service.CheckInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zdk
 * @since 2021-12-25
 */
@Service
public class CheckInfoServiceImpl extends ServiceImpl<CheckInfoMapper, CheckInfo> implements CheckInfoService {

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
                    .like(CheckInfo::getCheckTime,pageDto.getDate().substring(0, 11))
                    .like(StringUtils.isNotBlank(keywords), CheckInfo::getStaffName, keywords)
                    .list();
        }else{
            checkInfoList = new ArrayList<>();
        }
        return new PageInfo<>(checkInfoList);
    }
}
