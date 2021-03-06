package gdl.dreamteam.skynet.Others

import gdl.dreamteam.skynet.BuildConfig
import gdl.dreamteam.skynet.Models.Client
import gdl.dreamteam.skynet.Models.Device
import gdl.dreamteam.skynet.Models.Fan
import gdl.dreamteam.skynet.Models.Zone
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import java.util.*

/**
 * Created by christopher on 31/08/17.
 */

@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class)
class SQLRepositoryTest {

    private lateinit var zone: Zone
    private lateinit var repo: IDataRepository

    @Before
    fun setup() {
        repo = SQLRepository(RuntimeEnvironment.application)
        zone = Zone(
            UUID.randomUUID(),
            "room1",
            arrayOf(Client(
                UUID.randomUUID(),
                "room1",
                "aosiherwela",
                arrayOf(Device(
                    UUID.randomUUID(),
                    "",
                    Fan(25.2f, 0.45f, 1)
                ))
            ))
        )
        //(zone.clients[0].devices[0].data as Fan).status = true
    }

    @Test
    fun setZone() {
        val result = repo.addZone(zone).get()
    }

    @Test
    fun getZone() {
        setZone()
        val repoZone: Zone? = repo.getZone("room1").get()

        assertNotNull(repoZone)

        assertEquals(zone, repoZone)
    }

    @Test
    fun deleteZone() {
        setZone()
        repo.deleteZone("room1").get()
        val repoZone: Zone? = repo.getZone("room1").get()
        assertNull(repoZone)
    }

    @Test
    fun updateZone() {
        setZone()
        zone.name = "room2"
        repo.updateZone("room1", zone).get()
        val zone: Zone? = repo.getZone("room2").get()
        assertNotNull(zone)
        assertEquals(this.zone, zone)
    }

}