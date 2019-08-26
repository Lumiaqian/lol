package com.caoyuqian.lol.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Hero {

    private String HName; //英雄名称

    private String Image; //英雄图片

    private List<String>  position; //英雄位置



}
