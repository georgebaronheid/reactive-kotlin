package com.baronheid.reactivekotlin.handler

import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Flux
import reactor.kotlin.core.publisher.toMono
import reactor.kotlin.test.test

@SpringBootTest
@AutoConfigureWebTestClient
class SampleHandlerTest {

    @Autowired
    private lateinit var webTestClient: WebTestClient

    @Test
    @Disabled
    fun `Flux approach test`() {

        val integerFlux = webTestClient.get().uri("functional/mono")
                .accept(MediaType.APPLICATION_NDJSON)
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
    fun `Mono endpoint test`(){

        val expectedInt = 1.toMono()

        webTestClient.get().uri("/functional/mono")


    }
}