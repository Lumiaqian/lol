package com.caoyuqian.lol.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author qian
 * @version V1.0
 * @Title: Ladder
 * @Package: com.caoyuqian.lol.entity
 * @Description: 天梯榜
 * @date 2019-08-22 11:55
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ladder {
    /** 召唤师名称
     */
    String name;
    /** rank排名
     */
    String ranking;
    /** 段位
    */
    String level;
    /** rank分数
     */
    String lp;
    /** 胜率
     */
    String winRatio;
    /** 召唤师等级
     */
    String lv;
}
