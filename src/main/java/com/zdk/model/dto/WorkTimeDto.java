package com.zdk.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * @Description
 * @Author zdk
 * @Date 2021/12/28 18:39
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class WorkTimeDto {
    @NotBlank
    private String workTime;
    @NotBlank
    private String closingTime;
}
