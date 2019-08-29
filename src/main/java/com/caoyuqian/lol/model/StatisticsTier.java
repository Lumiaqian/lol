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
 * @Title: StatisticsTier
 * @Package: com.caoyuqian.lol.model
 * @Description: TOTO
 * @date 2019-08-27 16:34
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StatisticsTier implements Serializable {

    @Id
    private String id;
    /**
     * 段位
     */
    private String level;
    /**
     * 每个段位的召唤师数量
     */
    private int numberOfSummoners;
    /**
     * 每个段位的召唤师百分比
     */
    private String summonerPercentage;

}
