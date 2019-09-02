package com.caoyuqian.lol.repository;

import com.caoyuqian.lol.model.StatisticsChampion;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

/**
 * @author lumiaqian
 */
@Repository
public interface StatisticsChampionRepository extends ReactiveMongoRepository<StatisticsChampion, String> {

    /**
     * @param r1 起始位置
     * @param r2 结束位置
     * @return Flux<StatisticsChampion>
     */
    Flux<StatisticsChampion> findAllByRankBetweenOrderByRank(int r1,int r2);
}
