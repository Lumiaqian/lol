package com.caoyuqian.lol.service;

import com.caoyuqian.lol.model.StatisticsChampion;
import com.caoyuqian.lol.repository.StatisticsChampionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @author qian
 * @version V1.0
 * @Title: StatisticsChampionService
 * @Package: com.caoyuqian.lol.service
 * @Description: TOTO
 * @date 2019-08-27 17:41
 **/
@Service
public class StatisticsChampionService {
    @Autowired
    private StatisticsChampionRepository repository;

    public Flux<StatisticsChampion> saveAll(List<StatisticsChampion> statisticsChampions){
        return repository
                .findAll()
                .flatMap(statisticsChampion -> {
                    StatisticsChampion sc = statisticsChampions.stream()
                            .filter(s -> statisticsChampion.getChampionName().equals(s.getChampionName()))
                            .findAny()
                            .get();
                    sc.setId(statisticsChampion.getId());
                    return repository.save(sc);
                }).switchIfEmpty(repository.saveAll(statisticsChampions));
    }

    public Flux<StatisticsChampion> findAll(){
        return repository.findAll();
    }
     /**
       * @Param: pageNum , pageSize
       * @return: Flux<StatisticsChampion>
       * @Author: qian
       * @Description: 分页查询StatisticsChampion
       * @Date: 2019/9/2 1:48 下午
      **/
    public Flux<StatisticsChampion> findAllByPage(int pageNum,int pageSize){
        return repository.findAllByRankBetweenOrderByRank((pageNum-1)*pageSize,pageNum*pageSize+1);
    }
}
