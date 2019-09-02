package com.caoyuqian.lol.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author qian
 * @version V1.0
 * @Title: GameMapData
 * @Package: com.caoyuqian.lol.entity
 * @Description: 团队数据
 * @date 2019/9/2 4:59 下午
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GameMapData {
    /**
     * 大龙数
     */
    private int baron;
    /**
     * 小龙数
     */
    private int dragon;
    /**
     * 摧毁防御塔数
     */
    private int tower;
    /**
     * 团队击杀
     */
    private int totalKill;
    /**
     * 团队经济
     */
    private int totalGold;
}
