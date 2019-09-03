package com.caoyuqian.lol.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

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

    @Id
    private long gameId;

    /**
     * 游戏时间
     */
    private long gameTime;
    /**
     * 红方玩家
     */
    private List<Gamer> red;
    /**
     * 蓝方玩家
     */
    private List<Gamer> blue;
    /**
     * 游戏时长
     */
    private String gameLength;
    /**
     * 蓝色方团队数据
     */
    private GameMapData blueData;
    /**
     * 红色方团队数据
     */
    private GameMapData redData;
}
