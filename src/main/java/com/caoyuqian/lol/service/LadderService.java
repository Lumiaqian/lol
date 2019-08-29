package com.caoyuqian.lol.service;

import com.caoyuqian.lol.entity.Ladder;
import com.caoyuqian.lol.model.StatisticsTier;
import com.caoyuqian.lol.repository.LadderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.LongConsumer;

/**
 * @author qian
 * @version V1.0
 * @Title: LadderService
 * @Package: com.caoyuqian.lol.service
 * @Description: TOTO
 * @date 2019-08-29 11:38
 **/
@Service
@Slf4j
public class LadderService {
    @Autowired
    private LadderRepository repository;

    public Mono<String> saveAll(List<Ladder> ladderList) {

       return Mono.just("save ladderList")
               .doFirst(() -> repository.deleteAll().subscribe())
               .doFinally(signalType -> repository.saveAll(ladderList).subscribe());
    }
}
