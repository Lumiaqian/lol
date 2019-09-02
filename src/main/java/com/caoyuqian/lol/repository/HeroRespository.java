package com.caoyuqian.lol.repository;

import com.caoyuqian.lol.entity.Hero;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HeroRespository extends ReactiveMongoRepository<Hero,String> {
}
