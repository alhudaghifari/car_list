package com.alhudaghifari.androidbasicsub.view.favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.alhudaghifari.androidbasicsub.R
import com.alhudaghifari.androidbasicsub.databinding.ActivityFavoriteBinding
import com.alhudaghifari.androidbasicsub.db.DatabaseBuilder
import com.alhudaghifari.androidbasicsub.db.DatabaseHelperImpl
import com.alhudaghifari.androidbasicsub.model.CarItem
import com.alhudaghifari.androidbasicsub.presenter.Status
import com.alhudaghifari.androidbasicsub.presenter.favorite.FavoriteDbHelper
import com.alhudaghifari.androidbasicsub.view.detail.DetailActivity

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var viewModel: FavoriteDbHelper
    private lateinit var adapter: FavoriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setSupportActionBar(binding.toolbarMain)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = resources.getString(R.string.favorite)

        setAdapter()
        setViewModel()
    }

    override fun onResume() {
        super.onResume()
        getDataFavorite()
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setAdapter() {
        binding.rvFavorite.layoutManager = LinearLayoutManager(applicationContext)
        adapter = FavoriteAdapter(applicationContext, mutableListOf())
        adapter.setOnCarClickListener(object: FavoriteAdapter.OnCarClickListener {
            override fun onClick(position: Int, itemCar: CarItem) {
                val intent = Intent(this@FavoriteActivity, DetailActivity::class.java)
                intent.putExtra("itemCar", itemCar)
                startActivity(intent)
            }
        })
        binding.rvFavorite.adapter = adapter
    }

    private fun setViewModel() {
        viewModel = FavoriteDbHelper( DatabaseHelperImpl(DatabaseBuilder.getInstance(applicationContext)))
        viewModel.getCars().observe(this, {
            when(it.status) {
                Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    it.data?.let { favCars ->
                        val listCars: MutableList<CarItem> = mutableListOf()
                        if (favCars.size == 0) {
                            binding.rvFavorite.visibility = View.GONE
                            binding.tvNoData.visibility = View.VISIBLE
                        } else {
                            binding.rvFavorite.visibility = View.VISIBLE
                            binding.tvNoData.visibility = View.GONE
                        }
                        for (car in favCars) {
                            Log.d("FavoriteAct", "get cars kuu $car")
                            val item = CarItem(
                                id = car.id,
                                name = car.name,
                                imgUrl = car.posterPath,
                                avgHorsepower = car.avg_horsepower?.toDouble(),
                                avgPrice = car.avg_price?.toDouble(),
                                desc = car.desc,
                            )
                            listCars.add(item)
                        }
                        adapter.setNewData(listCars)
                    }
                }
                Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.rvFavorite.visibility = View.GONE
                }
                Status.ERROR -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun getDataFavorite() {
        viewModel.fetchCars()
    }
}