package se.ulvhamne.time;

import io.vertx.core.AbstractVerticle
import io.vertx.ext.web.Router

class MainVerticle : AbstractVerticle() {

    @Throws(Exception::class)
    override fun start() {
        val router = Router.router(vertx)
        router.get("/time").handler {

                it.response()
                    .putHeader("content-type", "text/plain")
                    .end("Hello from Vert.x!")
        }
        vertx.createHttpServer().requestHandler(router::accept).listen(8080)
        println("HTTP server started on port 8080")
    }
}

