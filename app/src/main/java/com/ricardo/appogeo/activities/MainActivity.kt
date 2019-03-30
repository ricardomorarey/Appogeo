package com.ricardo.appogeo.activities

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SearchView
import android.view.Menu
import com.ricardo.appogeo.R
import com.ricardo.appogeo.ui.fragments.FragmentSearch
import com.ricardo.appogeo.ui.fragments.HistorialFragment
import com.ricardo.appogeo.ui.fragments.HomeFragment

class MainActivity : AppCompatActivity() {

    private var fragmentManager: FragmentManager? = null
    private var currentFragment: Fragment? = null

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        val fragmentTransaction: FragmentTransaction

        when (item.itemId) {
            R.id.navigation_home -> {
                currentFragment = HomeFragment()
                fragmentTransaction = fragmentManager!!.beginTransaction()
                fragmentTransaction.replace(R.id.fragment_content, currentFragment!!).commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_busqueda -> {
                currentFragment = FragmentSearch()
                fragmentTransaction = fragmentManager!!.beginTransaction()
                fragmentTransaction.replace(R.id.fragment_content, currentFragment!!).commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_historial -> {
                currentFragment = HistorialFragment()
                fragmentTransaction = fragmentManager!!.beginTransaction()
                fragmentTransaction.replace(R.id.fragment_content, currentFragment!!).commit()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
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
