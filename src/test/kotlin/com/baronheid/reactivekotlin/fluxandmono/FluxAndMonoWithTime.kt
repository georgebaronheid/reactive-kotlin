package com.baronheid.reactivekotlin.fluxandmono

import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.kotlin.core.publisher.toFlux
import reactor.kotlin.test.test
import java.time.Duration

class FluxAndMonoWithTime {

    @Test
    fun `Infinite sequence`() {
        val infiniteFlux = Flux.interval(Duration.ofMillis(50)).toFlux().log()

        infiniteFlux.subscribe { println("Value is: $it") }

        Thread.sleep(3000)
    }

    @Test
    fun `Infinite sequence test`() {

        val finiteFlux = Flux.interval(Duration.ofMillis(50)).take(3).log()

        finiteFlux.test()
                .expectSubscription()
                .expectNext(0L, 1L, 2L)
                .verifyComplete()

    }

    @Test
    fun `Infinite map sequence test`() {

        val finiteFlux = Flux.interval(Duration.ofMillis(50))
                .map { it.toInt() }
                .take(3).log()

        finiteFlux.test()
                .expectSubscription()
                .expectNext(0, 1, 2)
                .verifyComplete()
    }

    @Test
    fun `Infinite map sequence test with delay`() {

        val finiteFlux = Flux.interval(Duration.ofMillis(50))
                .delayElements(Duration.ofMillis(100))
                .map { it.toInt() }
                .take(3).log()

        finiteFlux.test()
                .expectSubscription()
                .expectNext(0, 1, 2)
                .verifyComplete()
    }
}