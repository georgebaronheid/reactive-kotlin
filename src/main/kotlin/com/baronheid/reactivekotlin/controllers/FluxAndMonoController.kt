package com.baronheid.reactivekotlin.controllers

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toFlux
import reactor.kotlin.core.publisher.toMono
import java.time.Duration

@RestController
class FluxAndMonoController {


    @GetMapping("/flux")
    fun getIntegerFlux(): Flux<Int> = listOf(1, 2, 3, 4).toFlux().delayElements(Duration.ofSeconds(1)).log()

    @GetMapping("/flux-stream", produces = [MediaType.APPLICATION_STREAM_JSON_VALUE])
    fun getIntegerFluxStream(): Flux<Long> = Flux.interval(Duration.ofSeconds(1)).log()

//    Mono is a representation of just one element
    @GetMapping("/mono")
    fun returnMono(): Mono<Int> = 1.toMono().log()
}