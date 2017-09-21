package gdl.dreamteam.skynet.Bindings

import android.databinding.ObservableField
import android.databinding.ViewDataBinding

/**
 * Created by Cesar on 20/09/17.
 */
class DeviceFanBinding (fanStatus: Boolean, fanTemperature: Float, fanHumidity: Float, fanSpeed: Int){

    var fanStatus: ObservableField<Boolean>
    var fanTemperature: ObservableField<Float>
    var fanHumidity: ObservableField<Float>
    var fanSpeed: ObservableField<Int>

    init {
        this.fanStatus = ObservableField(fanStatus)
        this.fanTemperature = ObservableField(fanTemperature)
        this.fanHumidity = ObservableField(fanHumidity)
        this.fanSpeed = ObservableField(fanSpeed)
    }

    var status: Boolean
        get() = fanStatus.get()
        set(value) { fanStatus.set(value) }

    var temperature: Float
        get() = fanTemperature.get()
        set(value) { fanTemperature.set(value) }

    var humidity: Float
        get() = fanHumidity.get()
        set(value) { fanHumidity.set(value) }

    var speed: Int
        get() = fanSpeed.get()
        set(value) { fanSpeed.set(value) }

}

