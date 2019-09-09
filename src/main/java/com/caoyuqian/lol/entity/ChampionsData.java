package com.caoyuqian.lol.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;

/**
 * @author qian
 * @version V1.0
 * @Title: ChampionsData
 * @Package: com.caoyuqian.lol.entity
 * @Description: 召唤师英雄数据
 * @date 2019/9/9 1:51 下午
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChampionsData {

    /**
     * 召唤师名称
     */
    private String summoner;
    /**
     * 英雄名称
     */
    private String champion;
    /**
     * 英雄头像
     */
    private String championIcon;
    /**
     * 游戏数据
     */
    private HashMap<String,Object> gameData;
    /**
     * kda数据
     */
    private Kda kda;
    /**
     * 金币
     */
    private int gold;
    /**
     * 平均每局补刀数
     */
    private double cs;
    /**
     * 最高击杀数
     */
    private int highestKill;
    /**
     * 最高死亡数
     */
    private int highestDeath;
    /**
     * 平均伤害输出
     */
    private int dps;
    /**
     * 平均承受伤害
     */
    private int bear;
    /**
     * 双杀次数
     */
    private int doubleKill;
    /**
     * 三杀次数
     */
    private int tripleKill;
    /**
     * 四杀次数
     */
    private int quadraKill;
    /**
     * 五杀次数
     */
    private int pentaKill;

}
