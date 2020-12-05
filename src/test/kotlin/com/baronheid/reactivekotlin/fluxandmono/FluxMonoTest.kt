package com.baronheid.reactivekotlin.fluxandmono

import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.kotlin.core.publisher.toFlux
import reactor.kotlin.core.publisher.toMono
import reactor.kotlin.core.publisher.whenComplete
import reactor.kotlin.test.test
import java.util.function.Consumer

class FluxMonoTest {

    @Test
    fun `Flux basic test`() {
        val stringFlux = listOf("Spring", "Spring Boot", "Reactive Spring").toFlux()
//                .concatWith(RuntimeException("This is an error").toFlux())
                .concatWith(Flux.just("Arroz")).toFlux()
                .log()

//        Attaching a subscriber that will read values from the flux
        stringFlux.subscribe({ println(it) }, { it.message }, { println("Completed") })
    }

    @Test
    fun `Test flux elements without error`() {
        val stringFlux = listOf("Spring", "Spring Boot", "Reactive Spring").toFlux()
                .log()

        stringFlux.test()
                .expectNext("Spring")
                .expectNext("Spring Boot")
                .expectNext("Reactive Spring")
                .verifyComplete()
    }

    @Test
    fun `Test flux elements with error`() {
        val stringFlux = listOf("Spring", "Spring Boot", "Reactive Spring").toFlux()
                .concatWith(RuntimeException("This is an error").toFlux())
                .log()

        stringFlux.test()
                .expectNext("Spring")
                .expectNext("Spring Boot")
                .expectNext("Reactive Spring")
//                .expectError(RuntimeException::class.java)
                .expectErrorMessage("This is an error")
                .verify()
    }

    @Test
    fun `Test flux elements count`() {
        val stringFlux = listOf("Spring", "Spring Boot", "Reactive Spring").toFlux()
                .concatWith(RuntimeException("This is an error").toFlux())
                .log()

        stringFlux.test()
                .expectNextCount(3)
                .expectErrorMessage("This is an error")
                .verify()

    }

    @Test
    fun `Test flux with error`() {
        val stringFlux = listOf("Spring", "Spring Boot", "Reactive Spring").toFlux()
                .concatWith(RuntimeException("This is an error").toFlux())
                .log()

        stringFlux.test()
                .expectNext("Spring", "Spring Boot", "Reactive Spring")
                .expectErrorMessage("This is an error")
                .verify()
    }

    @Test
    fun `Mono test`(){

        val stringMono = "Spring Mono".toMono().log()

        stringMono.test()
                .expectNext("Spring Mono")
                .verifyComplete()

    }

    @Test
    fun `Mono error test`(){


        IllegalArgumentException("arroz").toMono<Exception>().log()
                .test()
                .expectError(IllegalArgumentException::class.java)
                .verify()

    }
}
