package gdl.dreamteam.skynet.Activities

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import gdl.dreamteam.skynet.Others.RestRepository
import gdl.dreamteam.skynet.R

class EditNameActivity : AppCompatActivity() {

    lateinit var currNameView : TextView
    lateinit var newNameView : TextView

    var currName = ""
    var zoneId = ""

    val restRepository = RestRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_name)

        currNameView = findViewById(R.id.textName) as TextView
        newNameView = findViewById(R.id.editName) as TextView

        currName = intent.getStringExtra("zoneName")
        zoneId = intent.getStringExtra("zoneID")

        currNameView.text = currName
    }

    fun save(v: View){
        val newName = newNameView.text.toString()

        val intent = Intent()
        intent.putExtra("newName", newName)

        restRepository.updateZoneName(zoneId, newName)
            .thenApply {
                setResult(Activity.RESULT_OK, intent)
                runOnUiThread{ finish() }
            }
    }
}
