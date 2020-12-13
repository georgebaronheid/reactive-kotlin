package com.baronheid.reactivekotlin.fluxandmono

import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.kotlin.test.test
import reactor.test.StepVerifier
import reactor.test.scheduler.VirtualTimeScheduler
import java.time.Duration

class VirtualTimeTest {

    @Test
    fun `Testing without virtual time`(){

        val fluxLong = Flux.interval(Duration.ofSeconds(1)).take(3).log()
                .map { (it + 2L) }

        fluxLong.test()
                .expectSubscription()
                .expectNext(2L, 3L, 4L)
                .verifyComplete()

    }

    @Test
    fun `Testing with virtual time`(){

        VirtualTimeScheduler.getOrSet()

        val fluxLong = Flux.interval(Duration.ofSeconds(1)).take(3).log()

        StepVerifier.withVirtualTime { fluxLong.log() }
                .expectSubscription()
                .thenAwait(Duration.ofSeconds(3))
                .expectNext(0L, 1L, 2L)
                .verifyComplete()


    }
}