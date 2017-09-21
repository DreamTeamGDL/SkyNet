package gdl.dreamteam.skynet.Models

import java.util.*

/**
 * Created by christopher on 28/08/17.
 */
class Client (
    var id: UUID,
    var name: String,
    var alias: String,
    var devices: Array<Device>
)