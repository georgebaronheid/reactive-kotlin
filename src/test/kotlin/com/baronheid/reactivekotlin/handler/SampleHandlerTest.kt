package com.baronheid.reactivekotlin.handler

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.EntityExchangeResult
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.WebTestClient.BodySpec
import org.springframework.web.reactive.result.method.annotation.ResponseEntityResultHandler
import reactor.kotlin.core.publisher.toMono
import reactor.kotlin.test.test
import java.util.function.Consumer


@SpringBootTest
@AutoConfigureWebTestClient
class SampleHandlerTest {

    @Autowired
    private lateinit var webTestClient: WebTestClient

    @Test
//    @Disabled
    fun `Flux approach test`() {

        val integerFlux = webTestClient.get().uri("/functional/flux")
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
}