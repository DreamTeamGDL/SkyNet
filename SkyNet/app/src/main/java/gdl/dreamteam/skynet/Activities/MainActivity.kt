package gdl.dreamteam.skynet.Activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import gdl.dreamteam.skynet.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }


    fun onLoginPress(view: View){

        startActivity(Intent(this, ClientsActivity::class.java))

    }




}
