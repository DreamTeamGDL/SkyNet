package gdl.dreamteam.skynet.Activities

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.TextView
import gdl.dreamteam.skynet.Models.*
import gdl.dreamteam.skynet.Others.LoginService
import gdl.dreamteam.skynet.Others.RestRepository
import gdl.dreamteam.skynet.Others.SettingsService

import gdl.dreamteam.skynet.R
import java.net.URI

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
    private lateinit var settingsService : SettingsService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clients)
        val title = findViewById(R.id.titleMain) as TextView
        if (intent.hasExtra("zone")) loadElements(intent, title)


    }

    private fun loadElements(intent: Intent, title: TextView) {
        if (intent.hasExtra("zone")) loadElements(intent)

        val toolbar = findViewById(R.id.toolBar) as Toolbar
        setSupportActionBar(toolbar)
        assert(supportActionBar != null)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        settingsService = SettingsService(applicationContext)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.action_settings -> signOut()
            R.id.supportRequired -> launchPhone()
        }

        return true
    }

    private fun launchPhone(){
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel://+523519134806"))
        startActivity(intent)
    }

    private fun signOut(){
        clearToken()

        val i = Intent(this, MainActivity::class.java)
        i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(i)
    }

    private fun clearToken(){
        LoginService.accessToken = ""
        settingsService.saveString("Token", "")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }


    private fun loadElements(intent: Intent) {
        val rawZone = intent.extras.getString("zone")
        Log.wtf("zone", rawZone)
        zone = RestRepository.gson.fromJson(rawZone, Zone::class.java)

        title.text = zone.name

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
            for (item in content) {
                val view = inflater.inflate(R.layout.item_clients, layout, false) as TextView
                view.text = item
                view.setOnClickListener {
                    val intent = Intent(this, DeviceActivity::class.java)
                    val (device, client) = findDevice(zone, item, deviceType)
                    val rawJson = RestRepository.gson.toJson(device, Device::class.java)
                    intent.putExtra("device", rawJson)
                    intent.putExtra("clientName", client)
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

    fun findDevice(zone: Zone, name: String, type: String): Pair<Device?, String> {
        for (client in zone.clients) {
            for (device in client.devices) {
                val data = device.data
                if (name == device.name && type == data.javaClass.canonicalName) {
                    return Pair(device, client.name)
                }
            }
        }
        return Pair(null, "")
    }


}
