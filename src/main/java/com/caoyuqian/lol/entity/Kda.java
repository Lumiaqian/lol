package com.caoyuqian.lol.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author qian
 * @version V1.0
 * @Title: Kda
 * @Package: com.caoyuqian.lol.entity
 * @Description: KDA数据
 * @date 2019/9/2 4:04 下午
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Kda {

    /**
     * 本局击杀数
     */
    private double kill;
    /**
     * 本局阵亡次数
     */
    private double death;
    /**
     * 本局助攻数
     */
    private double assist;
    /**
     * 本局kda
     */
    private double kda;
    /**
     * 本局击杀贡献率CKRate
     */
    private String ckRate;
}
