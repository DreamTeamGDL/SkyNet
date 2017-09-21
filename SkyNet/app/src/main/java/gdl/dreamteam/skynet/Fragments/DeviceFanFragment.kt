package gdl.dreamteam.skynet.Fragments

import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import gdl.dreamteam.skynet.Others.RestRepository
import gdl.dreamteam.skynet.R
import android.support.annotation.Nullable
import gdl.dreamteam.skynet.Models.Fan
import android.databinding.DataBindingUtil
import android.util.Log
import gdl.dreamteam.skynet.Bindings.DeviceFanBinding
import gdl.dreamteam.skynet.databinding.FanBinding


/**
 * Created by Cesar on 18/09/17.
 */


class DeviceFanFragment : Fragment() {

    private lateinit var mListener: OnFragmentInteractionListener


    // Empty Constructor
    fun DeviceFanFragment(){

    }

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

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState);
    }

    override fun onCreateView(inflater: LayoutInflater?,
                              @Nullable container: ViewGroup?,
                              @Nullable savedInstanceState: Bundle?): View? {

        val binding: FanBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_device_fan, container, false)
        val view = binding.root

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
