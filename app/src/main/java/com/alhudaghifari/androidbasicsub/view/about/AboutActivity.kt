package com.alhudaghifari.androidbasicsub.view.about

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alhudaghifari.androidbasicsub.R
import com.alhudaghifari.androidbasicsub.databinding.ActivityAboutBinding

class AboutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setSupportActionBar(binding.toolbarMain)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = resources.getString(R.string.about)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}