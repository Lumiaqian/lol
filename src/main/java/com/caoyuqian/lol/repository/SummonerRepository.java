package com.caoyuqian.lol.repository;

import com.caoyuqian.lol.entity.Summoner;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

/**
 * @author lumiaqian
 */
@Repository
public interface SummonerRepository extends ReactiveMongoRepository<Summoner,String> {


}
