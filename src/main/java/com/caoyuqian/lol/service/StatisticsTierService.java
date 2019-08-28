package com.caoyuqian.lol.service;

import com.caoyuqian.lol.model.StatisticsTier;
import com.caoyuqian.lol.repository.StatisticsTierRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @author qian
 * @version V1.0
 * @Title: StatisticsTierService
 * @Package: com.caoyuqian.lol.service
 * @Description: TOTO
 * @date 2019-08-27 18:05
 **/
@Service
@Slf4j
public class StatisticsTierService {

    @Autowired
    private StatisticsTierRepository repository;

    public Flux<StatisticsTier> saveAll(List<StatisticsTier> statisticsTiers){
        repository.deleteAll().subscribe();
        return repository
                .saveAll(statisticsTiers);
    }

    public Mono<StatisticsTier> save(StatisticsTier statisticsTier){
        return repository
                .insert(statisticsTier);

    }
}
