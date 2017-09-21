package gdl.dreamteam.skynet.Others

import gdl.dreamteam.skynet.BuildConfig
import org.junit.Test

import org.junit.Assert.*
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.util.*

/**
 * Created by christopher on 21/09/17.
 */
@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class)
class QueueServiceTest {
    @Test
    fun configure() {
        QueueService.configure("DefaultEndpointsProtocol=https;AccountName=skynetgdl;AccountKey=KVJGcGdkiUg6rhDyDbvbgb5YfCf3zaQX3z78K5YFrW4zmjaGzAnUlZwCna4k7nhuq9sZU6uqb7dHdi3S5EODvw==;EndpointSuffix=core.windows.net",
            "queue1")
        assertNotNull(QueueService.queue)
    }

    @Test
    fun sendMessage() {
        QueueService.sendMessage("Hola Migue, que pedo que cuentas")
    }

}