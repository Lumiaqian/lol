package com.caoyuqian.lol.entity.json;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SummonerSkillOne implements Serializable {

    private String id;

    private String name;

    private String description;

    private int maxrank;

    private String key;

    private Image image;


}
