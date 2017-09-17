package gdl.dreamteam.skynet.Activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import gdl.dreamteam.skynet.Models.*
import gdl.dreamteam.skynet.Others.RestRepository
import gdl.dreamteam.skynet.R
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }


    fun onLoginPress(view: View) {
        val zone = Zone(
            "Living Room",
            arrayOf(
                Client("Dragon", "Board", arrayOf(
                    Device("Entrance", Fan(10.0f, 0.24f, 2)),
                    Device("Main", Light()),
                    Device("Hall", Light()),
                    Device("Entrance", Light()),
                    Device("Garden", Camera(""))
                ))
            )
        )
        val intent = Intent(this, ClientsActivity::class.java)
        val rawZone = RestRepository.gson.toJson(zone)
        intent.extras.putString("zone", rawZone)
        startActivity(intent)
    }
}
