package com.caoyuqian.lol.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author qian
 * @version V1.0
 * @Title: GameAverageStats
 * @Package: com.caoyuqian.lol.entity
 * @Description: 游戏平均统计数据
 * @date 2019/9/2 5:58 下午
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GameAverageStats {
    /**
     * 近20场比赛胜场
     */
    private int win;
    /**
     * 近20场比赛败场
     */
    private int lose;
    /**
     * 近20场比赛胜率
     */
    private String winRatio;
    /**
     * 近20场比赛平均KDA数据
     */
    private Kda kda;
}
