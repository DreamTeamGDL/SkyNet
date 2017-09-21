package gdl.dreamteam.skynet.Activities

import android.app.Fragment
import android.content.ComponentName
import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.util.Log
import android.widget.Toast
import gdl.dreamteam.skynet.Bindings.AbstractDeviceBinding
import gdl.dreamteam.skynet.Bindings.DeviceLightsBinding
import gdl.dreamteam.skynet.Fragments.DeviceFanFragment
import gdl.dreamteam.skynet.Fragments.DeviceLightsFragment
import gdl.dreamteam.skynet.Models.AbstractDeviceData
import gdl.dreamteam.skynet.Models.Device
import gdl.dreamteam.skynet.Models.Fan
import gdl.dreamteam.skynet.Models.Light
import gdl.dreamteam.skynet.Others.LoginService
import gdl.dreamteam.skynet.Others.RestRepository

import gdl.dreamteam.skynet.R
import gdl.dreamteam.skynet.databinding.DeviceBinding

class DeviceActivity : FragmentActivity(), DeviceFanFragment.OnFragmentInteractionListener, DeviceLightsFragment.OnFragmentInteractionListener {

    lateinit var fragment: Fragment


    companion object {
        const val DEVICE_TYPE_FAN = "Fan"
        const val DEVICE_TYPE_LIGHTS = "Light"
        // const val DEVICE_TYPE_CAMERAS = "Cameras"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_device)

        Log.wtf("access token", LoginService.accessToken)

        if (intent.hasExtra("device")) {
            val rawJson = intent.extras.getString("device")
            val device = RestRepository.gson.fromJson(rawJson, Device::class.java)
            val binding: DeviceBinding = DataBindingUtil.setContentView(this, R.layout.activity_device)
            // Log.wtf("DEVICE", rawJson)
            binding.abstractDevice = AbstractDeviceBinding(
                device.name,
                device.data.javaClass.simpleName
            )
            addFragment(device.data.javaClass.simpleName, device)
        }
    }

    private fun addFragment(type: String, device: Device){
        val manager = fragmentManager
        val transaction = manager.beginTransaction()

        if(type.equals(DEVICE_TYPE_FAN)){
            val data = RestRepository.gson.toJson(device.data, Fan::class.java)
            fragment = DeviceFanFragment.newInstance(data)
        }
        else if (type.equals(DEVICE_TYPE_LIGHTS)){
            val data = RestRepository.gson.toJson(device.data, Light::class.java)
            fragment = DeviceLightsFragment.newInstance(data)
        }

        if (fragment != null){
            transaction.add(R.id.fragmentContainer, fragment, type)
            transaction.commit()
        }

    }

    override fun somethingHappened(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}
