package gdl.dreamteam.skynet.Others

import android.content.Context
import android.util.Log
import gdl.dreamteam.skynet.Models.LoginRequest
import gdl.dreamteam.skynet.Models.LoginResponse
import gdl.dreamteam.skynet.R
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.CompletableFuture
import java.util.stream.Collectors

/**
 * Created by christopher on 16/09/17.
 */

object LoginService {

    private lateinit var clientId: String
    private lateinit var domain: String
    private lateinit var clientSecret: String
    private var credentials: LoginResponse
    private val grantType: String = "password"

    init {
        credentials = LoginResponse("","password", 123)
    }

    fun setup(context: Context) {
        clientId = context.getString(R.string.com_auth0_client_id)
        clientSecret = context.getString(R.string.com_auth0_client_secret)
        domain = context.getString(R.string.com_auth0_domain)
    }

    var accessToken: String
        get() = credentials.access_token
        set(value){
            credentials.access_token = value
        }

    fun login(username: String, password: String): CompletableFuture<LoginResponse> {
        return CompletableFuture.supplyAsync {
            val loginRequestData = LoginRequest(
                username,
                password,
                grantType,
                clientId,
                clientSecret
            )
            val payload = RestRepository.gson.toJson(loginRequestData)
            val url = URL("$domain/oauth/token")
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "POST"
            connection.setRequestProperty("Content-Type", "application/json")
            connection.setRequestProperty("Accept", "application/json")
            connection.doOutput = true
            val streamWriter = OutputStreamWriter(connection.outputStream)
            streamWriter.write(payload)
            streamWriter.close()

            return@supplyAsync RestRepository.handleResponseCode(connection.responseCode) {
                val streamReader = InputStreamReader(connection.inputStream)
                val rawJson = streamReader.readLines()
                    .stream()
                    .collect(Collectors.joining(""))
                streamReader.close()
                connection.disconnect()
                val loginResponseData = RestRepository.gson.fromJson(rawJson, LoginResponse::class.java)
                this.credentials = loginResponseData
                return@handleResponseCode this.credentials
            }
        }
    }


}