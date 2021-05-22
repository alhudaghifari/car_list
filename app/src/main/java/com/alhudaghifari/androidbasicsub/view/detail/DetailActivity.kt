package com.alhudaghifari.androidbasicsub.view.detail

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.alhudaghifari.androidbasicsub.ImageUtils
import com.alhudaghifari.androidbasicsub.R
import com.alhudaghifari.androidbasicsub.databinding.ActivityDetailBinding
import com.alhudaghifari.androidbasicsub.db.DatabaseBuilder
import com.alhudaghifari.androidbasicsub.db.DatabaseHelperImpl
import com.alhudaghifari.androidbasicsub.model.CarItem
import com.alhudaghifari.androidbasicsub.presenter.Status
import com.alhudaghifari.androidbasicsub.presenter.favorite.FavoriteDbHelper

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var itemCar: CarItem
    private lateinit var viewModel: FavoriteDbHelper
    private var statusFavorite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setSupportActionBar(binding.toolbarMain)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = resources.getString(R.string.detail_car)

        if (intent.extras != null) {
            val bundle = intent.extras
            itemCar = bundle!!.getParcelable("itemCar")!!
            supportActionBar?.title = itemCar.name
            setViewModel()
            setData()
            setButtonFavoriteListener()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.detail_nav, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_share -> {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, getShareContent())
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
            true
        }
        else -> {

            super.onOptionsItemSelected(item)
        }
    }

    private fun getShareContent(): String {
        val avgHorsePower = "Horse Power : ${ itemCar.avgHorsepower.toString() ?: "" }"
        val avgPrice = "Price $${itemCar.avgPrice}"

        return "${itemCar.name}\n$avgPrice\n$avgHorsePower"
    }

    private fun setData() {
        ImageUtils().loadImage(this, binding.ivImage, itemCar.imgUrl)

        val price = "Price : $${itemCar.avgPrice.toString()}"
        val horsePower = "Horse Power : ${itemCar.avgHorsepower.toString() ?: "-"}"

        binding.tvTitle.text = itemCar.name ?: "-"
        binding.tvPrice.text = price
        binding.tvHorsePower.text = horsePower
        binding.tvDesc.text = itemCar.desc ?: "Desc : -"
        viewModel.fetchCarById(itemCar.id!!)
    }


    private fun setViewModel() {
        viewModel =
            FavoriteDbHelper(DatabaseHelperImpl(DatabaseBuilder.getInstance(applicationContext)))
        viewModel.getSingleCar().observe(this, {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let { carList ->
                        if (carList.size > 0) {
                            if (carList[0].id == itemCar.id) {
                                binding.ivFavorite.setImageResource(R.drawable.ic_baseline_favorite_24_full_red)
                                statusFavorite = true
                            }
                        }
                    }
                }
                Status.LOADING -> {
                    Log.d("DetailAct", "getSingleCar loading")
                }
                Status.ERROR -> {
                    Log.e("DetailAct", "getSingleCar error : ${it.message}")
                }
            }
        })
        viewModel.getStatusFavorite().observe(this, {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let { status ->
                        if (status) {
                            binding.ivFavorite.setImageResource(R.drawable.ic_baseline_favorite_24_full_red)
                            statusFavorite = true
                        } else {
                            binding.ivFavorite.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                            statusFavorite = false
                        }
                    }
                }
                Status.LOADING -> {
                    Log.d("DetailCarAct", "getStatusFavorite loading")
                }
                Status.ERROR -> {
                    Log.e("DetailCarAct", "getStatusFavorite error : ${it.message}")
                }
            }
        })
    }

    private fun setButtonFavoriteListener() {
        binding.ivFavorite.setOnClickListener {
            if (statusFavorite) {
                viewModel.deleteFavorite(itemCar)
            } else {
                viewModel.addFavorite(itemCar)
            }
        }
    }
}