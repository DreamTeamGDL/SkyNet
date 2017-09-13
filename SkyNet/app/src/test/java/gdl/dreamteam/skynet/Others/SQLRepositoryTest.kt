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
    private lateinit var repo: IDataRepository

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
                    "Fan"//Fan(25.2f, 0.45f, 1)
                ))
            ))
        )
        //(zone.clients[0].devices[0].data as Fan).status = true
    }

    @Test
    fun setZone() {
        val result = repo.addZone(zone).get()
        assertTrue(result)
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
        val result: Boolean = repo.deleteZone("room1").get()
        assertTrue(result)
        val repoZone: Zone? = repo.getZone("room1").get()
        assertNull(repoZone)
    }

    @Test
    fun updateZone() {
        setZone()
        zone.name = "room2"
        val result: Boolean = repo.updateZone("room1", zone).get()
        assertTrue(result)
        val zone: Zone? = repo.getZone("room2").get()
        assertNotNull(zone)
        assertEquals(this.zone, zone)
    }

}