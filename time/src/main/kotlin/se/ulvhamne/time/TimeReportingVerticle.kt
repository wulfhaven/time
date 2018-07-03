package se.ulvhamne.time

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.vertx.core.AbstractVerticle
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import se.ulvhamne.time.entities.WorkDay
import io.vertx.core.Vertx
import io.vertx.core.VertxOptions
import io.vertx.ext.web.handler.BodyHandler
import se.ulvhamne.time.entities.Project
import se.ulvhamne.time.entities.WorkRate
import se.ulvhamne.time.entities.WorkTask
import java.util.*

fun main(args: Array<String>)  {
    val vertx = Vertx.vertx(VertxOptions())
    vertx.deployVerticle(TimeReportingVerticle())
}


class TimeReportingVerticle : AbstractVerticle() {

    val workDays = mutableListOf<WorkDay>()
    val pretty = GsonBuilder().setPrettyPrinting().create()
    val json = Gson()


    override fun start() {
        workDays.add(
            WorkDay(
                tasks = mutableListOf(
                    WorkTask(
                        Project("TESTiT", company = "Scania", validUntil = Date(2018, 7, 4)),
                        name = "JBoss",
                        timeUsed = 60*60*4,
                        rate = WorkRate.EXTRA_OMGWTFBBQ_OVERTIME
                    ),
                    WorkTask(
                        Project("Mina Sidor", company = "KFM", validUntil = Date(2018, 11, 30)),
                        name = "WLS",
                        timeUsed = 60*60*4,
                        rate = WorkRate.OVERTIME
                    )
                )
            )
        )

        val router = Router.router(vertx)
        router.route().handler(BodyHandler.create())
        router.get("/time/all").handler(this::handleGet)
        router.post("/time/add").handler(this::handlePost)
        vertx.createHttpServer().requestHandler(router::accept).listen(8080)
        println("HTTP server started on port 8080")
    }

    private fun handleGet(routingContext: RoutingContext) {
        val response = routingContext.response()
        response.putHeader("content-type", "text/plain").end(pretty.toJson(workDays))
    }

    private fun handlePost(routingContext: RoutingContext) {
        val workDay = json.fromJson<WorkDay>(routingContext.bodyAsString, WorkDay::class.java)
        workDays.add(workDay)
        routingContext.response().end()
    }
}
