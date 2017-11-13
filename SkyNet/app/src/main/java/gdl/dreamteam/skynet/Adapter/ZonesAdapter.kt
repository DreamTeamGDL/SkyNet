package gdl.dreamteam.skynet.Adapter

import android.app.ActionBar
import android.app.Activity
import android.content.Context
import android.databinding.adapters.ViewBindingAdapter.setPadding
import android.media.Image
import android.view.View
import android.widget.ImageView.ScaleType
import android.widget.GridView
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.ImageView.TEXT_ALIGNMENT_CENTER
import android.widget.TextView
import gdl.dreamteam.skynet.Models.Zone
import gdl.dreamteam.skynet.R
import org.w3c.dom.Text


/**
 * Created by Cesar on 31/10/17.
 */
class ZonesAdapter(private val activity: Activity, private val zones: Array<Zone>) : BaseAdapter() {

    private val zonesThumbIds = intArrayOf(R.drawable.zone_01,
                                      R.drawable.zone_02,
                                      R.drawable.zone_03,
                                      R.drawable.zone_04,
                                      R.drawable.zone_05,
                                      R.drawable.zone_06)

    override fun getCount(): Int {
        return zones.size
    }

    override fun getItem(position: Int): Any? {
        return null
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(i: Int, view: View?, container: ViewGroup?): View {
        var view = view

        if (view == null){
            view = activity.layoutInflater.inflate(R.layout.layout_item_zones, null)
        }

        var name = view?.findViewById<View>(R.id.text) as TextView
        var image = view?.findViewById<View>(R.id.image) as ImageView

        name.text = zones[i].name
        image.setImageResource(zonesThumbIds[zones[i].imageIndex-1])

        return view
    }
}