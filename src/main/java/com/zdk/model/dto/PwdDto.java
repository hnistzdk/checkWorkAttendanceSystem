package com.zdk.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
    @NotNull
    private Integer id;
    @NotBlank
    private String oldPassword;
    @NotBlank
    private String newPassword;
}
