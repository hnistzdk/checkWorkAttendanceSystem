package com.zdk.mapper;

import com.zdk.model.Setting;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author zdk
 * @since 2021-12-28
 */
@Repository
public interface SettingMapper extends BaseMapper<Setting> {

}
