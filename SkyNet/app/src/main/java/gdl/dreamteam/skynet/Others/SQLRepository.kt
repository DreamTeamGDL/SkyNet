package gdl.dreamteam.skynet.Others

import com.google.gson.Gson

/**
 * Created by christopher on 28/08/17.
 */
class SQLRepository : IDataRepository {

    override fun getZone(name: String): Zone {
        var zone = Zone(
            "zone1",
            arrayOf(Client(
                "room1",
                "aosiherwela",
                arrayOf(Device(
                    "ailesjrwenf",
                    "fan",
                    Fan(25.2f, 0.45f, 1)
                ))
            ))
        )
        (zone.clients[0].devs[0].data as Fan).status = true
        var gson = Gson()
        println(gson.toJson(zone))
        return zone
    }

}