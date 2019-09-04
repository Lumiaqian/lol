package com.caoyuqian.lol.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author qian
 * @version V1.0
 * @Title: GameParams
 * @Package: com.caoyuqian.lol.entity
 * @Description: 查询Game所用的参数
 * @date 2019/9/3 3:40 下午
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GameParams {

    /**
     * 召唤师Id
     */
    private String summonerId;
    /**
     * 时间
     */
    private long gameTime;
    /**
     * Id
     */
    private String gameId;
    /**
     * 游戏时长
     */
    private String gameLength;
}
