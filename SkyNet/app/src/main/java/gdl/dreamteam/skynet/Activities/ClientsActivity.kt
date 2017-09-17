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

import gdl.dreamteam.skynet.R
import java.util.zip.Inflater

class ClientsActivity : AppCompatActivity() {

    private val clients = ArrayList<LinearLayout>()
    private val values = ArrayList<Array<String>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clients)

        clients.add(findViewById(R.id.linearLayout1) as LinearLayout)
        clients.add(findViewById(R.id.linearLayout2) as LinearLayout)
        clients.add(findViewById(R.id.linearLayout3) as LinearLayout)

        values.add(arrayOf("Principal"))
        values.add(arrayOf("Entrada", "Principal", "Puerta", "Pasillo"))
        values.add(arrayOf("Jardín", "Pasillo Frente", "Pasillo Fondo"))

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


}
