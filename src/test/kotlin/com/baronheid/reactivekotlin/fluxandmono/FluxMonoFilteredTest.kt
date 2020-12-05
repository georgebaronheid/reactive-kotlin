package com.baronheid.reactivekotlin.fluxandmono

import org.junit.jupiter.api.Test
import reactor.kotlin.core.publisher.toFlux
import reactor.kotlin.test.test

class FluxMonoFilteredTest {

    val names = listOf("João", "Maria", "Marcos")

    @Test
    fun `Filter test`() {
        val namesFlux = names.toFlux()
        val namesFluxFiltered = namesFlux.filter { it.startsWith("M") }.log()
        val namesFluxSize = namesFlux.filter { it.length > 5 }.log()


        namesFluxFiltered.test()
                .expectNextCount(2)
                .verifyComplete()

        namesFluxSize.test()
                .expectNext("Marcos")
                .verifyComplete()

    }
}