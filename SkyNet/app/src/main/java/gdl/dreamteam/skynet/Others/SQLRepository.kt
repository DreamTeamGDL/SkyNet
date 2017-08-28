package gdl.dreamteam.skynet.Others

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.google.gson.Gson
import java.nio.ByteBuffer
import java.util.*

/**
 * Created by christopher on 28/08/17.
 */
class SQLRepository (
    context: Context
) : IDataRepository, SQLiteOpenHelper (
    context,
    database,
    null,
    version
) {
    companion object {
        const val database = "database.db"
        const val version = 1
    }

    override fun onCreate(db: SQLiteDatabase?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getZone(name: String): Zone? {
        var query = "Select data From Zones Where name = $name"
        val cursor = readableDatabase.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            val rawJson: ByteArray = cursor.getBlob(0)
            var gson = Gson()
            val json = Charsets.UTF_8.decode(ByteBuffer.wrap(rawJson))
            return gson.fromJson(json.toString(), Zone::class.java)
        }
        return null
    }

}