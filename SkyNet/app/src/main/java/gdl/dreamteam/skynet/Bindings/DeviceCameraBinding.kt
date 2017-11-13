package gdl.dreamteam.skynet.Bindings

import android.databinding.ObservableField

/**
 * Created by Cesar on 12/11/17.
 */
class DeviceCameraBinding (camStatus: Boolean){

    var camStatus: ObservableField<Boolean>

    init {
        this.camStatus = ObservableField(camStatus)
    }

    var status: Boolean
        get() = camStatus.get()
        set(value) { camStatus.set(value) }

}