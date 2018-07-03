package se.ulvhamne.time

import io.vertx.core.Vertx
import io.vertx.core.VertxOptions

fun main(args: Array<String>) {
    val vertx = Vertx.vertx(VertxOptions())
    vertx.deployVerticle(MainVerticle())
}
