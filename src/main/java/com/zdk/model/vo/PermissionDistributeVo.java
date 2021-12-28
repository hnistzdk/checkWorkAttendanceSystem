package com.zdk.model.vo;

import com.zdk.model.Permission;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Description
 * @Author zdk
 * @Date 2021/12/28 15:04
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class PermissionDistributeVo extends Permission implements Serializable {
    @ApiModelProperty("是否被勾选")
    private boolean isChecked=false;
}
