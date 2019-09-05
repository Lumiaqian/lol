package com.caoyuqian.lol.repository;

import com.caoyuqian.lol.entity.Ladder;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


/**
 * @author lumiaqian
 */
@Repository
public interface LadderRepository extends ReactiveMongoRepository<Ladder,String> {

    /**
     * @return Flux<Ladder>
     *  返回ranking排名在a-b之间的
     */
    Flux<Ladder> findByRankingBetween(int r1,int r2);



}
