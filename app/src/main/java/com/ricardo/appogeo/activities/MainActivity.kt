package com.ricardo.appogeo.activities

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.ricardo.appogeo.R
import com.ricardo.appogeo.api.DatosMadridSevice
import com.ricardo.appogeo.ui.homeFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Job

class MainActivity : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->

        var f: Fragment? = null
        var g: Fragment? = null
        var h: Fragment? = null

        when (item.itemId) {
            R.id.navigation_home -> {
                h = homeFragment()
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.contenedor, h)
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_search -> {
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_searched -> {
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        supportFragmentManager
            .beginTransaction()
            .add(R.id.contenedor, homeFragment())
            .commit()
    }
}
