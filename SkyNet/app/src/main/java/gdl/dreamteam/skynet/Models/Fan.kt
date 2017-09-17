package gdl.dreamteam.skynet.Models

import gdl.dreamteam.skynet.Models.AbstractDeviceData

/**
 * Created by christopher on 28/08/17.
 */
class Fan(
    var temperature: Float,
    var humidity: Float,
    var speed: Int
) : AbstractDeviceData()