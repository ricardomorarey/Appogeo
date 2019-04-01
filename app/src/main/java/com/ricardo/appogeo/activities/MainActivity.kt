package com.ricardo.appogeo.activities

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ricardo.appogeo.R
import com.ricardo.appogeo.ui.fragments.FragmentSearch
import com.ricardo.appogeo.ui.fragments.HistorialFragment
import com.ricardo.appogeo.ui.fragments.HomeFragment

class MainActivity : AppCompatActivity() {

    private var fragmentManager: FragmentManager? = null
    private var currentFragment: Fragment? = null

    private  var mio:FragmentManager?=null

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->

        currentFragment = when(item.itemId){
            R.id.navigation_home ->HomeFragment()
            R.id.navigation_busqueda ->FragmentSearch()
            R.id.navigation_historial ->HistorialFragment()
            else -> HomeFragment()
        }

        fragmentManager?.run {
            beginTransaction().replace(R.id.fragment_content, currentFragment!!).commit()
        }

        return@OnNavigationItemSelectedListener true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.fragmentManager = supportFragmentManager

        val navigation = findViewById<BottomNavigationView>(R.id.navigation)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        navigation.selectedItemId = R.id.navigation_home
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.search_menu, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.menu_search).actionView as SearchView
        searchView.setSearchableInfo(
            searchManager.getSearchableInfo(componentName))

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(s: String): Boolean {

                return false
            }

            override fun onQueryTextChange(s: String): Boolean {
                if (currentFragment is HomeFragment) {
                    (currentFragment as HomeFragment).filterResults(s)
                }
                return false
            }
        })
        return true
    }
}
