package com.caoyuqian.lol.service;

import com.caoyuqian.lol.entity.Ladder;
import com.caoyuqian.lol.model.StatisticsTier;
import com.caoyuqian.lol.repository.LadderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
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
public class LadderService {
    @Autowired
    private LadderRepository repository;
    @Autowired
    private ReactiveMongoTemplate template;

    public Mono<String> saveAll(List<Ladder> ladderList) {
        // Optional<Ladder> optional=ladders.stream().max(Comparator.comparingLong(Ladder::getVersion));
        ladderList.forEach(ladder1 -> ladder1.setVersion(1));
        return findLatelyVersion().flatMap(ladder -> {
            long version = ladder.getVersion();
            //设置最新版本号
            ladderList.forEach(ladder1 -> ladder1.setVersion(version + 1));
            return repository
                    .saveAll(ladderList)
                    .collectList()
                    .flatMap(ladders -> Mono.just("保存最新版本的排行榜数据"));
        }).switchIfEmpty(repository
                .saveAll(ladderList)
                .collectList()
                .flatMap(ladders -> Mono.just("保存第一版本的排行榜数据")));

        /*return repository.findAll()
                .collectList()
                .flatMap(ladders -> {
                    if (ladders.size() != 0) {
                        long version = ladders.stream().max(Comparator.comparingLong(Ladder::getVersion)).get().getVersion();
                        ladderList.forEach(ladder -> ladder.setVersion(version + 1));
                        return repository
                                .saveAll(ladderList)
                                .collectList()
                                .flatMap(ladders1 -> Mono.just("保存最新版本的排行榜数据"));
                    } else {
                        ladderList.forEach(ladder -> ladder.setVersion(1));
                        return repository
                                .saveAll(ladderList)
                                .collectList()
                                .flatMap(ladders1 -> Mono.just("保存第一版本的数据"));
                    }


                });*/
    }

    public Flux<Ladder> findAll() {
        return repository.findAll();
    }

    public Flux<Ladder> findByPage(int pageNum, int pageSize) {
        return repository.findByRankingBetween((pageNum - 1) * pageSize, pageNum * pageSize);
    }

    /**
     * @Param:
     * @return: Mono
     * @Author: qian
     * @Description: 查询数据库中最新版本号的数据
     * @Date: 2019/9/5 12:57 下午
     **/
    public Mono<Ladder> findLatelyVersion() {
        Sort sort = new Sort(Sort.Direction.DESC, "version");
        Query query = new Query().with(sort).limit(1);
        return template.findOne(query, Ladder.class);
    }
}
