package com.caoyuqian.lol.repository;

import com.caoyuqian.lol.entity.SummonerSkill;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SummonerSkillRespository extends ReactiveMongoRepository<SummonerSkill,String> {
}
