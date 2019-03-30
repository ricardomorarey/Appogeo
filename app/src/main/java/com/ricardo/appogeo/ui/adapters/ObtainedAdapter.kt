package com.ricardo.appogeo.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import com.ricardo.appogeo.R
import com.ricardo.appogeo.db.Obtenido
import java.util.ArrayList
import butterknife.BindView
import butterknife.ButterKnife

class ObtainedAdapter(ctx: Context, private val source: List<Obtenido>) : BaseAdapter(), Filterable {
    private val inflater: LayoutInflater
    private var filter: Filter? = null
    private var mDisplayedItems: List<Obtenido> = ArrayList()

    init {
        this.mDisplayedItems = source
        this.inflater = LayoutInflater.from(ctx)
    }

    override fun getCount(): Int { return mDisplayedItems.size }

    override fun getFilter(): Filter {
        if (filter != null) {
            return filter as Filter
        }

        filter = object : Filter() {
            override fun performFiltering(charSequence: CharSequence): Filter.FilterResults {
                val results = Filter.FilterResults()
                val arrayResults = ArrayList<Obtenido>()

                synchronized(this@ObtainedAdapter) {
                    val values = charSequence.toString().toLowerCase()
                    if (values.length == 0) {
                        results.count = source.size
                        results.values = source
                    } else {
                        for (doc in source) {
                            if (doc.title!!.toLowerCase().contains(values)) {
                                arrayResults.add(doc)
                            }
                        }
                        results.count = arrayResults.size
                        results.values = arrayResults
                    }
                }
                return results
            }

            override fun publishResults(charSequence: CharSequence, filterResults: Filter.FilterResults) {
                try {
                    mDisplayedItems = filterResults.values as ArrayList<Obtenido>
                    notifyDataSetChanged()
                } catch (ignore: Exception) { }
            }
        }
        return filter as Filter
    }

    override fun getItem(position: Int): Obtenido? {
        return if (mDisplayedItems.size > position) {
            this.mDisplayedItems[position]
        } else null
    }

    override fun getItemId(i: Int): Long {
        return if (this.mDisplayedItems.size > 0) {
            mDisplayedItems[i].hashCode().toLong()
        } else -1
    }

    override fun getView(i: Int, view: View?, viewGroup: ViewGroup): View {
        var view = view
        val holder: ViewHolder
        if (view == null) {
            view = this.inflater.inflate(R.layout.item_results, viewGroup, false)
            holder = ViewHolder(view)
            view!!.tag = holder
        } else { holder = view.tag as ViewHolder }
        if (this.mDisplayedItems.size > i) { holder.setData(this.mDisplayedItems[i]) }

        return view
    }

    class ViewHolder internal constructor(view: View) {

        @BindView(R.id.image_type)
        internal var image_type: ImageView? = null
        @BindView(R.id.textView_location)
        internal var tv_location: TextView? = null
        @BindView(R.id.textView_street)
        internal var tv_street: TextView? = null
        @BindView(R.id.textView_title)
        internal var tv_title: TextView? = null
        init { ButterKnife.bind(this, view) }

        fun setData(item: Obtenido?) {
            if (item != null) {
                tv_location!!.text = item.address.locality
                tv_street!!.text = item.address.streetaddress
                tv_title!!.text = item.title

                if (item.title!!.toLowerCase().contains("consulado")) {
                    image_type!!.setImageResource(R.drawable.ic_consulado)
                } else {
                    image_type!!.setImageResource(R.drawable.ic_embassy)
                }
            }
        }
    }
}
