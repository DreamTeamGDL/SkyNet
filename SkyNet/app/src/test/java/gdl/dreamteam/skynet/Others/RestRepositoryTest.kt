package gdl.dreamteam.skynet.Others

import org.junit.Test

import org.junit.Assert.*

/**
 * Created by christopher on 10/09/17.
 */
class RestRepositoryTest {
    @Test
    fun getZone() {
        val repo = RestRepository()
        val zone: Zone? = repo.getZone("")
    }

}