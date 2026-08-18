package com.farnz.tvplayer

import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

/**
 * Parser sederhana untuk file playlist M3U/M3U8.
 * Format yang didukung:
 * #EXTM3U
 * #EXTINF:-1 tvg-logo="http://logo.png" group-title="Berita",Nama Channel
 * http://stream-url.m3u8
 */
object M3UParser {

    fun fetchAndParse(playlistUrl: String): List<Channel> {
        val connection = URL(playlistUrl).openConnection() as HttpURLConnection
        connection.connectTimeout = 15000
        connection.readTimeout = 15000
        connection.requestMethod = "GET"
        connection.connect()

        if (connection.responseCode !in 200..299) {
            throw Exception("HTTP ${connection.responseCode}")
        }

        val reader = BufferedReader(InputStreamReader(connection.inputStream))
        val lines = reader.readLines()
        reader.close()
        connection.disconnect()

        return parse(lines)
    }

    fun parse(lines: List<String>): List<Channel> {
        val channels = mutableListOf<Channel>()

        var currentName: String? = null
        var currentLogo: String? = null
        var currentGroup: String? = null

        for (rawLine in lines) {
            val line = rawLine.trim()
            if (line.isEmpty()) continue

            when {
                line.startsWith("#EXTINF") -> {
                    currentLogo = Regex("tvg-logo=\"([^\"]*)\"").find(line)?.groupValues?.get(1)
                    currentGroup = Regex("group-title=\"([^\"]*)\"").find(line)?.groupValues?.get(1)
                    val commaIndex = line.lastIndexOf(',')
                    currentName = if (commaIndex != -1 && commaIndex < line.length - 1) {
                        line.substring(commaIndex + 1).trim()
                    } else {
                        "Channel"
                    }
                }
                line.startsWith("#") -> {
                    // Metadata lain, diabaikan
                }
                else -> {
                    // Ini baris URL stream
                    if (currentName != null) {
                        channels.add(
                            Channel(
                                name = currentName,
                                logoUrl = currentLogo,
                                groupTitle = currentGroup,
                                streamUrl = line
                            )
                        )
                        currentName = null
                        currentLogo = null
                        currentGroup = null
                    }
                }
            }
        }

        return channels
    }
}
