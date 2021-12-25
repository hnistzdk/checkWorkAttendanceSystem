package com.zdk.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author zdk
 * @date 2021/12/25 20:02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class UpdateUserDto implements Serializable {
    @NotNull(message = "id不能为空")
    private Integer id;
    @NotBlank(message = "真实姓名不能为空")
    private String trueName;
    @NotBlank(message = "性别不能为空")
    private String gender;
    @NotBlank(message = "邮箱不能为空")
    private String email;
}
