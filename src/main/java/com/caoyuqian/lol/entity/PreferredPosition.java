package com.caoyuqian.lol.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author qian
 * @version V1.0
 * @Title: PreferredPosition
 * @Package: com.caoyuqian.lol.entity
 * @Description: 擅长的位置
 * @date 2019/9/2 6:06 下午
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PreferredPosition {

    /**
     * 位置
     */
    private String position;
    /**
     * 选取率
     */
    private int roleRate;
    /**
     * 胜率
     */
    private int winRatio;

}
