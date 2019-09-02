package com.caoyuqian.lol.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import reactor.core.publisher.Mono;

/**
 * @author qian
 * @version V1.0
 * @Title: Response
 * @Package: com.caoyuqian.lol.model
 * @Description: 返回Json实体类
 * @date 2019-08-30 13:20
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Response<T> {

    private int code;
    private String msg;
    private T data;
}
