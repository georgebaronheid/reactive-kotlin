package com.baronheid.reactivekotlin.router

import com.baronheid.reactivekotlin.handler.SampleHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.RequestPredicates.GET
import org.springframework.web.reactive.function.server.RequestPredicates.accept
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.RouterFunctions
import org.springframework.web.reactive.function.server.ServerResponse

@Configuration
class RouterFunctionConfig {

    @Bean
    fun routerFunction(handlerFunction: SampleHandler): RouterFunction<ServerResponse> = RouterFunctions
            .route(GET("/functional/flux").and(accept(MediaType.APPLICATION_NDJSON)), handlerFunction::flux)
            .andRoute(GET("/functional/mono").and(accept(MediaType.APPLICATION_JSON)), handlerFunction::mono)

}