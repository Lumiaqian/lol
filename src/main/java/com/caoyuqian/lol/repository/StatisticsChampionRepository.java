package com.caoyuqian.lol.repository;

import com.caoyuqian.lol.model.StatisticsChampion;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author lumiaqian
 */
@Repository
public interface StatisticsChampionRepository extends ReactiveMongoRepository<StatisticsChampion, String> {

}
