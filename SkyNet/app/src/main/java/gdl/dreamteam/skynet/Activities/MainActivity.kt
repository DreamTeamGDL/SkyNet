package gdl.dreamteam.skynet.Activities

import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import gdl.dreamteam.skynet.Bindings.LoginBinding
import gdl.dreamteam.skynet.Exceptions.ForbiddenException
import gdl.dreamteam.skynet.Exceptions.InternalErrorException
import gdl.dreamteam.skynet.Exceptions.UnauthorizedException
import gdl.dreamteam.skynet.Extensions.longToast
import gdl.dreamteam.skynet.Extensions.shortToast
import gdl.dreamteam.skynet.Models.*
import gdl.dreamteam.skynet.Others.IDataRepository
import gdl.dreamteam.skynet.Others.LoginService
import gdl.dreamteam.skynet.Others.RestRepository
import gdl.dreamteam.skynet.R
import gdl.dreamteam.skynet.databinding.MainBinding
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ExecutionException
import java.util.logging.Logger

class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainBinding
    private lateinit var dataRepository: IDataRepository
    val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        dataRepository = RestRepository()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.login = LoginBinding()
    }

    fun onLoginPress(view: View) {
      /* 
      Example of Zone
      val zone = Zone(
            "Living Room",
            arrayOf(
                Client("Dragon", "Board", arrayOf(
                    Device("Entrance", Fan(10.0f, 0.24f, 2)),
                    Device("Main", Light()),
                    Device("Hall", Light()),
                    Device("Entrance", Light())
                )),
                Client("Rasperry", "Pi", arrayOf(
                    Device("Hall", Fan(24.0f, 0.14f, 5)),
                    Device("Stairs", Light()),
                    Device("Lobby", Light()),
                    Device("Table", Light())
                ))
            )
        )
        */
        val username = binding.login.username
        val password = binding.login.password
        if (username == "" || username == null) {
            longToast("Please put a username")
            return
        }
        if (password == "" || password == null) {
            longToast("Please put a password")
            return
        }
        LoginService.setup(applicationContext)
        LoginService.login(
            binding.login.username as String,
            binding.login.password as String
        ).thenApply {
            return@thenApply dataRepository.getZone("livingroom").get()
        }.thenApply { zone ->
            val intent = Intent(this, ClientsActivity::class.java)
            val rawZone = RestRepository.gson.toJson(zone, Zone::class.java)
            intent.putExtra("zone", rawZone)
            handler.post {
                startActivity(intent)
            }
        }.exceptionally { throwable ->
            Log.wtf("Exception", throwable.cause.toString())
            when(throwable.cause) {
                is UnauthorizedException, is ForbiddenException -> {
                    handler.post {
                        shortToast("Please introduce valid credentials")
                    }
                }
                is InternalErrorException -> {
                    handler.post {
                        shortToast("Uups, that was a server error, try again in a few moments")
                    }
                } else -> {
                    return@exceptionally null
                }
            }
        }
    }
}
