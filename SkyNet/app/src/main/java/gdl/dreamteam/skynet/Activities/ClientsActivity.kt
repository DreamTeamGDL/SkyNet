package gdl.dreamteam.skynet.Activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView
import gdl.dreamteam.skynet.Models.Camera
import gdl.dreamteam.skynet.Models.Fan
import gdl.dreamteam.skynet.Models.Light
import gdl.dreamteam.skynet.Models.Zone
import gdl.dreamteam.skynet.Others.RestRepository

import gdl.dreamteam.skynet.R
import java.util.zip.Inflater

class ClientsActivity : AppCompatActivity() {

    private val clients = ArrayList<LinearLayout>()
    private val values = ArrayList<List<String>>()
    private lateinit var zone: Zone

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clients)

        if (intent.hasExtra("zone")) {
            loadElements(intent)
        }

    }

    private fun loadElements(intent: Intent) {
        val rawZone = intent.extras.getString("zone")
        zone = RestRepository.gson.fromJson(rawZone, Zone::class.java)

        clients.add(findViewById(R.id.linearLayout1) as LinearLayout)
        clients.add(findViewById(R.id.linearLayout2) as LinearLayout)
        clients.add(findViewById(R.id.linearLayout3) as LinearLayout)

        values.add(extractDeviceNames(zone, Fan::class.java))
        values.add(extractDeviceNames(zone, Light::class.java))
        values.add(extractDeviceNames(zone, Camera::class.java))

        val inflater = LayoutInflater.from(this)

        for (i in clients.indices){
            val layout = clients[i]
            val content = values[i]
            for (item in content){
                val view = inflater.inflate(R.layout.item_clients, layout, false) as TextView
                view.text = item
                view.setOnClickListener {
                    startActivity(Intent(this, DeviceActivity::class.java))
                }
                layout.addView(view)
            }
        }
    }

    fun <T> extractDeviceNames(zone: Zone, deviceType: Class<T>): List<String> {
        val result = ArrayList<String>()
        for (client in zone.clients) {
            for (device in client.devices) {
                if (device.data.javaClass.canonicalName == deviceType.canonicalName) {
                    result.add(device.id)
                }
            }
        }
        return result
    }


}
