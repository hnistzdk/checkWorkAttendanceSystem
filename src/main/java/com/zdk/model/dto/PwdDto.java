package com.zdk.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Description
 * @Author zdk
 * @Date 2021/12/28 12:51
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class PwdDto implements Serializable {
    private Integer id;
    private String oldPassword;
    private String newPassword;
}
