package gdl.dreamteam.skynet.Fragments

import android.app.Fragment
import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.annotation.Nullable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import gdl.dreamteam.skynet.Bindings.DeviceFanBinding
import gdl.dreamteam.skynet.Bindings.DeviceLightsBinding
import gdl.dreamteam.skynet.Models.Fan
import gdl.dreamteam.skynet.Models.Light
import gdl.dreamteam.skynet.Others.RestRepository
import gdl.dreamteam.skynet.R
import gdl.dreamteam.skynet.databinding.FanBinding
import gdl.dreamteam.skynet.databinding.LightsBinding

/**
 * Created by Cesar on 20/09/17.
 */

class DeviceLightsFragment : Fragment() {


    private lateinit var mListener: OnFragmentInteractionListener

    // Empty Constructor
    fun DeviceFanFragment(){

    }

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

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState);
    }

    override fun onCreateView(inflater: LayoutInflater?,
                              @Nullable container: ViewGroup?,
                              @Nullable savedInstanceState: Bundle?): View? {

        val binding: LightsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_device_lights, container, false)
        val view = binding.root

        val voltage = arguments.getInt(ARG_VOLTAGE)
        val timeOn = (arguments.getLong(ARG_TIMEON)/3600)
        val energy = (voltage * timeOn).toString() + " KwH/day"

        binding.deviceLights = DeviceLightsBinding(
                arguments.getBoolean(ARG_STATUS),
                energy
        )
        return view
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            mListener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
    }


    interface OnFragmentInteractionListener {
        fun somethingHappened(message: String)
    }

}