package com.baronheid.reactivekotlin.controllers

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Flux
import reactor.kotlin.test.test

@WebFluxTest
class FluxAndMonoControllerTest {

    @Autowired
    private lateinit var webTestClient: WebTestClient

    @Test
    fun `Flux approach test`() {

        val integerFlux: Flux<Int> = webTestClient.get().uri("/flux")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk
                .returnResult(Int::class.java)
                .responseBody

        integerFlux.test()
                .expectSubscription()
                .expectNextCount(4)
                .verifyComplete()

    }

    @Test
    fun `Flux approach 2`() {
        webTestClient.get().uri("/flux-stream")
                .exchange()
                .expectStatus().isOk
                .expectHeader().contentType(MediaType.APPLICATION_NDJSON)
                .expectBodyList(Int::class.java)
                .hasSize(4)
    }

    @Test
    fun `Flux approach 3`() {

        val expectedEntityList = listOf(1, 2, 3, 4)

        val entityExchangeResult = webTestClient.get().uri("/flux-stream")
                .exchange()
                .expectStatus().isOk
                .expectBodyList(Int::class.java)
                .returnResult()

        Assertions.assertEquals(expectedEntityList, entityExchangeResult.responseBody)

    }

    @Test
    fun `Flux approach 4`() {

        val expectedEntityList = listOf(1, 2, 3, 4)

        webTestClient.get().uri("/flux-stream")
                .exchange()
                .expectStatus().isOk
                .expectBodyList(Int::class.java)
                .consumeWith<WebTestClient.ListBodySpec<Int>> {
                    Assertions.assertEquals(expectedEntityList, it.responseBody)
                }
    }

    @Test
    fun `Flux stream`() {
        val longStreamFlux: Flux<Long> = webTestClient.get().uri("/flux")
                .accept(MediaType.APPLICATION_NDJSON)
                .exchange()
                .expectStatus().isOk
                .returnResult(Long::class.java)
                .responseBody

        longStreamFlux.test()
                .expectNext(1L)
                .expectNext(2L)
                .expectNext(3L)
                .thenCancel()
                .verify()
    }

    @Test
    fun `Mono test`() {
        val test = webTestClient.get().uri("/mono")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk
                .expectBody(Int::class.java)
                .returnResult()
                .responseBody

        Assertions.assertEquals(1, test)

    }
}