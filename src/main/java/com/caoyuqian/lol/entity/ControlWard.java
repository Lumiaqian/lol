package com.caoyuqian.lol.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author qian
 * @version V1.0
 * @Title: ControlWard
 * @Package: com.caoyuqian.lol.entity
 * @Description: 眼信息
 * @date 2019/9/2 4:08 下午
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ControlWard {
    /**
     * 本局买的控制守卫数量
     */
    private int buy;
    /**
     * 本局放置控制守卫数量
     */
    private int place;
    /**
     * 本局清除的控制守卫数量
     */
    private int clear;
}
