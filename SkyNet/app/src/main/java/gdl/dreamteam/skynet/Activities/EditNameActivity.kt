package gdl.dreamteam.skynet.Activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import gdl.dreamteam.skynet.R

class EditNameActivity : AppCompatActivity() {

    private var button = findViewById(R.id.queueButton) as Button
    private var edit = findViewById(R.id.editName) as EditText
    private var name = findViewById(R.id.textName) as TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_name)


    }
}
