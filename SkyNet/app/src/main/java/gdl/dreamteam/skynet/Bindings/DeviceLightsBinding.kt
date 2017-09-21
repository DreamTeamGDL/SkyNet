package gdl.dreamteam.skynet.Bindings

import android.databinding.ObservableField

/**
 * Created by Cesar on 20/09/17.
 */

class DeviceLightsBinding (lightStatus: Boolean, lightEnergy: String){

    var lightStatus: ObservableField<Boolean>
    var lightEnergy: ObservableField<String>

    init {
        this.lightStatus = ObservableField(lightStatus)
        this.lightEnergy = ObservableField(lightEnergy)
    }

    var status: Boolean
        get() = lightStatus.get()
        set(value) { lightStatus.set(value) }

    var energy: String
        get() = lightEnergy.get()
        set(value) { lightEnergy.set(value) }
}