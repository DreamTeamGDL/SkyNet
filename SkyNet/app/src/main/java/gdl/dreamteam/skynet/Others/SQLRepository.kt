package gdl.dreamteam.skynet.Others


import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory
import gdl.dreamteam.skynet.Models.*
import java.nio.ByteBuffer
import java.util.concurrent.CompletableFuture


/**
 * Created by christopher on 28/08/17.
 */
class SQLRepository constructor (
    context: Context
) : SQLiteOpenHelper (
    context,
    database,
    null,
    version
), IDataRepository {

    private var gson: Gson

    companion object {
        const val database = "database.db"
        const val version = 1
    }

    init {
        val typeAdapter = RuntimeTypeAdapterFactory
            .of(AbstractDeviceData::class.java, "type")
            .registerSubtype(Door::class.java)
            .registerSubtype(Fan::class.java)
            .registerSubtype(Light::class.java)
            .registerSubtype(Camera::class.java)
        this.gson = GsonBuilder()
            .registerTypeAdapterFactory(typeAdapter)
            .create()
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val query = "Create Table Zones ( " +
                    "Name Text Primary Key, " +
                    "Data Blob)"
        db?.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        val query = "Drop Database If Exists ?"
        val args = arrayOf("Zones")
        db?.execSQL(query, args)
    }

    override fun addZone(zone: Zone): CompletableFuture<Unit> {
        val json = gson.toJson(zone, Zone::class.java)
        val payload = Charsets.UTF_8.encode(json).array()
        val sql = "Insert Into Zones (Name, Data) Values(?, ?);"
        val sqlStatement = writableDatabase.compileStatement(sql)
        sqlStatement.clearBindings()
        sqlStatement.bindString(1, zone.name)
        sqlStatement.bindBlob(2, payload)
        return CompletableFuture()
    }

    override fun getZone(name: String): CompletableFuture<Zone?> {
        val query = "Select data From Zones Where name = \"$name\""
        val cursor = readableDatabase.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            val rawJson: ByteArray = cursor.getBlob(0)
            var json = Charsets.UTF_8.decode(ByteBuffer.wrap(rawJson)).toString()
            json = json.replace("\u0000", "") // Clean null chars after utf decode
            val result = gson.fromJson(json, Zone::class.java)
            return CompletableFuture.completedFuture(result)
        }
        return CompletableFuture.completedFuture(null)
    }

    override fun deleteZone(name: String): CompletableFuture<Unit> {
        val sql = "Delete From Zones Where Name = ?"
        val sqlStatement = writableDatabase.compileStatement(sql)
        sqlStatement.clearBindings()
        sqlStatement.bindString(1, name)
        val result = sqlStatement.executeUpdateDelete() != -1
        return CompletableFuture()
    }

    override fun updateZone(name: String, zone: Zone): CompletableFuture<Unit> {
        val sql = "Update Zones Set Data = ?, Name = ? Where Name = ?"
        val sqlStatement = writableDatabase.compileStatement(sql)
        sqlStatement.clearBindings()
        val json = gson.toJson(zone, Zone::class.java)
        val payload = Charsets.UTF_8.encode(json).array()
        sqlStatement.bindBlob(1, payload)
        sqlStatement.bindString(2, zone.name)
        sqlStatement.bindString(3, name)
        val result =  sqlStatement.executeUpdateDelete() != -1
        return CompletableFuture()
    }

}