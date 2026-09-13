package com.oremoschanganaptcli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.UsageError
import com.github.ajalt.clikt.core.main
import com.github.ajalt.clikt.core.subcommands
import com.github.ajalt.clikt.parameters.options.option
import com.oremoschanganaptcli.data.praysList
import com.oremoschanganaptcli.data.songsList

class MainCommand : CliktCommand(name = "oremos") {
    override fun run() {
//        echo("Oremos Changana-PT")
    }
}

class GetPraysCommand : CliktCommand(name = "prays") {
    private val prayId by option("-i", "--id", help = "ID da oração")
    private val searchPray by option("-f", "--find", help = "Encontrar a oração pelo titulo")

    override fun run() {
        if (prayId != null && searchPray != null) {
            throw UsageError("As opções --id ou -i e --find ou -f não podem ser usadas em simultâneo.")
        }

        if (prayId == null && searchPray == null) {
            echo()
            echo("Todas as oração")
            echo()
            praysList.forEach { echo("${it.id} - ${it.title}") }
        }

        prayId?.let { pray ->
            praysList
                .find { it.id == pray.toInt() }
                ?.let {
                    echo()
                    echo("Oração: ${it.title}")
                    echo()
                    echo(it.body)
                }
                ?: run {
                    UsageError("Nenhuma oração com ID $pray encontrada")
                }
        }

        searchPray?.let { possiblyPrayTitle ->
                praysList
                    .filter { it.title.contains(possiblyPrayTitle, ignoreCase = true) }
                    .let { praysList ->
                        if (praysList.isNotEmpty()) {
                            echo()
                            echo(message = if (praysList.size > 1) "Orações encontradas" else "Oração encontrada")
                            echo()
                            praysList.forEach { echo("${it.id} - ${it.title}") }
                        } else {
                            UsageError("Nenhuma oração foi encontrada.")
                        }
                    }
            }
    }
}


class GetSongsCommand : CliktCommand(name = "songs") {
    private val songOption by option("-n", "--num", help = "Número do cântico")
    private val searchSong by option("-f", "--find", help = "Procurar cântico pelo titulo")

    override fun run() {
        if (songOption != null && searchSong != null) {
            throw UsageError("As opções --num ou -n e --find ou -f não podem ser usadas em simultâneo.")
        }

        if (songOption == null && searchSong == null) {
            echo()
            echo("Todos os cânticos")
            echo("")
            songsList.forEach { echo("${it.number} - ${it.title}") }
        }

        songOption?.let { songNum ->
            songsList
                .find { it.number == songNum }
                ?.let {
                    echo()
                    echo("Cântico: ${it.number} - ${it.title}")
                    echo()
                    echo(it.body)
                }
                ?: run {
                    UsageError("Nenhum cântico com número $songNum foi encontrado")
                }
        }

        searchSong?.let { songTitle ->
            songsList
                .filter { it.title.contains(songTitle, ignoreCase = true)}
                .let { foundSongs ->
                    if (foundSongs.isNotEmpty()) {
                        echo()
                        echo(message = if (foundSongs.size > 1) "Cânticos encontrados" else "Cântico encontrado")
                        echo()
                        foundSongs.forEach { song ->
                            echo("${song.number} - ${song.title}")
                        }
                    } else {
                        UsageError("Nenhum cântico foi encontrado")
                    }
                }
        }
    }
}

fun main(args: Array<String>) {
    MainCommand()
        .subcommands(GetPraysCommand(), GetSongsCommand())
        .main(args)
}