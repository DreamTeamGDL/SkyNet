package gdl.dreamteam.skynet.Others

import android.support.test.InstrumentationRegistry
import org.junit.Test

import org.junit.Assert.*
import java.util.*

/**
 * Created by christopher on 28/08/17.
 */
class SQLRepositoryTest {
    @Test
    fun getZone() {
        val context = InstrumentationRegistry.getTargetContext()
        var dataRepo: IDataRepository = SQLRepository(context)
        var zone = dataRepo.getZone("room1")

        assertNotNull(zone)

        var zone1 = Zone(
            "room1",
            arrayOf(Client(
                "room1",
                "aosiherwela",
                arrayOf(Device(
                    UUID.randomUUID().toString(),
                    "fan",
                    Fan(25.2f, 0.45f, 1)
                ))
            ))
        )
        (zone!!.clients[0].devs[0].data as Fan).status = true

        assertEquals(zone, zone1)
    }

}