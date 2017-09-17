package gdl.dreamteam.skynet.Others

import gdl.dreamteam.skynet.Models.Client
import gdl.dreamteam.skynet.Models.Device
import gdl.dreamteam.skynet.Models.Fan
import gdl.dreamteam.skynet.Models.Zone
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import java.util.*

/**
 * Created by christopher on 10/09/17.
 */
class RestRepositoryTest {

    private lateinit var repo: RestRepository

    @Before
    fun setup() {
        repo = RestRepository()
    }

    @Test
    fun addZone() {
        val zone = Zone(
            "zoneName", arrayOf(Client("device",
                UUID.randomUUID().toString(),
                arrayOf(Device(
                    "fan1",
                    Fan(25.0f, 0.45f, 2)
                ))
            ))
        )
        repo.addZone(zone).get()
    }

    @Test
    fun getZone() {
        val zone: Zone? = repo.getZone("Zone").get()
        assertNotNull(zone)
    }

}