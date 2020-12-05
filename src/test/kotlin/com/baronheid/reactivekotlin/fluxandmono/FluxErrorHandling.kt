package com.baronheid.reactivekotlin.fluxandmono

import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.kotlin.core.publisher.toFlux
import reactor.kotlin.test.test

class FluxErrorHandling {

    @Test
    fun `Flux error handling on error map`(){

        val fluxString = listOf("A","B","C").toFlux().concatMap { java.lang.RuntimeException("exception") }.onErrorMap {  }
                .log()

        fluxString.test()
                .expectSubscription()
                .expectNext("A","B","C")
                .expectNext("default")
                .verifyComplete()
    }
}