package com.caoyuqian.lol.entity;

import lombok.*;
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
@Getter
@Setter
@NoArgsConstructor
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

    @Override
    public String toString() {
        return "Summoner{" +
                "summonerId='" + summonerId + '\'' +
                ", gas=" + gas +
                ", champions=" + champions +
                ", positions=" + positions +
                ", name='" + name + '\'' +
                ", ranking=" + ranking +
                ", tier='" + tier + '\'' +
                ", lp='" + lp + '\'' +
                ", winRatio='" + winRatio + '\'' +
                ", lv='" + lv + '\'' +
                '}';
    }

    /**
     * @param summoner
     */
    private Summoner(Summoner summoner) {
        this.name = summoner.name;
        this.champions = summoner.champions;
        this.gas = summoner.gas;
        this.positions = summoner.positions;
        this.summonerId = summoner.summonerId;
        this.lp = summoner.lp;
        this.lv = summoner.lv;
        this.ranking = summoner.ranking;
        this.tier = summoner.tier;
        this.winRatio = summoner.winRatio;
    }


    public static class Builder{
        private Summoner summoner;

        public Builder() {
            this.summoner = new Summoner();
        }
        public Builder name(String name){
            summoner.name = name;
            return this;
        }
        public Builder ranking(int ranking){
            summoner.ranking = ranking;
            return this;
        }
        public Builder tier(String tier){
            summoner.tier = tier;
            return this;
        }
        public Builder lp(String lp){
            summoner.lp = lp;
            return this;
        }
        public Builder winRatio(String winRatio){
            summoner.winRatio = winRatio;
            return this;
        }
        public Builder lv(String lv){
            summoner.lv = lv;
            return this;
        }
        public Builder summonerId(String summonerId){
            summoner.summonerId = summonerId;
            return this;
        }
        public Builder gas(GameAverageStats gas){
            summoner.gas = gas;
            return this;
        }
        public Builder champions(List<MostChampion> champions){
            summoner.champions = champions;
            return this;
        }
        public Builder positions(List<PreferredPosition> positions){
            summoner.positions = positions;
            return this;
        }
        public Summoner build(){
            return new Summoner(summoner);
        }
    }
}
