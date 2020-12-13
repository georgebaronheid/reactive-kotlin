package com.baronheid.reactivekotlin.fluxandmono

import org.junit.jupiter.api.Test
import reactor.core.publisher.BaseSubscriber
import reactor.core.publisher.Flux
import reactor.kotlin.test.test


class FluxAndMonoBackPressureTest {

    @Test
    fun `Back pressure test`() {
        val finiteFlux = Flux.range(1, 10).log()

        finiteFlux.test()
                .expectSubscription()
                .thenRequest(1)
                .expectNext(1)
                .thenRequest(1)
                .expectNext(2)
                .thenCancel()
                .verify()
    }

    @Test
    fun `Back pressure`() {
        val finiteFlux = Flux.range(1, 10).log()

        finiteFlux.subscribe({ element ->
            println("Element is $element")
        }, { errorConsumer ->
            println("Error is ${errorConsumer.message}")
        }, {
            println("Done")
        }, { subscription ->
            subscription.request(5)
        })
    }

    @Test
    fun `Canceling back pressure`() {
        val finiteFlux = Flux.range(1, 10).log()

        finiteFlux.subscribe({ element ->
            println("Element is $element")
        }, { errorConsumer ->
            println("Error is ${errorConsumer.message}")
        }, {
            println("Done")
        }, { subscription ->
            subscription.cancel()
        })
    }

    @Test
    fun `Customized back pressure`() {
        val finiteFlux = Flux.range(1, 10).log()

//        NOT WORKING
        finiteFlux.subscribe {
            object : BaseSubscriber<Int>() {
                override fun hookOnNext(value: Int) {
                    if (value < 4) cancel() else request(1)
                    println("Value received is $value")
                }
            } }

    }
}