package com.caoyuqian.lol.service;

import com.caoyuqian.lol.entity.Summoner;
import com.caoyuqian.lol.model.StatisticsTier;
import com.caoyuqian.lol.repository.StatisticsTierRepository;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Subscriber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author qian
 * @version V1.0
 * @Title: StatisticsTierService
 * @Package: com.caoyuqian.lol.service
 * @Description: TOTO
 * @date 2019-08-27 18:05
 **/
@Service
public class StatisticsTierService {
    private final static Logger log = LoggerFactory.getLogger(StatisticsTierService.class);
    @Autowired
    private StatisticsTierRepository repository;
    @Autowired
    private ReactiveMongoTemplate template;

    public Mono<String> saveAll(List<StatisticsTier> statisticsTiers) {
        statisticsTiers.forEach(statisticsTier -> statisticsTier.setVersion(1));
        return findLatelyVersion().flatMap(statisticsTier -> {
            long version = statisticsTier.getVersion();
            //设置新版本号
            statisticsTiers.forEach(statisticsTier1 -> statisticsTier1.setVersion(version+1));
            return repository
                    .saveAll(statisticsTiers)
                    .collectList()
                    .flatMap(statisticsTiers1 -> Mono.just("保存最新版本的召唤师数据"));
        }).switchIfEmpty(repository
                .saveAll(statisticsTiers)
                .collectList()
                .flatMap(statisticsTiers1 -> Mono.just("保存第一版本的召唤师数据")));

    }

    public Flux<StatisticsTier> findAll(){
        return repository.findAll();
    }
     /**
       * @Param:
       * @return:
       * @Author: qian
       * @Description: 查询最新版本号
       * @Date: 2019/9/11 3:26 下午
      **/
    public Mono<StatisticsTier> findLatelyVersion(){
        Sort sort = new Sort(Sort.Direction.DESC,"version");
        Query query = new Query().with(sort).limit(1);
        return template.findOne(query,StatisticsTier.class);
    }

     /**
       * @Param:
       * @return:
       * @Author: qian
       * @Description: 查询最新版本的统计数据
       * @Date: 2019/9/11 3:34 下午
      **/
    public Flux<StatisticsTier> findLatelyVersionStatisticsTier(){

        Sort sort = new Sort(Sort.Direction.DESC, "version");
        Query query = new Query().with(sort).limit(27);
        return template.find(query, StatisticsTier.class);
    }
}
