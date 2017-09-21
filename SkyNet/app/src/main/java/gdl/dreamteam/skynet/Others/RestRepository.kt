package gdl.dreamteam.skynet.Others

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory
import gdl.dreamteam.skynet.Exceptions.BadRequestException
import gdl.dreamteam.skynet.Exceptions.ForbiddenException
import gdl.dreamteam.skynet.Exceptions.InternalErrorException
import gdl.dreamteam.skynet.Exceptions.UnauthorizedException
import gdl.dreamteam.skynet.Models.*
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import java.util.concurrent.CompletableFuture
import java.util.concurrent.CompletableFuture.supplyAsync
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

/**
 * Created by christopher on 10/09/17.
 */
class RestRepository : IDataRepository {

    companion object {

        private val typeAdapter = RuntimeTypeAdapterFactory
            .of(AbstractDeviceData::class.java, "type")
            .registerSubtype(Door::class.java)
            .registerSubtype(Fan::class.java)
            .registerSubtype(Light::class.java)
            .registerSubtype(Camera::class.java)
        val gson: Gson = GsonBuilder()
            .registerTypeAdapterFactory(typeAdapter)
            .create()

        fun <T> handleResponseCode(code: Int, handler: () -> T): T {
            println(code)
            when(code) {
                HttpURLConnection.HTTP_BAD_REQUEST -> throw BadRequestException()
                HttpURLConnection.HTTP_INTERNAL_ERROR -> throw InternalErrorException()
                HttpURLConnection.HTTP_UNAUTHORIZED -> throw UnauthorizedException()
                HttpURLConnection.HTTP_FORBIDDEN -> throw ForbiddenException()
                else -> return handler()
            }
        }

        fun urlEncode(item: String): String = URLEncoder.encode(item, "UTF-8")
    }

    private val url = "http://skynetgdl.azurewebsites.net/api"

    override fun addZone(zone: Zone): CompletableFuture<Unit> {
        return supplyAsync {
            val json: String = gson.toJson(zone)
            println(json)
            val connection = URL("$url/zones")
                .openConnection() as HttpsURLConnection
            connection.requestMethod = "POST"
            connection.setRequestProperty("Content-Type", "application/json")
            connection.setRequestProperty("Accept", "application/json")
            connection.doOutput = true
            val streamWriter = OutputStreamWriter(connection.outputStream)
            streamWriter.write(json)
            streamWriter.close()
            handleResponseCode(connection.responseCode) {}
        }
    }

    override fun getZone(name: String): CompletableFuture<Zone?> {
        return supplyAsync {
            val encodedName = urlEncode(name)
            val connection = URL("$url/zones/$encodedName")
                .openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.setRequestProperty("Accept", "application/json")
            connection.setRequestProperty("Authorization", "Bearer ${LoginService.accessToken}")
            return@supplyAsync handleResponseCode(connection.responseCode) {
                val streamReader = InputStreamReader(connection.inputStream)
                val rawJson = streamReader.readLines()
                    .stream()
                    .collect(Collectors.joining())
                streamReader.close()
                connection.disconnect()
                return@handleResponseCode gson.fromJson(rawJson, Zone::class.java)
            }
        }
    }

    override fun deleteZone(name: String): CompletableFuture<Unit> {
        return supplyAsync {
            val encodedName = urlEncode(name)
            val connection = URL("$url/zone/$encodedName")
                .openConnection() as HttpURLConnection
            connection.requestMethod = "DELETE"
            connection.setRequestProperty("Accept", "application/json")
            handleResponseCode(connection.responseCode) {}
        }
    }

    override fun updateZone(name: String, zone: Zone): CompletableFuture<Unit> {
        return supplyAsync {
            val encodedName = URLEncoder.encode(name, "UTF-8")
            val connection = URL("$url/zone/$encodedName")
                .openConnection() as HttpURLConnection
            connection.requestMethod = "PUT"
            connection.setRequestProperty("Accept", "application/json")
            handleResponseCode(connection.responseCode) {}
        }
    }

}