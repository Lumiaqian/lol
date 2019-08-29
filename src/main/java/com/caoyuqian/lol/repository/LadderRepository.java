package com.caoyuqian.lol.repository;

import com.caoyuqian.lol.entity.Ladder;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LadderRepository extends ReactiveMongoRepository<Ladder,String> {
}
