package com.ricardo.appogeo.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.ricardo.appogeo.R
import com.ricardo.appogeo.db.HistorialBusqueda
import java.text.SimpleDateFormat
import java.util.Date
import butterknife.BindView
import butterknife.ButterKnife

class HistorialAdapter(ctx: Context, private val source: List<HistorialBusqueda>) : BaseAdapter() {
    private val inflater: LayoutInflater

    init { this.inflater = LayoutInflater.from(ctx) }
    override fun getCount(): Int { return source.size }
    override fun getItem(i: Int): HistorialBusqueda { return source[i] }
    override fun getItemId(i: Int): Long { return source[i]._id.toLong() }
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

        @BindView(R.id.textView_date)
        internal var tv_date: TextView? = null
        @BindView(R.id.textView_lat)
        internal var tv_lat: TextView? = null
        @BindView(R.id.textView_lon)
        internal var tv_lon: TextView? = null
        @BindView(R.id.image_type)
        internal var image_type: ImageView? = null
        init { ButterKnife.bind(this, view) }

        fun setData(item: HistorialBusqueda?) {
            if (item != null) {
                tv_date!!.text = item.title
                tv_lat!!.text = getDatePreview(item.date)
                tv_lon!!.text = item.streetaddress

                if (item.title!!.toLowerCase().contains("consulado")) {
                    image_type!!.setImageResource(R.drawable.ic_consulado)
                } else {
                    image_type!!.setImageResource(R.drawable.ic_embassy)
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
