package gdl.dreamteam.skynet.Others

import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

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

    }

    @Test
    fun getZone() {
        val zone: Zone? = repo.getZone("").get()
        assertNotNull(zone)
    }

}