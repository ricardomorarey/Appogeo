package com.ricardo.appogeo.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.ricardo.appogeo.R
import com.ricardo.appogeo.db.RoomEntitie
import kotlinx.android.synthetic.main.item_busqueda.view.*
import java.text.SimpleDateFormat

class HistorialAdapter(ctx: Context, private val source: List<RoomEntitie>) : BaseAdapter() {

    private val inflater: LayoutInflater
    init { this.inflater = LayoutInflater.from(ctx) }
    override fun getCount(): Int { return source.size }
    override fun getItem(i: Int): RoomEntitie { return source[i] }
    override fun getItemId(i: Int): Long { return source[i].id.toLong() }
    override fun getView(i: Int, view: View?, viewGroup: ViewGroup): View {
        var view = view
        val holder: ViewHolder
        if (view == null) {
            view = this.inflater.inflate(R.layout.item_busqueda, viewGroup, false)
            holder = ViewHolder(view)
            view!!.tag = holder
        } else {
            holder = view.tag as ViewHolder
        }
        holder.setData(this.source[i])
        return view
    }

    class ViewHolder internal constructor(view: View) {

        val tv_date: TextView  = view.textView_date
        val tv_lat: TextView = view.textView_lat
        val tv_lon: TextView = view.textView_lon
        val image_type: ImageView = view.image_type

        fun setData(item: RoomEntitie) {
            if (item != null) {
                tv_date!!.text = item.Title
                tv_lat!!.text = getDatePreview(item.DATE)
                tv_lon!!.text = item.Streetaddress

                if (item.Title!!.toLowerCase().contains("consulado")) {
                    image_type!!.setImageResource(R.drawable.ic_consulado)
                } else {
                    image_type!!.setImageResource(R.drawable.ic_embassy)
                }
            }
        }

        private fun getDatePreview(date: String): String {
            if (date == null) {
                return ""
            }
            val sdf = SimpleDateFormat("dd-MMM-yyyy HH:mm.ss")
            val dateString = sdf.format(date)
            return dateString.toUpperCase()
        }
    }
}
