package com.ricardo.appogeo.activities

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.google.gson.Gson
import com.ricardo.appogeo.R
import com.ricardo.appogeo.api.DatosMadridSevice
import com.ricardo.appogeo.db.ConsuladosEntity
import com.ricardo.appogeo.ui.adapters.ConsuladosReciclerViewAdapter
import com.ricardo.appogeo.ui.fragments.BuscadosFragment
import com.ricardo.appogeo.ui.fragments.ConsuladosInteractionListener
import com.ricardo.appogeo.ui.fragments.HomeFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_home_list.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    lateinit var service: DatosMadridSevice
    lateinit var jobMain: Job

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->

        var f: Fragment? = null
        var g: Fragment? = null
        var h: Fragment? = null

        when (item.itemId) {
            R.id.navigation_home -> {
                h = HomeFragment()
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
                f = BuscadosFragment()
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.contenedor, f)
                    .commit()
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
            .add(R.id.contenedor, HomeFragment())
            .commit()

        // Libreria retrofit
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://datos.madrid.es/egob/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create<DatosMadridSevice>(DatosMadridSevice::class.java)

        //Compruebo que hay conexión si no la hay le muestro un toast al usuario
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        if (!(networkInfo != null && networkInfo.isConnected)) Toast.makeText(
            this,
            "There was a connection problem with the server, try it later", Toast.LENGTH_LONG
        ).show()

        jobMain = GlobalScope.launch {
            repeat(1) { i ->
                getConsuladosList()
                delay(10L)
            }
        }

        // en el onsetonclicklistener cancelo la corutine para que no sigua haciendo peticiones, cojo la posiico y voy a la vista de detalle
        /*list_home.setOnItemClickListener { adapterView, view, position, l ->
            val company = list_home.getItemAtPosition(position) as ConsuladosEntity
            val requestCompanyId = company.id
            jobMain.cancel()
            val intent = Intent(this, DetailsCompanyActivity::class.java)
            intent.putExtra("requestCompanyId", requestCompanyId)
            startActivity(intent)
            finish()
        }*/
    }

    //Creo la funcion para pedir la lista de consulados y en ella cargao el daptador personalizado de mi lista
    fun getConsuladosList() {
        service.searchEmbajadasConsulados().enqueue(object : Callback<List<ConsuladosEntity>> {
            @RequiresApi(Build.VERSION_CODES.N)
            override fun onResponse(call: Call<List<ConsuladosEntity>>?, response: Response<List<ConsuladosEntity>>?) {
                val posts = response?.body()!!
                Log.i("TAG_LOGS", Gson().toJson(posts))
                lateinit var adapter: ConsuladosReciclerViewAdapter
                lateinit var consuladosList: List<ConsuladosEntity>
                lateinit var cons: ConsuladosInteractionListener
                consuladosList = posts
                adapter = ConsuladosReciclerViewAdapter(consuladosList,cons )
                list_home.adapter = adapter
            }

            override fun onFailure(call: Call<List<ConsuladosEntity>>?, t: Throwable?) {
                t?.printStackTrace()
            }
        })
    }
}