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
import gdl.dreamteam.skynet.Bindings.DeviceLightsBinding
import gdl.dreamteam.skynet.Extensions.shortToast
import gdl.dreamteam.skynet.Models.Fan
import gdl.dreamteam.skynet.Models.Light
import gdl.dreamteam.skynet.Others.RestRepository
import gdl.dreamteam.skynet.R
import gdl.dreamteam.skynet.databinding.LightsBinding

/**
 * Created by Cesar on 20/09/17.
 */

class DeviceLightsFragment : Fragment() {


    private lateinit var mListener: DeviceFragmentListener

    // Factory method to create an instance of this fragment
    companion object {

        const val ARG_STATUS = "status"
        const val ARG_VOLTAGE = "voltage"
        const val ARG_TIMEON = "timeOn"

        fun newInstance(data: String): DeviceLightsFragment {
            Log.d("FRAGMENT", data)
            val device = RestRepository.gson.fromJson(data, Light::class.java)
            val fragment = DeviceLightsFragment()
            val args = Bundle()
            fragment.arguments = args

            args.putBoolean(ARG_STATUS, device.status)
            args.putInt(ARG_VOLTAGE, device.voltage)
            args.putLong(ARG_TIMEON, device.timeOn)

            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater?,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding: LightsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_device_lights, container, false)
        val view = binding.root

        val voltage = arguments.getInt(ARG_VOLTAGE)
        val timeOn = (arguments.getLong(ARG_TIMEON)/3600)
        val energy = (voltage * timeOn).toString() + " KwH/day"

        binding.deviceLights = DeviceLightsBinding(
            arguments.getBoolean(ARG_STATUS),
            energy
        )

        val button = view.findViewById<Button>(R.id.saveSettings)
        button.setOnClickListener {
            activity.shortToast("Uploading new settings...")
            button.isEnabled = false
            val status = if (binding.deviceLights.status) "TRUE" else "FALSE"
            mListener.somethingHappened(status)
        }


        // TODO: Add Toast to show interaction with buttons on Save Settings

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