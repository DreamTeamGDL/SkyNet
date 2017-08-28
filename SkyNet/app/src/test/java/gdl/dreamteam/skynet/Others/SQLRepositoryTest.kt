package gdl.dreamteam.skynet.Others

import org.junit.Test

import org.junit.Assert.*

/**
 * Created by christopher on 28/08/17.
 */
class SQLRepositoryTest {
    @Test
    fun getZone() {
        var dataRepo: IDataRepository = SQLRepository()
        var zone: Zone = dataRepo.getZone("room1")
        assert(true)
    }

}