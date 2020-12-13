package com.baronheid.reactivekotlin.fluxandmono

import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.kotlin.core.publisher.toFlux
import reactor.kotlin.test.test
import reactor.test.StepVerifier
import reactor.test.scheduler.VirtualTimeScheduler
import java.time.Duration


class FluxMonoCombineTest {

    @Test
    fun `Combine using merge`(){
        val fluxStringOne = listOf("A","B","C").toFlux()
        val fluxStringTwo = listOf("D","E","F").toFlux()

        val mergedFlux = Flux.merge(fluxStringOne, fluxStringTwo).log()

        mergedFlux.test()
                .expectSubscription()
                .expectNext("A","B","C","D","E","F")
                .verifyComplete()
    }

    @Test
    fun `Combine using merge with delay`(){
        val fluxStringOne = listOf("A","B","C").toFlux().delayElements(Duration.ofSeconds(1))
        val fluxStringTwo = listOf("D","E","F").toFlux().delayElements(Duration.ofSeconds(1))

        val mergedFlux = Flux.merge(fluxStringOne, fluxStringTwo).log()

        mergedFlux.test()
                .expectSubscription()
                .expectNextCount(6)
                .verifyComplete()
    }

    @Test
    fun `Combine using concat`(){
        val fluxStringOne = listOf("A","B","C").toFlux()
        val fluxStringTwo = listOf("D","E","F").toFlux()

        val mergedFlux = Flux.concat(fluxStringOne, fluxStringTwo).log()

        mergedFlux.test()
                .expectSubscription()
                .expectNextCount(6)
                .verifyComplete()
    }

    @Test
    fun `Combine using concat with delay`(){

        VirtualTimeScheduler.getOrSet()

        val fluxStringOne = listOf("A","B","C").toFlux().delayElements(Duration.ofSeconds(1))
        val fluxStringTwo = listOf("D","E","F").toFlux().delayElements(Duration.ofSeconds(1))

        val mergedFlux = Flux.concat(fluxStringOne, fluxStringTwo).log()

        StepVerifier.withVirtualTime { mergedFlux.log() }
                .expectSubscription()
                .thenAwait(Duration.ofSeconds(6))
                .expectNextCount(6)
                .verifyComplete()

//        mergedFlux.test()
//                .expectSubscription()
//                .expectNextCount(6)
//                .verifyComplete()
    }

    @Test
    fun `Combine using zip`(){
        val fluxStringOne = listOf("A","B","C").toFlux()
        val fluxStringTwo = listOf("D","E","F").toFlux()

        val mergedFlux = Flux.zip(fluxStringOne, fluxStringTwo).log()

        mergedFlux.test()
                .expectSubscription()
                .expectNextCount(3)
                .verifyComplete()
    }
}