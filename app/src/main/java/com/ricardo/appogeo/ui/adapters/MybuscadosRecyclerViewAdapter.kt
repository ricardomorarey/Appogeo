package com.ricardo.appogeo.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.ricardo.appogeo.R
import com.ricardo.appogeo.db.HistorialEntity
import com.ricardo.appogeo.ui.fragments.BusacadosInteractionListener
import kotlinx.android.synthetic.main.fragment_buscados.view.*

class MybuscadosRecyclerViewAdapter(
    private val mValues: List<HistorialEntity>,
    private val mListener: BusacadosInteractionListener?
) : RecyclerView.Adapter<MybuscadosRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as HistorialEntity
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_buscados, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
        holder.mIdnombre.text = item.name
        holder.mFecha.text = item.fecha
        holder.imageView.setImageResource(R.mipmap.ic_launcher_consulado)

        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)

        }
    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mIdnombre: TextView = mView.item_nombre_buscados
        val mFecha: TextView = mView.item_fecha_buscados
        val imageView: ImageView = mView.imageView_buscados

        override fun toString(): String {
            return super.toString() + " '" + mFecha.text + "'"
        }
    }
}
