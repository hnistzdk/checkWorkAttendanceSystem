package com.zdk.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Description
 * @Author zdk
 * @Date 2021/12/28 15:27
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class PermissionDistributeDto implements Serializable {
    @NotNull
    private Integer id;
    private String permissionIds;
}
