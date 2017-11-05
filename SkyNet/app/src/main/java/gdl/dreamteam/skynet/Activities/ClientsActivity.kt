package gdl.dreamteam.skynet.Activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import gdl.dreamteam.skynet.Models.*
import gdl.dreamteam.skynet.Others.RestRepository

import gdl.dreamteam.skynet.R

class ClientsActivity : AppCompatActivity() {

    companion object {
        private val deviceTypes = arrayOf(
            Fan::class.java.canonicalName,
            Light::class.java.canonicalName,
            Camera::class.java.canonicalName
        )
    }

    private val clients = ArrayList<LinearLayout>()
    private val values = ArrayList<List<String>>()
    private lateinit var zone: Zone

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clients)
        if (intent.hasExtra("zone")) loadElements(intent)
    }

    private fun loadElements(intent: Intent) {
        val rawZone = intent.extras.getString("zone")
        Log.wtf("zone", rawZone)
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
            val deviceType = deviceTypes[i]
            for (item in content){
                val view = inflater.inflate(R.layout.item_clients, layout, false) as TextView
                view.text = item
                view.setOnClickListener {
                    val intent = Intent(this, DeviceActivity::class.java)
                    val rawJson = RestRepository.gson.toJson(findDevice(zone, item, deviceType))
                    intent.putExtra("device", rawJson)
                    startActivity(intent)
                }
                layout.addView(view)
            }
        }
    }

    private fun <T> extractDeviceNames(zone: Zone, deviceType: Class<T>): List<String> {
        val result = ArrayList<String>()
            for (client in zone.clients) {
                for (device in client.devices) {
                    if (device.data.javaClass.canonicalName == deviceType.canonicalName) {
                        result.add(device.name)
                    }
                }
        }
        result.sort()
        return result
    }

    fun findDevice(zone: Zone, name: String, type: String): Device? {
        for (client in zone.clients) {
            for (device in client.devices) {
                val data = device.data
                if (name == device.name && type == data.javaClass.canonicalName) {
                    return device
                }
            }
        }
        return null
    }


}
