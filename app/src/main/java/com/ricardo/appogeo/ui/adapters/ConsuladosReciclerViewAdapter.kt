package com.ricardo.appogeo.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.ricardo.appogeo.R
import com.ricardo.appogeo.db.ConsuladosEntity
import com.ricardo.appogeo.ui.fragments.ConsuladosInteractionListener
import kotlinx.android.synthetic.main.fragment_home.view.*


class ConsuladosReciclerViewAdapter (
    private val mValues: List<ConsuladosEntity>,
    private val mListener: ConsuladosInteractionListener?
) : RecyclerView.Adapter<ConsuladosReciclerViewAdapter.ViewHolder>(){

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as ConsuladosEntity
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_home, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
        holder.mIdnombre.text = item.title
        holder.mCiudad.text = item.locality
        holder.mDireccion.text = item.street_address
        holder.imageView.setImageResource(R.mipmap.ic_launcher_consulado)

        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)

        }
    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mIdnombre: TextView = mView.item_nombre
        val mCiudad: TextView = mView.item_ciudad
        val mDireccion: TextView = mView.item_direccion
        val imageView: ImageView = mView.item_image

        override fun toString(): String {
            return super.toString() + " '" + mCiudad.text + "'"
        }
    }
}