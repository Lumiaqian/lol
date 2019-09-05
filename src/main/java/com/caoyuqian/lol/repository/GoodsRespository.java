package com.caoyuqian.lol.repository;

import com.caoyuqian.lol.entity.goods.Goods;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GoodsRespository extends ReactiveMongoRepository<Goods,String> {
}
