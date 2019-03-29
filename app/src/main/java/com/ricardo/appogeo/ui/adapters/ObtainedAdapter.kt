package com.ricardo.appogeo.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.ricardo.appogeo.db.Obtenido
import kotlinx.android.synthetic.main.item_results.view.*


class ObtainedAdapter(ctx: Context, private val source: List<com.ricardo.appogeo.db.Obtenido>) : BaseAdapter(), Filterable {
    private val inflater: LayoutInflater
    private var filter: Filter? = null
    private var mDisplayedItems: List<com.ricardo.appogeo.db.Obtenido> = ArrayList()

    init {
        this.mDisplayedItems = source
        this.inflater = LayoutInflater.from(ctx)
    }

    override fun getCount(): Int {
        return mDisplayedItems.size
    }

    override fun getFilter(): Filter {
        if (filter != null) {
            return filter as Filter
        }

        filter = object : Filter() {
            protected override fun performFiltering(charSequence: CharSequence): FilterResults {
                val results = FilterResults()
                val arrayResults = ArrayList<com.ricardo.appogeo.db.Obtenido>()

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

            protected override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
                try {
                    mDisplayedItems = filterResults.values as List<com.ricardo.appogeo.db.Obtenido>
                    notifyDataSetChanged()
                } catch (ignore: Exception) {
                }

            }
        }
        return this.filter as Filter
    }

    override fun getItem(position: Int): com.ricardo.appogeo.db.Obtenido? {
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
            view = this.inflater.inflate(com.ricardo.appogeo.R.layout.item_results, viewGroup, false)
            holder = ViewHolder(view)
            view!!.setTag(holder)
        } else {
            holder = view!!.getTag() as ViewHolder
        }

        if (this.mDisplayedItems.size > i) {
            holder.setData(this.mDisplayedItems[i])
        }
        return view
    }

    class ViewHolder internal constructor(view: View) {

        internal var image_type: ImageView = view.image_type
        internal var tv_location: TextView = view.textView_location
        internal var tv_street: TextView = view.textView_street
        internal var tv_title: TextView = view.textView_title


        fun setData(item: Obtenido?) {
            if (item != null) {
                tv_location.text = "${item.location}"
                tv_street.text = "${item.address}"
                tv_title.text = "${item.title}"

                if (item!!.title!!.toLowerCase().contains("consulado")) {
                    image_type!!.setImageResource(com.ricardo.appogeo.R.drawable.ic_consulado)
                } else {
                    image_type!!.setImageResource(com.ricardo.appogeo.R.drawable.ic_embassy)
                }
            }
        }
    }
}