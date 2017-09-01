package gdl.dreamteam.skynet.Others

import gdl.dreamteam.skynet.BuildConfig
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
    private lateinit var repo: SQLRepository

    @Before
    fun setup() {
        repo = SQLRepository(RuntimeEnvironment.application)
        zone = Zone (
            "room1",
            arrayOf(Client(
                "room1",
                "aosiherwela",
                arrayOf(Device(
                    UUID.randomUUID().toString(),
                    Fan(25.2f, 0.45f, 1)
                ))
            ))
        )
        (zone.clients[0].devs[0].data as Fan).status = true
    }

    @Test
    fun setZone() {
        var result = repo.addZone(zone)
        assertTrue(result)
    }

    @Test
    fun getZone() {
        setZone()
        var repoZone: Zone? = repo.getZone("room1")

        assertNotNull(repoZone)

        assertEquals(zone, repoZone)
    }

    @Test
    fun deleteZone() {
        setZone()
        var result: Boolean = repo.deleteZone("room1")
        assertTrue(result)
        var repoZone: Zone? = repo.getZone("room1")
        assertNull(repoZone)
    }

    @Test
    fun updateZone() {
        setZone()
        zone.name = "room2"
        var result: Boolean = repo.updateZone("room1", zone)
        assertTrue(result)
        var zone: Zone? = repo.getZone("room2")
        assertEquals(this.zone, zone)
    }

}