package gdl.dreamteam.skynet.Others

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory
import gdl.dreamteam.skynet.Exceptions.BadRequestException
import gdl.dreamteam.skynet.Exceptions.InternalErrorException
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import java.util.concurrent.CompletableFuture
import java.util.stream.Collectors

/**
 * Created by christopher on 10/09/17.
 */
class RestRepository : IDataRepository {

    private var gson: Gson
    private val url = "http://skynetgdl.azurewebsites.net/api"

    init {
        val typeAdapter = RuntimeTypeAdapterFactory
            .of(AbstractDeviceData::class.java, "type")
            .registerSubtype(Door::class.java)
            .registerSubtype(Fan::class.java)
            .registerSubtype(Light::class.java)
            .registerSubtype(Camera::class.java)
        this.gson = GsonBuilder()
            .registerTypeAdapterFactory(typeAdapter)
            .create()
    }

    override fun addZone(zone: Zone): CompletableFuture<Unit> {
        return CompletableFuture.supplyAsync {
            val json: String = gson.toJson(zone)
            val connection = URL("$url/zone").openConnection() as HttpURLConnection
            connection.requestMethod = "POST"
            connection.setRequestProperty("Content-Type", "application/json")
            connection.setRequestProperty("Accept", "application/json")
            val streamWriter = OutputStreamWriter(connection.outputStream)
            streamWriter.write(json)
            streamWriter.flush()
            streamWriter.close()
            handleResponseCode(connection.responseCode) {}
        }
    }

    override fun getZone(name: String): CompletableFuture<Zone?> {
        return CompletableFuture.supplyAsync {
            val encodedName = urlEncode(name)
            val connection = URL("$url/zone/$encodedName")
                .openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.setRequestProperty("Accept", "application/json")
            return@supplyAsync handleResponseCode(connection.responseCode) {
                val streamReader = InputStreamReader(connection.inputStream)
                val bufferedReader = BufferedReader(streamReader)
                val rawJson = bufferedReader
                    .lines()
                    .collect(Collectors.joining())
                return@handleResponseCode gson.fromJson(rawJson, Zone::class.java)
            }
        }
    }

    override fun deleteZone(name: String): CompletableFuture<Unit> {
        return CompletableFuture.supplyAsync {
            val encodedName = urlEncode(name)
            val connection = URL("$url/zone/$encodedName")
                .openConnection() as HttpURLConnection
            connection.requestMethod = "DELETE"
            connection.setRequestProperty("Accept", "application/json")
            handleResponseCode(connection.responseCode) {}
        }
    }

    override fun updateZone(name: String, zone: Zone): CompletableFuture<Unit> {
        return CompletableFuture.supplyAsync {
            val encodedName = URLEncoder.encode(name, "UTF-8")
            val connection = URL("$url/zone/$encodedName")
                .openConnection() as HttpURLConnection
            connection.requestMethod = "PUT"
            connection.setRequestProperty("Accept", "application/json")
            handleResponseCode(connection.responseCode) {}
        }
    }

    private fun <T> handleResponseCode(code: Int, handler: () -> T): T {
        when(code) {
            HttpURLConnection.HTTP_BAD_REQUEST -> throw BadRequestException()
            HttpURLConnection.HTTP_INTERNAL_ERROR -> throw InternalErrorException()
            else -> return handler()
        }
    }

    private fun urlEncode(item: String): String = URLEncoder.encode(item, "UTF-8")

}