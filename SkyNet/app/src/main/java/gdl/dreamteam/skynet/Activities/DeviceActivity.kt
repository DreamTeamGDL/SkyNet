package gdl.dreamteam.skynet.Activities

import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import gdl.dreamteam.skynet.Bindings.AbstractDeviceBinding
import gdl.dreamteam.skynet.Models.AbstractDeviceData
import gdl.dreamteam.skynet.Models.Device
import gdl.dreamteam.skynet.Others.RestRepository

import gdl.dreamteam.skynet.R
import gdl.dreamteam.skynet.databinding.DeviceBinding

class DeviceActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_device)

        if (intent.hasExtra("device")) {
            val rawJson = intent.extras.getString("device")
            val device = RestRepository.gson.fromJson(rawJson, Device::class.java)
            val binding: DeviceBinding = DataBindingUtil.setContentView(this, R.layout.activity_device)
            binding.abstractDevice = AbstractDeviceBinding(
                device.id,
                device.data.javaClass.simpleName
            )
        }
    }
}
