package rs.etf.snippet.rest.ktor.routes

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.*
import rs.etf.snippet.rest.ktor.models.Obaveza
import rs.etf.snippet.rest.ktor.models.ephemeralObaveze

fun Route.allRouting() {
    route("/all") {
        get {
            val id = call.request.queryParameters["id"]?.toInt()
            if (id == null) call.respond(ephemeralObaveze)
            else call.respond(ephemeralObaveze.filter { it.id == id })
        }
        get("/neodradjene") {
            val id = call.request.queryParameters["id"]?.toInt()
            if (id == null) call.respond(ephemeralObaveze.filter { !it.fleg })
            else call.respond(ephemeralObaveze.filter { it.id == id && !it.fleg })
        }
        post("/azurirajFleg") {
            val id = call.receive<Int>()
            val obaveza = ephemeralObaveze.find { it.id == id }
            if (obaveza != null) {
                obaveza.fleg = true
                call.respondText(
                    text = "Uspesno",
                    status = HttpStatusCode.OK
                )
            }
            else {
                call.respondText(
                    text = "Neuspesno",
                    status = HttpStatusCode.BadRequest
                )
            }
        }
        post("/dodajObavezu") {
            val obaveza = call.receive<Obaveza>()
            ephemeralObaveze.add(obaveza)
            call.respondText(
                text = "Uspesno",
                status = HttpStatusCode.OK
            )
        }
    }
}

/*
get {
    val queryParamStudentIndex = call.request.queryParameters["index"]
    if (queryParamStudentIndex != null) {
        call.respond(ephemeralImpressions.filter { it.student.index == queryParamStudentIndex })
    } else {
        call.respond(ephemeralImpressions)
    }
}
get("{rating}") {
    val rating = call.parameters["rating"] ?: return@get call.respondText(
        text = "Missing rating",
        status = HttpStatusCode.BadRequest
    )
    call.respond(ephemeralImpressions.filter { it.rating == Rating.fromString(rating) })
}
post {
    val impression = call.receive<Impression>()
    ephemeralImpressions.add(impression)
    call.respondText(
        text = "Impression stored correctly",
        status = HttpStatusCode.Created
    )
}
 */