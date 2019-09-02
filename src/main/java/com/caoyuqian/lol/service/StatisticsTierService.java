package com.caoyuqian.lol.service;

import com.caoyuqian.lol.model.StatisticsTier;
import com.caoyuqian.lol.repository.StatisticsTierRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
@Slf4j
public class StatisticsTierService {

    @Autowired
    private StatisticsTierRepository repository;

    public Flux<StatisticsTier> saveAll(List<StatisticsTier> statisticsTiers) {

        return repository
                .findAll()
                .flatMap(statisticsTier -> {
                    log.info("查询:{}",statisticsTier.toString());
                    StatisticsTier st = statisticsTiers.stream()
                            .filter(s -> statisticsTier.getLevel().equals(s.getLevel()))
                            .findAny()
                            .get();
                    log.info("集合中匹配到的元素：{}",st.toString());
                    st.setId(statisticsTier.getId());
                    return repository.save(st);
                }).switchIfEmpty(repository.saveAll(statisticsTiers));
    }

    public Mono<StatisticsTier> save(StatisticsTier statisticsTier) {
        return repository.findByLevel(statisticsTier.getLevel())
                .flatMap(e -> {
                    log.info("查询：{}", e.toString());
                    statisticsTier.setId(e.getId());
                    return repository.save(statisticsTier);
                }).defaultIfEmpty(statisticsTier)
                .flatMap(s -> repository.save(statisticsTier));

    }
    public Flux<StatisticsTier> findAll(){
        return repository.findAll();
    }
}
