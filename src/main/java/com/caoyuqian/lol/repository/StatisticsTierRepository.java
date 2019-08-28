package com.caoyuqian.lol.repository;

import com.caoyuqian.lol.model.StatisticsTier;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author lumiaqian
 */
@Repository
public interface StatisticsTierRepository extends ReactiveMongoRepository<StatisticsTier,Long> {
}
