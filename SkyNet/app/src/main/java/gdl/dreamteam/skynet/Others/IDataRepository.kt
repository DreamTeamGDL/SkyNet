package gdl.dreamteam.skynet.Others

/**
 * Created by christopher on 28/08/17.
 */
interface IDataRepository {
    fun addZone(zone: Zone): Boolean
    fun getZone(name: String): Zone?
    fun deleteZone(name: String): Boolean
    fun updateZone(name: String, zone: Zone): Boolean
}