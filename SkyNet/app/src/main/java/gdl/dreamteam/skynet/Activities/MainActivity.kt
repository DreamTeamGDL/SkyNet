package gdl.dreamteam.skynet.Activities

import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import gdl.dreamteam.skynet.Bindings.LoginBinding
import gdl.dreamteam.skynet.Exceptions.ForbiddenException
import gdl.dreamteam.skynet.Exceptions.InternalErrorException
import gdl.dreamteam.skynet.Exceptions.UnauthorizedException
import gdl.dreamteam.skynet.Models.*
import gdl.dreamteam.skynet.Others.LoginService
import gdl.dreamteam.skynet.Others.RestRepository
import gdl.dreamteam.skynet.R
import gdl.dreamteam.skynet.databinding.MainBinding
import java.util.concurrent.ExecutionException

class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainBinding
    private lateinit var usernameView: TextView
    private lateinit var passwordView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.login = LoginBinding()

        usernameView = findViewById(R.id.textUsername) as TextView
        passwordView = findViewById(R.id.textPassword) as TextView
    }


    fun onLoginPress(view: View) {
        binding.login.username = usernameView.text.toString()
        binding.login.password = passwordView.text.toString()

        if (binding.login.username == "") {
            Toast.makeText(this, "Please put a username", Toast.LENGTH_LONG).show()
            return
        }
        if (binding.login.password == "") {
            Toast.makeText(this, "Please put a password", Toast.LENGTH_LONG).show()
            return
        }

        LoginService.setup(applicationContext)
        try {
            LoginService.login(
                binding.login.username as String,
                binding.login.password as String
            ).get()
        } catch (e: ExecutionException) {
            Log.wtf("Exception", e.cause.toString())
            when(e.cause) {
                is UnauthorizedException, is ForbiddenException -> {
                    errorMessage("Please introduce valid credentials")
                }
                is InternalErrorException -> {
                    errorMessage("Uups, that was a server error, try again in a few moments")
                }
            }
            return
        }
        val zone = Zone(
            "Living Room",
            arrayOf(
                Client("Dragon", "Board", arrayOf(
                    Device("Entrance", Fan(10.0f, 0.24f, 2)),
                    Device("Main", Light()),
                    Device("Hall", Light()),
                    Device("Entrance", Light()),
                    Device("Garden", Camera(""))
                )),
                Client("Rasperry", "Pi", arrayOf(
                    Device("Hall", Fan(24.0f, 0.14f, 5))
                ))
            )
        )
        val intent = Intent(this, ClientsActivity::class.java)
        val rawZone = RestRepository.gson.toJson(zone, zone::class.java)
        println(rawZone)
        intent.putExtra("zone", rawZone)
        startActivity(intent)
    }

    fun errorMessage(cause: String) {
        Toast.makeText(this, cause, Toast.LENGTH_SHORT).show()
    }
}
