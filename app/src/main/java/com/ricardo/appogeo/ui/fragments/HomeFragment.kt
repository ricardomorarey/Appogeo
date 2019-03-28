package com.ricardo.appogeo.ui.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ricardo.appogeo.R
import com.ricardo.appogeo.db.HistorialEntity

class HomeFragment : Fragment() {

    private val ARG_COLUMN_COUNT = "column-count"
    private var mColumnCount = 2
    private var historialEntityList: List<HistorialEntity>? = null

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
            val context = view.getContext()
            val recyclerView = view as RecyclerView
            if (view.getId() == R.id.list_home) {
                recyclerView.layoutManager = LinearLayoutManager(context)
            } else {
                val displayMetrics = context.getResources().getDisplayMetrics()
                val dpWidth = displayMetrics.widthPixels / displayMetrics.density
                val numeroColumnas = (dpWidth / 180).toInt()
                recyclerView.layoutManager =
                    StaggeredGridLayoutManager(numeroColumnas, StaggeredGridLayoutManager.VERTICAL)
            }

        }
        return view
    }
}