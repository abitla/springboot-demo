package com.loyer.example.handle;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * @author kuangq
 * @projectName demo
 * @title WebFluxHandler
 * @description WebFlux示例接口
 * @date 2019-10-16 15:26
 */
@Component
public class WebFluxHandler {

    /**
     * @param request
     * @return reactor.core.publisher.Mono<org.springframework.web.reactive.function.server.ServerResponse>
     * @author kuangq
     * @description 声明函数并设置header处理中文乱码
     * @date 2019-10-17 14:05
     */
    public Mono<ServerResponse> helloWebFlux(ServerRequest request) {
        return ServerResponse.ok().contentType(MediaType.TEXT_PLAIN).header("Content-Type", "text/plain; charset=utf-8")
                .body(BodyInserters.fromObject("Hello，WebFlux！"));
    }
}