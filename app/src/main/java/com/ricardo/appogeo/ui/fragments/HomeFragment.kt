package com.ricardo.appogeo.ui.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ricardo.appogeo.R
import com.ricardo.appogeo.db.ConsuladosEntity
import com.ricardo.appogeo.ui.adapters.ConsuladosReciclerViewAdapter

class HomeFragment : Fragment() {

    private val ARG_COLUMN_COUNT = "column-count"
    private var mColumnCount = 2
    private var columnCount = 1
    private var listener: ConsuladosInteractionListener? = null
    private  var consuladosList: List<ConsuladosEntity>? = null

    fun newInstance(columnCount: Int): HomeFragment {
        val fragment = HomeFragment()
        val args = Bundle()
        args.putInt(ARG_COLUMN_COUNT, columnCount)
        fragment.setArguments(args)
        return fragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (arguments != null) {
            mColumnCount = arguments!!.getInt(ARG_COLUMN_COUNT)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }

                adapter = consuladosList?.let { ConsuladosReciclerViewAdapter(it,listener) }
            }
        }
        return view
    }
}
