package gdl.dreamteam.skynet.Others

import gdl.dreamteam.skynet.Models.Zone
import java.util.concurrent.CompletableFuture
/**
 * Created by christopher on 28/08/17.
 */
interface IDataRepository {
    fun addZone(zone: Zone): CompletableFuture<Unit>
    fun getZone(): CompletableFuture<Array<Zone>?>
    fun getZone(name: String): CompletableFuture<Zone?>
    fun deleteZone(name: String): CompletableFuture<Unit>
    fun updateZone(name: String, zone: Zone): CompletableFuture<Unit>

    fun updateZoneName(zoneId: String, newName: String) : CompletableFuture<Unit>
}