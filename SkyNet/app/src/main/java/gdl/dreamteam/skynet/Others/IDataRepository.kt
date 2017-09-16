package gdl.dreamteam.skynet.Others

import java.util.concurrent.CompletableFuture
import java.util.concurrent.Future

/**
 * Created by christopher on 28/08/17.
 */
interface IDataRepository {
    fun addZone(zone: Zone): CompletableFuture<Unit>
    fun getZone(name: String): CompletableFuture<Zone?>
    fun deleteZone(name: String): CompletableFuture<Unit>
    fun updateZone(name: String, zone: Zone): CompletableFuture<Unit>
}