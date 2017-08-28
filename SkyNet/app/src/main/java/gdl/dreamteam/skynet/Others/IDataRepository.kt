package gdl.dreamteam.skynet.Others

/**
 * Created by christopher on 28/08/17.
 */
interface IDataRepository {
    fun getZone(name: String): Zone?
}