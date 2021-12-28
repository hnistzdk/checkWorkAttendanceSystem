package com.zdk.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author zdk
 * @since 2021-12-25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("check_info")
@Accessors(chain = true)
public class CheckInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * userid
     */
    private Integer staffId;

    /**
     * true_name
     */
    private String staffName;

    /**
     * 上班打卡时间
     */
    private String checkTime;

    /**
     * 下班打卡时间
     */
    private String leaveTime;

    /**
     * 这条打卡信息属于的日期
     */
    private String infoTime;

    /**
     * 是否迟到
     */
    private Boolean isLate;

    /**
     * 是否早退
     */
    private Boolean isLeaveEarly;

    /**
     * 是否缺勤
     */
    private Boolean absent;

}
