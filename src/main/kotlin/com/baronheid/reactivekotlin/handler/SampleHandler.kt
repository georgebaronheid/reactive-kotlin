package com.baronheid.reactivekotlin.handler

import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toFlux
import reactor.kotlin.core.publisher.toMono

@Component
class SampleHandler {

    //    ServerResponse always comes back wrapped in a Mono, despite the fact that it might contain a Flux
    fun flux(serverRequest: ServerRequest): Mono<ServerResponse> = ServerResponse.ok()
            .contentType(MediaType.APPLICATION_NDJSON)
            .body(
                    listOf(1, 2, 3, 4).toFlux().log(),
                    Int::class.java
            )

    fun mono(serverRequest: ServerRequest): Mono<ServerResponse> = ServerResponse.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(
                    1.toMono().log(),
                    Int::class.java
            )
}