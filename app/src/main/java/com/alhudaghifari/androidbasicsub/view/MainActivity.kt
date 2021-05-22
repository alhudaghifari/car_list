package com.alhudaghifari.androidbasicsub.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.alhudaghifari.androidbasicsub.view.detail.DetailActivity
import com.alhudaghifari.androidbasicsub.R
import com.alhudaghifari.androidbasicsub.databinding.ActivityMainBinding
import com.alhudaghifari.androidbasicsub.model.CarItem
import com.alhudaghifari.androidbasicsub.model.CarListModel
import com.alhudaghifari.androidbasicsub.presenter.car_list.CarListInterface
import com.alhudaghifari.androidbasicsub.presenter.car_list.CarListPresenter
import com.alhudaghifari.androidbasicsub.view.about.AboutActivity
import com.alhudaghifari.androidbasicsub.view.favorite.FavoriteActivity

class MainActivity : AppCompatActivity(), CarListInterface {

    private lateinit var binding: ActivityMainBinding
    private lateinit var presenter: CarListPresenter
    private lateinit var adapter: HomeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setSupportActionBar(findViewById(R.id.toolbarMain))

        presenter = CarListPresenter(this, this)

        setAdapter()
        setSwipeListener()
    }

    override fun onResume() {
        super.onResume()
        presenter.getCar()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.home_nav, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_favorite -> {
            val intent = Intent(this@MainActivity, FavoriteActivity::class.java)
            startActivity(intent)
            true
        }
        R.id.action_about -> {
            val intent = Intent(this@MainActivity, AboutActivity::class.java)
            startActivity(intent)
            true
        }
        else -> {

            super.onOptionsItemSelected(item)
        }
    }
    
    override fun callFinished(model: CarListModel) {
        Log.d("mainnn", model.toString())
        if (model.results != null) {
            Log.d("mainnn", "result not null")
            if (model.results.size > 0) {
                Log.d("mainnn", "size > 0")
                showData()
                adapter.setNewData(model.results)
            } else {
                Log.d("mainnn", "size < 0")
                noData()
            }
        } else {
            Log.d("mainnn", "no data")
            noData()
        }
    }

    override fun showLoading() {
        binding.swipeRefreshLayout.isRefreshing = true
    }

    override fun hideLoading() {
        binding.swipeRefreshLayout.isRefreshing = false
    }

    private fun setSwipeListener() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            presenter.getCar()
        }
    }

    private fun setAdapter() {
        binding.rvMain.layoutManager = LinearLayoutManager(applicationContext)
        adapter = HomeAdapter(applicationContext, mutableListOf())
        adapter.setOnCarClickListener(object: HomeAdapter.OnCarClickListener {
            override fun onClick(position: Int, carItem: CarItem) {
                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                intent.putExtra("itemCar", carItem)
                startActivity(intent)
            }
        })
        binding.rvMain.adapter = adapter
    }


    private fun showData() {
        binding.tvInfo.visibility = View.GONE
        binding.rvMain.visibility = View.VISIBLE
    }

    private fun noData() {
        binding.tvInfo.visibility = View.VISIBLE
        binding.rvMain.visibility = View.GONE
    }

}