package rs.etf.snippet.rest.ktor.models

import kotlinx.serialization.Serializable

@Serializable
data class Obaveza(
    val id: Int = 0,
    var fleg: Boolean = false,
    val naziv: String = "",
    val vreme: String = "",
    val opis: String = ""
)

val ephemeralObaveze = mutableListOf(
    Obaveza(
        id = 1,
        fleg = false,
        naziv = "Obaveza#1",
        vreme = "2024-01-21T15:30:45",
        opis = "Super#1"
    ),
    Obaveza(
        id = 2,
        fleg = false,
        naziv = "Obaveza#2",
        vreme = "2024-01-24T19:15:22",
        opis = "Super#2"
    ),
    Obaveza(
        id = 3,
        fleg = false,
        naziv = "Obaveza#3",
        vreme = "2024-01-24T12:00:00",
        opis = "Super#3"
    )
)
