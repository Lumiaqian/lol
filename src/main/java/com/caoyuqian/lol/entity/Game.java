package com.caoyuqian.lol.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.HashMap;
import java.util.List;

/**
 * @author qian
 * @version V1.0
 * @Title: Game
 * @Package: com.caoyuqian.lol.entity
 * @Description: 游戏记录
 * @date 2019/9/2 4:16 下午
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Game {


    private String gameId;
    /**
     * 游戏模式
     */
    private String gameType;

    /**
     * 游戏时间
     */
    private long gameTime;
    /**
     * 胜利方玩家数据：包括玩家数据和红蓝方
     */
    private HashMap<String,Object> win;
    /**
     * s失利方玩家：包括玩家数据和红蓝方
     */
    private HashMap<String,Object> loss;
    /**
     * 游戏时长
     */
    private String gameLength;
    /**
     * 胜利方团队数据
     */
    private GameMapData winData;
    /**
     * 失利方团队数据
     */
    private GameMapData loseData;
}
