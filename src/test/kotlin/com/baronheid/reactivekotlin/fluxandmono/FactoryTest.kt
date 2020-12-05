package com.baronheid.reactivekotlin.fluxandmono

import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toFlux
import reactor.kotlin.core.publisher.toMono
import reactor.kotlin.test.test
import java.util.function.Supplier

class FactoryTest {

    val namesList: List<String> = listOf("João", "Maria", "Pedrinho")

    @Test
    fun `Flux with iterable`() {
        val namesFlux: Flux<String> = namesList.toFlux().log()

        namesFlux.test()
                .expectNext("João", "Maria", "Pedrinho")
                .verifyComplete()
    }

    @Test
    fun `Flux with array`() {
        val nameArray = arrayOf("João Array", "Maria Array", "Pedrinho Array")
        val nameArrayFlux = nameArray.toFlux().log()

        nameArrayFlux.test()
                .expectNext("João Array", "Maria Array", "Pedrinho Array")
                .verifyComplete()
    }

    @Test
    fun `Flux from stream`() {
        val namesFluxStream = namesList.stream().toFlux().log()

        namesFluxStream.test()
                .expectNext("João", "Maria", "Pedrinho")
                .verifyComplete()
    }

    @Test
    fun `Mono just or empty`() {
        val mono = Mono.empty<Unit>()

        mono
                .test()
                .verifyComplete()
    }


    @Test
    fun `Flux range`(){
        val range = Flux.range(1,10).log()

        range.test().expectNextCount(10)
                .verifyComplete()
    }
}