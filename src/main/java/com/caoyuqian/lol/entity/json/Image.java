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
public class Image  implements Serializable {

    private String full;

    private String sprite;

    private String group;

    private int x;

    private int y;

    private int w;

    private int h;
}
