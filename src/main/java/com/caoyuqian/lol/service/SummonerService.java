package com.caoyuqian.lol.service;

import com.caoyuqian.lol.entity.Summoner;
import com.caoyuqian.lol.repository.SummonerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @author qian
 * @version V1.0
 * @Title: SummonerService
 * @Package: com.caoyuqian.lol.service
 * @Description: TOTO
 * @date 2019/9/4 9:27 上午
 **/
@Service
public class SummonerService {
    @Autowired
    private SummonerRepository repository;

    public Mono<String> saveAll(List<Summoner> summoners){
        return repository.findAll()
                .collectList()
                .flatMap(summoners1 -> {
                    if (summoners1.size() <= 0){
                       return repository.saveAll(summoners).collectList()
                               .flatMap(summoners2 -> Mono.just("直接保存！"));
                    }else {
                        return  Mono.just("先删除再保存")
                                .doFirst(()->repository.deleteAll().subscribe())
                                .doFinally(signalType -> repository.saveAll(summoners).subscribe());
                    }
                });
    }
    public Mono<Summoner> save(Summoner summoner){
        return repository.save(summoner);
    }
}
