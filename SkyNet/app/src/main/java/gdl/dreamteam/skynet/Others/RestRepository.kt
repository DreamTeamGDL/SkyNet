package gdl.dreamteam.skynet.Others

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Future
import java.util.stream.Collector
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

    override fun addZone(zone: Zone): CompletableFuture<Boolean> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getZone(name: String): CompletableFuture<Zone?> {
        return CompletableFuture.supplyAsync {
            val connection = URL("$url/zones").openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.setRequestProperty("Accept", "application/json")
            val streamReader = InputStreamReader(connection.inputStream)
            val bufferedReader = BufferedReader(streamReader)
            val rawJson =  bufferedReader
                .lines()
                .collect(Collectors.joining())
            return@supplyAsync gson.fromJson(rawJson, Zone::class.java)
        }
    }

    override fun deleteZone(name: String): CompletableFuture<Boolean> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun updateZone(name: String, zone: Zone): CompletableFuture<Boolean> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}