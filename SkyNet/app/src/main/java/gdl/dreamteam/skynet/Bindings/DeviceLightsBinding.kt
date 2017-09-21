package gdl.dreamteam.skynet.Bindings

import android.databinding.ObservableField

/**
 * Created by Cesar on 20/09/17.
 */

class DeviceLightsBinding (lightStatus: Boolean){

    lateinit var lightStatus: ObservableField<Boolean>

    init {
        this.lightStatus = ObservableField(lightStatus)
    }

    var status: Boolean
        get() = lightStatus.get()
        set(value) { lightStatus.set(value) }

}