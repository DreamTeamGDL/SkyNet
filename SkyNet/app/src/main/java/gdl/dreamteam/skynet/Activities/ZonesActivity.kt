package gdl.dreamteam.skynet.Activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View

import gdl.dreamteam.skynet.R
import android.widget.Toast
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.GridView
import com.google.gson.reflect.TypeToken
import gdl.dreamteam.skynet.Adapter.ZonesAdapter
import gdl.dreamteam.skynet.Models.Client
import gdl.dreamteam.skynet.Models.Zone
import gdl.dreamteam.skynet.Others.RestRepository


class ZonesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_zones)

        if (intent.hasExtra("zones")){ loadZones(intent) }
    }

    private fun loadZones(intent: Intent){

        val rawJson = intent.getStringExtra("zones")
        val zones: Array<Zone> = RestRepository.gson.fromJson(rawJson, object : TypeToken<Array<Zone>>() {}.type)
        val gridview = findViewById(R.id.gridview) as GridView
        gridview.adapter = ZonesAdapter(this, zones)

        gridview.setOnItemClickListener { _, _, position, _ ->
            val intent = Intent(this, ClientsActivity::class.java)
            val rawJson = RestRepository.gson.toJson(zones[position], Zone::class.java)
            Toast.makeText(this@ZonesActivity, "Openning " + zones[position].name + "...", Toast.LENGTH_SHORT).show()
            intent.putExtra("zone", rawJson)
            startActivity(intent)
        }
    }
}

