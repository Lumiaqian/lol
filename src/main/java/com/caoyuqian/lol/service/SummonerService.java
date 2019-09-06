package com.caoyuqian.lol.service;

import com.caoyuqian.lol.entity.Ladder;
import com.caoyuqian.lol.entity.Summoner;
import com.caoyuqian.lol.repository.SummonerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
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
    @Autowired
    private ReactiveMongoTemplate template;

    public Mono<String> saveAll(List<Summoner> summonerList){

        summonerList.forEach(summoner -> summoner.setVersion(1));
        return findLatelyVersion().flatMap(summoner -> {
            long version = summoner.getVersion();
            //设置最新版本号
            summonerList.forEach(summoner1 -> summoner1.setVersion(version + 1));
            return repository
                    .saveAll(summonerList)
                    .collectList()
                    .flatMap(summoners -> Mono.just("保存最新版本的召唤师数据"));
        }).switchIfEmpty(repository
                .saveAll(summonerList)
                .collectList()
                .flatMap(summoners -> Mono.just("保存第一版本的召唤师数据")));
        /*return repository.findAll()
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
                });*/
    }
   /* public Flux<Summoner> saveAll(List<Summoner> summoners){
        return repository.saveAll(summoners);
    }*/
    /**
     * @Param:
     * @return: Mono
     * @Author: qian
     * @Description: 查询数据库中最新版本号的数据
     * @Date: 2019/9/5 12:57 下午
     **/
    public Mono<Summoner> findLatelyVersion() {
        Sort sort = new Sort(Sort.Direction.DESC, "version");
        Query query = new Query().with(sort).limit(1);
        return template.findOne(query, Summoner.class);
    }

    public Flux<Summoner> findAll(){
        return repository.findAll();
    }
}
