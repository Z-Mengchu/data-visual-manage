package com.ruoyi.sales.dto;

import com.ruoyi.common.core.domain.entity.SysUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDataFilterQueryParams {
    private SysUser currentUser;
    private List<String> postCodes;
    private List<String> roleKeys;
}
