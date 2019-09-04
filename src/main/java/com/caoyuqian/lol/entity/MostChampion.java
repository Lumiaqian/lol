package com.caoyuqian.lol.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author qian
 * @version V1.0
 * @Title: MostChampion
 * @Package: com.caoyuqian.lol.entity
 * @Description: 擅长的英雄
 * @date 2019/9/2 6:11 下午
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MostChampion {

    /**
     * 名称
     */
    private String name;
    /**
     * 胜率
     */
    private String image;
    /**
     * 胜率
     */
    private String winRatio;
    /**
     * 胜场
     */
    private int win;
    /**
     * 败场
     */
    private int lose;
    /**
     * KDA
     */
    private double kda;
}
