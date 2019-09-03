package com.caoyuqian.lol.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 召唤师技能
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SummonerSkill implements Serializable {

    private String sSname ;  //技能名称

    private String sScontent; //技能简介

    private String imageURL; //图片

}
