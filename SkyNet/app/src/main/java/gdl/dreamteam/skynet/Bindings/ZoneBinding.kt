package gdl.dreamteam.skynet.Bindings

import android.databinding.ObservableField
import gdl.dreamteam.skynet.Models.Zone

/**
 * Created by christopher on 16/09/17.
 */
class ZoneBinding (zone: Zone) {
    val zoneValue = ObservableField<Zone>()

    init {
        zoneValue.set(zone)
    }

    var zone: Zone
        get() = zoneValue.get()
        set(value) { zoneValue.set(value) }
}