package com.baronheid.reactivekotlin.fluxandmono

import org.junit.jupiter.api.Test
import reactor.kotlin.core.publisher.toFlux
import java.time.Duration

class ColdAndHardPublisherTest {

    @Test
    fun `Cold publisher test`(){
        val stringFlux = listOf("a","b","c","d","e","f").toFlux().delayElements(Duration.ofSeconds(1))
//      Cold publishers
        stringFlux.subscribe { println("Subscriber 1: $it") } // Emits a value from the beginning
        Thread.sleep(2000)

        stringFlux.subscribe { println("Subscriber 2: $it") } // Emits a value from the beginning
        Thread.sleep(4000)
    }

    @Test
    fun `Hot publisher test`(){
        val stringFlux = listOf("a","b","c","d","e","f").toFlux().delayElements(Duration.ofSeconds(1))
//      Hot publishers
        val connectableFlux = stringFlux.publish() // Emits a value from the beginning
        connectableFlux.connect()
        connectableFlux.subscribe { println("Subscriber 1: $it") }
        Thread.sleep(2000)

        connectableFlux.subscribe { println("Subscriber 2: $it") } // Emits the last value that the other
        // subscriber stopped
        Thread.sleep(4000)
    }
}