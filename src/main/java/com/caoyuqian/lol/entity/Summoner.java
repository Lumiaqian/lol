package com.caoyuqian.lol.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.List;

/**
 * @author qian
 * @version V1.0
 * @Title: Summoner
 * @Package: com.caoyuqian.lol.entity
 * @Description: 召唤师信息
 * @date 2019/9/2 5:24 下午
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Summoner extends Ladder {

    @Id
    private String summonerId;
    /**
     * 最近20场平均数据
     */
    private GameAverageStats gas;
    /**
     * 最近20场突出的英雄
     */
    private List<MostChampion> champions;
    /**
     * 最近20场所在的位置
     */
    private List<PreferredPosition> positions;
}
