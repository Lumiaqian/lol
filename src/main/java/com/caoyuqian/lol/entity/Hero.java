package com.caoyuqian.lol.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Hero implements Serializable {

    @Id
    private String id;

    private String HName; //英雄称号

    private String name;  //英雄名称

    private String image; //英雄图片

    private Boolean top; // 是否为上单

    private Boolean mid;  //是否为中单

    private Boolean jungle; //是否为打野

    private Boolean  assist;//是否为辅助

    private Boolean ad; //是否为AD

    private Skill Q; //Q技能信息

    private Skill W; //W技能信息

    private Skill E; //E技能信息

    private Skill R; //R技能信息

    private Skill bd; //被动技能信息



}
