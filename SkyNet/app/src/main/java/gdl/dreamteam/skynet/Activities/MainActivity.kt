package gdl.dreamteam.skynet.Activities

import android.content.Intent
import android.databinding.DataBindingUtil
import android.media.MediaPlayer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import gdl.dreamteam.skynet.Bindings.LoginBinding
import gdl.dreamteam.skynet.Exceptions.ForbiddenException
import gdl.dreamteam.skynet.Exceptions.InternalErrorException
import gdl.dreamteam.skynet.Exceptions.UnauthorizedException
import gdl.dreamteam.skynet.Extensions.bork
import gdl.dreamteam.skynet.Extensions.longToast
import gdl.dreamteam.skynet.Extensions.shortToast
import gdl.dreamteam.skynet.Models.*
import gdl.dreamteam.skynet.Others.IDataRepository
import gdl.dreamteam.skynet.Others.LoginService
import gdl.dreamteam.skynet.Others.RestRepository
import gdl.dreamteam.skynet.Others.SettingsService
import gdl.dreamteam.skynet.R
import gdl.dreamteam.skynet.databinding.MainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainBinding
    private lateinit var dataRepository: IDataRepository
    private lateinit var progressBar: ProgressBar
    private lateinit var loginButton: Button
    private val uiThread = Handler(Looper.getMainLooper())
    private lateinit var settingsService : SettingsService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        dataRepository = RestRepository()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.login = LoginBinding()
        progressBar = findViewById(R.id.progressBar) as ProgressBar
        loginButton = findViewById(R.id.loginButton) as Button
        settingsService = SettingsService(applicationContext)

        val token = settingsService.getString("Token")
        if(!token.isEmpty()){
            navigateToZones(token)
        }
    }

    private fun navigateToZones(token: String){
        LoginService.accessToken = token
        dataRepository.getZone()
        .thenApply { zone -> parseZone(zone) }
    }

    private fun parseZone(zones: Array<Zone>?) {
        bork()
        val intent = Intent(this, ZonesActivity::class.java)
        val rawZones = RestRepository.gson.toJson(zones, Array<Zone>::class.java)
        intent.putExtra("zones", rawZones)
        uiThread.post {
            loginButton.isEnabled = true
            progressBar.visibility = View.INVISIBLE
            startActivity(intent)
        }
    }

    private fun validateForm(username: String?, password: String?): Boolean {
        if (username == "" || username == null) {
            longToast("Please put a username")
            return false
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(username).matches()) {
            longToast("Invalid email address")
            return false
        }

        if (password == "" || password == null) {
            longToast("Please put a password")
            return false
        }
        return true
    }

    private fun handleExceptions(throwable: Throwable) {
        Log.wtf("Exception", throwable.cause.toString())
        when(throwable.cause) {
            is UnauthorizedException, is ForbiddenException -> {
                uiThread.post {
                    shortToast("Please introduce valid credentials") }
                }
            is InternalErrorException -> {
                uiThread.post {
                    shortToast("Uups, that was a server error, try again in a few moments")
                }
            }
        }
        uiThread.post {
            loginButton.isEnabled = true
            progressBar.visibility = View.INVISIBLE
        }
    }

    fun onLoginPress(view: View) {
        val username: String? = binding.login.username
        val password: String? = binding.login.password
        if (!validateForm(username, password)) return
        LoginService.setup(applicationContext)
        progressBar.visibility = View.VISIBLE
        shortToast("Logging In...")
        LoginService.login(
            username as String,
            password as String
        )
        .thenApply { loginResponse -> settingsService.saveString("Token", loginResponse.access_token) }
        .thenApply { dataRepository.getZone().get() }
        .thenApply { zones -> parseZone(zones)}

        .exceptionally { throwable -> handleExceptions(throwable)}
    }
}