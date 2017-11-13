package gdl.dreamteam.skynet.Models

import java.util.*

/**
 * Created by christopher on 28/08/17.
 */
class Zone (
    var id: UUID,
    var name: String,
    var imageIndex: Int,
    var clients: Array<Client>
) {
    companion object {
        val mock = Zone(
            UUID.randomUUID(),
            "Living Room",
            1,
            arrayOf(
                Client(UUID.randomUUID(),
                    "Dragon", "Board", arrayOf(
                    Device(UUID.randomUUID(), "Entrance", Fan(10.0f, 0.24f, 2)),
                    Device(UUID.randomUUID(), "Main", Light(20, 10L)),
                    Device(UUID.randomUUID(), "Hall", Light(40, 50L)),
                    Device(UUID.randomUUID(), "Entrance", Light(10, 70L)),
                    Device(UUID.randomUUID(), "Garden", Camera(""))
                )),
                Client(UUID.randomUUID(), "Rasperry", "Pi", arrayOf(
                    Device(UUID.randomUUID(), "Hall", Fan(24.0f, 0.14f, 5))
                ))
            )
        )
    }

    override fun equals(other: Any?): Boolean {
        val that = other as Zone
        return this.name == that.name
    }
}