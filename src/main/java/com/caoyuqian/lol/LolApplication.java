package com.caoyuqian.lol;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;

@SpringBootApplication()
public class LolApplication {

    public static void main(String[] args) {
        SpringApplication.run(LolApplication.class, args);
    }

}
