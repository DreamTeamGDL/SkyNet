package gdl.dreamteam.skynet.Fragments

import android.annotation.SuppressLint
import android.app.Fragment
import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import gdl.dreamteam.skynet.Bindings.DeviceCameraBinding
import gdl.dreamteam.skynet.Bindings.DeviceLightsBinding
import gdl.dreamteam.skynet.Extensions.shortToast
import gdl.dreamteam.skynet.Models.Camera
import gdl.dreamteam.skynet.Others.RestRepository
import gdl.dreamteam.skynet.R
import gdl.dreamteam.skynet.databinding.CameraBinding
import gdl.dreamteam.skynet.databinding.FanBinding
import gdl.dreamteam.skynet.databinding.LightsBinding
import android.media.MediaPlayer
import android.net.Uri
import android.support.customtabs.CustomTabsClient.getPackageName
import android.util.Base64
import android.widget.ImageView
import android.widget.MediaController
import android.widget.VideoView





@SuppressLint("ParcelCreator")
/**
 * Created by Cesar on 12/11/17.
 */
class DeviceCameraFragment() : Fragment() {

    private lateinit var mListener: DeviceFragmentListener


    // Factory method to create an instance of this fragment
    companion object {

        const val ARG_STATUS = "status"

        fun newInstance(data: String): DeviceCameraFragment {
            Log.d("FRAGMENT", data)
            val device = RestRepository.gson.fromJson(data, Camera::class.java)
            val fragment = DeviceCameraFragment()
            val args = Bundle()

            fragment.arguments = args

            args.putBoolean(ARG_STATUS, device.status)

            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater?,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        val binding: CameraBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_device_camera, container, false)
        val view = binding.root

        binding.deviceCamera = DeviceCameraBinding(arguments.getBoolean(ARG_STATUS))
        val image = view.findViewById<ImageView>(R.id.noVideoImage)
        image.setImageResource(R.drawable.novideo)

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