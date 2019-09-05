package com.caoyuqian.lol.entity.goods;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Goods implements Serializable {


    private String name; //装备名称

    private String price; //装备总价

    private String attribute; //装备属性

    private String effect; //装备效果（主动效果 or 被动效果）

    private String imageUrl; //装备图片


}
