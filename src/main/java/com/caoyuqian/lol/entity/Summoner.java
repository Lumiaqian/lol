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



    private String summonerId;
    /**
     * 所在的职业战队
     */
    private String team;
    /**
     * 职业ID
     */
    private String careerId;
    /**
     * 本赛季总胜场
     */
    private int totalWin;
    /**
     * 本赛季总负场
     */
    private int totalLose;
    /**
     * 段位头像
     */
    private String tierIcon;
    /**
     * 最近20场平均数据
     */
    private GameAverageStats gas;
    /**
     * 最近20场突出的英雄
     */
    //private List<MostChampion> champions;
    /**
     * 最近20场所在的位置
     */
    //private List<PreferredPosition> positions;
    /**
     * 最近20场比赛的查询参数
     */
    private List<GameParams> params;

    @Override
    public String toString() {
        return "Summoner{" +
                "version=" + version +
                ", summonerId='" + summonerId + '\'' +
                ", team='" + team + '\'' +
                ", careerId='" + careerId + '\'' +
                ", totalWin=" + totalWin +
                ", totalLose=" + totalLose +
                ", tierIcon='" + tierIcon + '\'' +
                ", gas=" + gas +
                ", params=" + params +
                ", borderImage='" + borderImage + '\'' +
                ", name='" + name + '\'' +
                ", ranking=" + ranking +
                ", tier='" + tier + '\'' +
                ", lp=" + lp +
                ", winRatio='" + winRatio + '\'' +
                ", lv=" + lv +
                '}';
    }

    /**
     * @param summoner
     */
    private Summoner(Summoner summoner) {
        this.name = summoner.name;
        //this.champions = summoner.champions;
        this.gas = summoner.gas;
        //this.positions = summoner.positions;
        this.summonerId = summoner.summonerId;
        this.lp = summoner.lp;
        this.lv = summoner.lv;
        this.ranking = summoner.ranking;
        this.tier = summoner.tier;
        this.winRatio = summoner.winRatio;
        this.borderImage = summoner.borderImage;
        this.totalWin = summoner.totalWin;
        this.totalLose = summoner.totalLose;
        this.team = summoner.team;
        this.careerId = summoner.careerId;
        this.tierIcon = summoner.tierIcon;
        this.params = summoner.params;
        this.version = summoner.version;
    }


    public static class Builder {
        private Summoner summoner;

        public Builder() {
            this.summoner = new Summoner();
        }

        public Builder name(String name) {
            summoner.name = name;
            return this;
        }

        public Builder ranking(int ranking) {
            summoner.ranking = ranking;
            return this;
        }

        public Builder tier(String tier) {
            summoner.tier = tier;
            return this;
        }

        public Builder lp(int lp) {
            summoner.lp = lp;
            return this;
        }

        public Builder winRatio(String winRatio) {
            summoner.winRatio = winRatio;
            return this;
        }

        public Builder lv(int lv) {
            summoner.lv = lv;
            return this;
        }

        public Builder summonerId(String summonerId) {
            summoner.summonerId = summonerId;
            return this;
        }

        public Builder gas(GameAverageStats gas) {
            summoner.gas = gas;
            return this;
        }

        /*public Builder champions(List<MostChampion> champions) {
            summoner.champions = champions;
            return this;
        }

        public Builder positions(List<PreferredPosition> positions) {
            summoner.positions = positions;
            return this;
        }*/

        public Builder borderImage(String borderImage) {
            summoner.borderImage = borderImage;
            return this;
        }

        public Builder team(String team) {
            summoner.team = team;
            return this;
        }

        public Builder careerId(String careerId) {
            summoner.careerId = careerId;
            return this;
        }

        public Builder totalWin(int totalWin) {
            summoner.totalWin = totalWin;
            return this;
        }

        public Builder totalLose(int totalLose) {
            summoner.totalLose = totalLose;
            return this;
        }

        public Builder tierIcon(String tierIcon) {
            summoner.tierIcon = tierIcon;
            return this;
        }

        public Builder params(List<GameParams> params) {
            summoner.params = params;
            return this;
        }
        public Builder version(long version){
            summoner.version = version;
            return this;
        }

        public Summoner build() {
            return new Summoner(summoner);
        }
    }
}
