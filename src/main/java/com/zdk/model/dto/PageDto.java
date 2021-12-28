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
 * @date 2021/12/25 19:03
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class PageDto implements Serializable {
    @NotNull
    private Integer pageNumber;
    @NotNull
    private Integer pageSize;
    private String keywords;
    private String date;
}
