package com.caoyuqian.lol.service;

import com.caoyuqian.lol.entity.Hero;
import com.caoyuqian.lol.repository.HeroRespository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class HeroService {

    @Autowired
    private HeroRespository heroRespository;

    public Mono<String> saveAllHero(List<Hero> heroes){
        return Mono.just("save heroList")

                .doFirst(() -> heroRespository.deleteAll().subscribe())
                .doFinally(signalType -> heroRespository.saveAll(heroes).subscribe());
    }

}
