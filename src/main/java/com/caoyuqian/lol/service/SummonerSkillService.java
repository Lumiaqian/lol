package com.caoyuqian.lol.service;

import com.caoyuqian.lol.entity.SummonerSkill;
import com.caoyuqian.lol.repository.SummonerSkillRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class SummonerSkillService {

    @Autowired
    private SummonerSkillRespository summonerSkillRespository;

    /**
     * 存入所有爬取的召唤师技能信息
     * @param summonerSkills
     * @return
     */
    public Mono<String> saveAll(List<SummonerSkill> summonerSkills){
        return Mono.just("save SummonerSkills ")
                .doFirst(()->summonerSkillRespository.deleteAll().subscribe())
                .doFinally(signalType -> summonerSkillRespository.saveAll(summonerSkills).subscribe());

    }

    /**
     * 查询所有召唤师技能信息
     * @return
     */
    public Flux<SummonerSkill> findAll(){



        return  summonerSkillRespository.findAll();
    }
}
