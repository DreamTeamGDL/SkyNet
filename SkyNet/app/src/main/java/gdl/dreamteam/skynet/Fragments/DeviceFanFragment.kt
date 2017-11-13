package gdl.dreamteam.skynet.Fragments

import android.app.Fragment
import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import gdl.dreamteam.skynet.Bindings.DeviceFanBinding
import gdl.dreamteam.skynet.Extensions.shortToast
import gdl.dreamteam.skynet.Models.Fan
import gdl.dreamteam.skynet.Others.RestRepository
import gdl.dreamteam.skynet.R
import gdl.dreamteam.skynet.databinding.FanBinding


/**
 * Created by Cesar on 18/09/17.
 */


class DeviceFanFragment : Fragment() {

    private lateinit var mListener: DeviceFragmentListener
    private lateinit var binding: FanBinding

    // Factory method to create an instance of this fragment
    companion object {

        const val ARG_STATUS = "status"
        const val ARG_TEMPERATURE = "temperature"
        const val ARG_HUMIDITY = "humidity"
        const val ARG_SPEED = "speed"

        fun newInstance(data: String): DeviceFanFragment {
            Log.d("FRAGMENT", data)
            val device = RestRepository.gson.fromJson(data, Fan::class.java)
            val fragment = DeviceFanFragment()
            val args = Bundle()
            fragment.arguments = args

            args.putBoolean(ARG_STATUS, device.status)
            args.putFloat(ARG_TEMPERATURE, device.temperature)
            args.putFloat(ARG_HUMIDITY, device.humidity)
            args.putInt(ARG_SPEED, device.speed)

            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater?,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_device_fan, container, false)
        val view = binding.root

        // TODO: Add Toast to show interaction with buttons on Save Settings
        val button = view.findViewById<Button>(R.id.queueButton)
        button.setOnClickListener {
            activity.shortToast("Uploading new settings...")
            button.isEnabled = false
            mListener.somethingHappened("SPEED CHANGE ${binding.deviceFan.speed}")
        }

        binding.deviceFan = DeviceFanBinding(
            arguments.getBoolean(ARG_STATUS),
            arguments.getFloat(ARG_TEMPERATURE),
            arguments.getFloat(ARG_HUMIDITY),
            arguments.getInt(ARG_SPEED)
        )
        return view
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is DeviceFragmentListener) {
            mListener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }
}
