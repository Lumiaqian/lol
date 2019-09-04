package com.caoyuqian.lol.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author qian
 * @version V1.0
 * @Title: Gamer
 * @Package: com.caoyuqian.lol.entity
 * @Description: 游戏玩家
 * @date 2019/9/2 3:34 下午
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Gamer {
    @Id
    private String id;
    /**
     * 使用的英雄名称
     */
    private String championName;

    /**
     * 使用的英雄等级
     */

    private int level;
    /**
     * 携带的召唤师技能
     */
    private HashMap summonerSpells;
    /**
     * 携带的天赋符文
     */
    private HashMap runes;
    /**
     * 召唤师名称
     */
    private String summonerName;
    /**
     * 召唤师段位
     */
    private String tier;
    /**
     * 胜点
     */
    private int lp;
    /**
     * 本局评分
     */
    private Double opScore;
    /**
     * 本局KDA信息
     */
    private Kda kda;
    /**
     * 本局伤害
     */
    private int championDamage;
    /**
     * 本局承伤
     */
    private int championInjury;
    /**
     * 本局控制守卫信息
     */
    private ControlWard controlWard;
    /**
     * 本局补刀数
     */
    private int cs;
    /**
     * 本局每分钟补刀数
     */
    private double csPerMinute;
    /**
     * 本局装备
     */
    private List<HashMap> items;

}
