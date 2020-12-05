package com.baronheid.reactivekotlin.fluxandmono

import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.core.scheduler.Schedulers.parallel
import reactor.kotlin.core.publisher.toFlux
import reactor.kotlin.test.test

class FluxMonoTransformTest {

    private val names = listOf("João", "Maria", "Serginho")

    @Test
    fun `Transform using map`() {

//        Iterates the list maintaining its type
        val namesFlux = names.toFlux()
                .map { it.toUpperCase() } //JOÃO, MARIA, SERGINHO
                .log()

        namesFlux.test()
                .expectNext("JOÃO", "MARIA", "SERGINHO")
                .verifyComplete()
    }

    @Test
    fun `Transform using map length`() {

//        Iterates the list maintaining its type
        val namesFlux = names.toFlux()
                .map { it.length } //JOÃO, MARIA, SERGINHO
                .log()

        namesFlux.test()
                .expectNext(4, 5, 8)
                .verifyComplete()
    }

    @Test
    fun `Transform using map length and repeat`() {

//        Iterates the list maintaining its type
        val namesFlux = names.toFlux()
                .map { it.length } //JOÃO, MARIA, SERGINHO
                .repeat(1)
                .log()

        namesFlux.test()
                .expectNext(4, 5, 8, 4, 5, 8)
                .verifyComplete()
    }

    @Test
    fun `Transform using map filter`() {

//        Iterates the list maintaining its type
        val namesFlux = names.toFlux()
                .filter { it.length >= 5 }
                .map { it.length } //JOÃO, MARIA, SERGINHO
                .repeat(1)
                .log()

        namesFlux.test()
                .expectNext(5, 8, 5, 8)
                .verifyComplete()
    }

    @Test
    fun `Transform using flatmap`() {
        val stringFlux = listOf("a", "b", "c", "d", "e", "f").toFlux() // One by one with toFlux()
                .flatMap { // When making an external service call that returns a flux
                    string -> // A -> {A, newValue}
                    Flux.fromIterable(convertToList(string))
                } // Every
                .log()

        stringFlux.test()
                .expectNextCount(12)
                .verifyComplete()
    }

    @Test
    fun `Transform using flatmap parallel`() {
        val stringFlux = listOf("a", "b", "c", "d", "e", "f").toFlux() // One by one with toFlux()
                .window(2) //Flux<Flux<String>> -> (A,B), (C,D)
                .flatMap {
                    it.map { it.toList() }.subscribeOn(parallel()) // Flux<List<String>>
                }
                .flatMap { it.toFlux() } // Flux<String>
                .log()

        stringFlux.test()
                .expectNextCount(6)
                .verifyComplete()
    }

    @Test
    fun `Transform using flatmap parallel maintaining order`() {
        val stringFlux = listOf("a", "b", "c", "d", "e", "f").toFlux() // One by one with toFlux()
                .window(2) //Flux<Flux<String>> -> (A,B), (C,D)
                .flatMapSequential {
                    it.map { it.toList() }.subscribeOn(parallel()) // Flux<List<String>>
                }
                .flatMap { it.toFlux() } // Flux<String>
                .log()

        stringFlux.test()
                .expectNextCount(6)
                .verifyComplete()
    }



    private fun convertToList(string: String?): List<String> {
        Thread.sleep(1000)
        return listOf(string!!, "newValue")

    }
}