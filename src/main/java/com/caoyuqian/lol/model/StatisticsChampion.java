package com.caoyuqian.lol.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

/**
 * @author qian
 * @version V1.0
 * @Title: StatisticsChampion
 * @Package: com.caoyuqian.lol.model
 * @Description: 英雄胜率
 * @date 2019-08-27 14:32
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StatisticsChampion implements Serializable {

    @Id
    private String id;
    /**
     * 排名
     */
    private int rank;
    /**
     * 英雄名称
     */
    private String championName;
    /**
     * 胜率
     */
    private String lp;
    /**
     * 总场次
     */
    private int games;
    /**
     * KDA
     */
    private Double kda;
    /**
     * CS：补刀数
     */
    private Double cs;
    /**
     * 金币
     */
    private int gold;
}
