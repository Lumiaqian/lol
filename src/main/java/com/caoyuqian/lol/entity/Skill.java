package com.caoyuqian.lol.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 技能
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Skill {

    private String cdTime; //冷却时间

    private String manaCost; //法力值消耗

    private String attackRange; //攻击范围

    private Integer skillType; //技能类型 : 1.被动技能  2.主动技能

}
