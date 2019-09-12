package com.caoyuqian.lol.service;

import com.caoyuqian.lol.entity.Summoner;
import com.caoyuqian.lol.repository.SummonerRepository;
import com.mongodb.client.result.DeleteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
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

     /**
       * @Param:
       * @return:
       * @Author: qian
       * @Description: 存入数据库
       * @Date: 2019/9/11 3:31 下午
      **/
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
    }
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
     /**
       * @Param:
       * @return: flux
       * @Author: qian
       * @Description: 查询数据库中最新版的韩服前一千名的召唤师的数据
       * @Date: 2019/9/10 11:23 上午
      **/
    public Flux<Summoner> findLatelyVersionSummoners(int pageNum,int pageSize) {
        Sort sort = new Sort(Sort.Direction.DESC, "version");
        Pageable pageable = PageRequest.of(pageNum,pageSize,sort);
        Query query = new Query().with(pageable);
        return template.find(query,Summoner.class);
    }
    /**
     * @Param:
     * @return: mono
     * @Author: qian
     * @Description: 根据玩家游戏内名称查询改召唤师的数据
     * @Date: 2019/9/10 11:23 上午
     **/
    public Mono<Summoner> findLatelyVersionSummonerByName(String name) {
        Sort sort = new Sort(Sort.Direction.DESC, "version");
        Query query = new Query(Criteria.where("name").is(name)).with(sort);
        return template.findOne(query,Summoner.class);
    }
     /**
       * @Param:
       * @return: mono
       * @Author: qian
       * @Description: 删除 除最新版本外的数据
       * @Date: 2019/9/11 10:02 上午
      **/
    public Mono<DeleteResult> deleteOldVersion() {
        return findLatelyVersion().flatMap(summoner -> {
            long version = summoner.getVersion();
            Query query = new Query(Criteria.where("version").lt(version));
            return template.remove(query,Summoner.class);
        }).defaultIfEmpty(DeleteResult.unacknowledged());

    }
}
