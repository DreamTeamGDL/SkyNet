package gdl.dreamteam.skynet.Activities

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View

import gdl.dreamteam.skynet.R
import android.widget.Toast
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.GridView
import com.google.gson.reflect.TypeToken
import gdl.dreamteam.skynet.Adapter.ZonesAdapter
import gdl.dreamteam.skynet.Extensions.bork
import gdl.dreamteam.skynet.Extensions.longToast
import gdl.dreamteam.skynet.Extensions.shortToast
import gdl.dreamteam.skynet.Models.Client
import gdl.dreamteam.skynet.Models.Zone
import gdl.dreamteam.skynet.Others.IDataRepository
import gdl.dreamteam.skynet.Others.LoginService
import gdl.dreamteam.skynet.Others.RestRepository
import gdl.dreamteam.skynet.Others.SettingsService


class ZonesActivity : AppCompatActivity() {

    private val repository = RestRepository()
    private val uiThread = Handler(Looper.getMainLooper())
    private lateinit var settingsService : SettingsService
    private lateinit var dataRepository: IDataRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_zones)

        if (intent.hasExtra("zones")){
            loadZones(intent)
        }
        else {
            dataRepository = RestRepository()
            dataRepository.getZone()
                .thenApply { zones -> uiThread.post{ loadZones(zones) } }
        }

        val toolbar = findViewById(R.id.toolBar2) as Toolbar
        setSupportActionBar(toolbar)
        assert(supportActionBar != null)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setBackgroundDrawable(getDrawable(R.drawable.custom_toolbar))

        settingsService = SettingsService(applicationContext)
    }

    private fun loadZones(intent: Intent){
        val rawJson = intent.getStringExtra("zones")
        val zones: Array<Zone> = RestRepository.gson.fromJson(rawJson, Array<Zone>::class.java)
        renderZones(zones)
    }

    private fun loadZones(zones: Array<Zone>?) = renderZones(zones!!)

    private fun renderZones(zones: Array<Zone>){
        val gridview = findViewById(R.id.gridview) as GridView
        gridview.adapter = ZonesAdapter(this, zones)
        gridview.setOnItemClickListener { _, _, position, _ ->
            val intent = Intent(this, ClientsActivity::class.java)
            repository.getZone(zones[position].name)
                    .thenApply { zone ->
                        Log.wtf("Zone", zone.toString())
                        val zoneJson = RestRepository.gson.toJson(zone, Zone::class.java)
                        intent.putExtra("zone", zoneJson)
                        uiThread.post {
                            shortToast("Opening ${zones[position].name}...")
                            startActivity(intent)
                        }
                    }
                    .exceptionally { throwable ->
                        Log.wtf("Error", throwable.message)
                        return@exceptionally true
                    }
        }
    }


    // Handle Navigation
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.action_settings -> signOut()
            R.id.supportRequired -> launchPhone()
        }
        return true
    }

    // Options Menu
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
    fun easterEgg(view: View){
        bork()
        Toast.makeText(this, "PÓNGASE A PROGRAMAR", Toast.LENGTH_SHORT).show()
    }
    private fun clearToken(){
        LoginService.accessToken = ""
        settingsService.saveString("Token", "")
    }
}

