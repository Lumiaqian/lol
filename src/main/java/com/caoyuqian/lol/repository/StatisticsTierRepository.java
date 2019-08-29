package com.caoyuqian.lol.repository;

import com.caoyuqian.lol.model.StatisticsTier;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @author lumiaqian
 */
@Repository
public interface StatisticsTierRepository extends ReactiveMongoRepository<StatisticsTier,String> {

    /**
      * @Param: Level
      * @return: Mono
      * @Author: qian
      * @Description: 通过段位查找数据
      * @Date: 2019-08-28 10:19
     **/
    Mono<StatisticsTier> findByLevel(String level);
}
