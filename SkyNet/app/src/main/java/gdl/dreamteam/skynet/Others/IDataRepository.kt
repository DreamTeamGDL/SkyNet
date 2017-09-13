package gdl.dreamteam.skynet.Others

import java.util.concurrent.CompletableFuture
import java.util.concurrent.Future

/**
 * Created by christopher on 28/08/17.
 */
interface IDataRepository {
    fun addZone(zone: Zone): CompletableFuture<Boolean>
    fun getZone(name: String): CompletableFuture<Zone?>
    fun deleteZone(name: String): CompletableFuture<Boolean>
    fun updateZone(name: String, zone: Zone): CompletableFuture<Boolean>
}