package com.zdk.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zdk.model.User;
import org.springframework.stereotype.Repository;

/**
 * @author zdk
 * @since 2021-12-25
 */
@Repository
public interface UserMapper extends BaseMapper<User> {

}
