package com.caoyuqian.lol.service;

import com.caoyuqian.lol.entity.Hero;
import com.caoyuqian.lol.entity.goods.Goods;
import com.caoyuqian.lol.repository.GoodsRespository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@Slf4j
public class GoodsCrawService {

    @Autowired
    private GoodsRespository goodsRespository;

    public Mono<String> saveAllGoods(List<Goods> goodss){
        return Mono.just("save GoodsList")

                .doFirst(() -> goodsRespository.deleteAll().subscribe())
                .doFinally(signalType -> goodsRespository.saveAll(goodss).subscribe());
    }
}
