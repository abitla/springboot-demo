package com.loyer.example.router;


import com.loyer.example.handle.WebFluxHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.time.Duration;

/**
 * @author kuangq
 * @projectName demo
 * @title WebFluxRouter
 * @description WebFlux路由函数绑定
 * @date 2019-10-16 15:35
 */
@Configuration
public class WebFluxRouter {

    /**
     * @param
     * @return org.springframework.web.reactive.function.server.RouterFunction<org.springframework.web.reactive.function.server.ServerResponse>
     * @author kuangq
     * @description 声明webflux接口
     * @date 2019-10-17 14:03
     */
    @Bean
    public RouterFunction<ServerResponse> webFlux() {
        return RouterFunctions.route(RequestPredicates.GET("/webFlux"),
                request -> {
                    Mono<String> str = Mono.just("Hello World").delayElement(Duration.ofMillis(10));
                    return ServerResponse.ok().body(str, String.class);
                });
    }

    /**
     * @param webFluxHandler
     * @return org.springframework.web.reactive.function.server.RouterFunction<org.springframework.web.reactive.function.server.ServerResponse>
     * @author kuangq
     * @description 函数绑定
     * @date 2019-10-17 14:03
     */
    @Bean
    public RouterFunction<ServerResponse> helloWebFlux(WebFluxHandler webFluxHandler) {
        return RouterFunctions.route(RequestPredicates.GET("/helloWebFlux")
                .and(RequestPredicates.accept(MediaType.TEXT_PLAIN)), webFluxHandler::helloWebFlux);
    }
}