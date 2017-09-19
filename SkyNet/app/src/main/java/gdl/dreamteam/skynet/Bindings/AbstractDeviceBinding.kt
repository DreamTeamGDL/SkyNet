package gdl.dreamteam.skynet.Bindings

import android.databinding.ObservableField

/**
 * Created by christopher on 17/09/17.
 */
class AbstractDeviceBinding (deviceName: String, deviceType: String) {

    private var deviceName = ObservableField(deviceName)
    private var deviceType = ObservableField(deviceType)

    var name: String
        get() = deviceName.get()
        set(value) { deviceName.set(value) }

    var type: String
        get() = deviceType.get()
        set(value) { deviceType.set(value) }


}