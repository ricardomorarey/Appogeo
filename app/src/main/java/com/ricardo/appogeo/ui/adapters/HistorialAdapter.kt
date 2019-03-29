package com.ricardo.appogeo.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.ricardo.appogeo.db.HistorialBusqueda
import java.text.SimpleDateFormat
import java.util.*

class HistorialAdapter(ctx: Context, private val source: List<HistorialBusqueda>) : BaseAdapter() {
    private val inflater: LayoutInflater

    init { this.inflater = LayoutInflater.from(ctx) }

    override fun getCount(): Int { return source.size }

    override fun getItem(i: Int): HistorialBusqueda {
        return source[i]
    }

    override fun getItemId(i: Int): Long {
        return source[i]._id.toLong()
    }

    override fun getView(i: Int, view: View?, viewGroup: ViewGroup): View {
        var view = view
        val holder: ViewHolder
        if (view == null) {
            view = this.inflater.inflate(com.ricardo.appogeo.R.layout.item_busqueda, viewGroup, false)
            holder = ViewHolder(view)
            view!!.setTag(holder)
        } else {
            holder = view!!.getTag() as ViewHolder
        }
        holder.setData(this.source[i])
        return view
    }

    class ViewHolder internal constructor(view: View) {
        internal var tv_date: TextView? = null
        internal var tv_lat: TextView? = null
        internal var tv_lon: TextView? = null
        internal var image_type: ImageView? = null

        fun setData(item: HistorialBusqueda?) {
            if (item != null) {
                tv_date!!.setText(item!!.title)
                tv_lat!!.text = getDatePreview(item!!.date)
                tv_lon!!.setText(item!!.streetaddress)

                if (item!!.title!!.toLowerCase().contains("consulado")) {
                    image_type!!.setImageResource(com.ricardo.appogeo.R.drawable.ic_consulado)
                } else {
                    image_type!!.setImageResource(com.ricardo.appogeo.R.drawable.ic_embassy)
                }
            }
        }

        private fun getDatePreview(date: Date?): String {
            if (date == null) {
                return ""
            }
            val sdf = SimpleDateFormat("dd-MMM-yyyy HH:mm.ss")
            val dateString = sdf.format(date)
            return dateString.toUpperCase()
        }
    }
}