package com.alhudaghifari.androidbasicsub.presenter.car_list

import android.app.Activity
import com.alhudaghifari.androidbasicsub.loadJSONFromAsset
import com.alhudaghifari.androidbasicsub.model.CarListModel

import com.google.gson.Gson

class CarListPresenter(val activity: Activity, private val view: CarListInterface) {

    fun getCar() {
        view.showLoading()
        val gson = Gson()
        val model = gson.fromJson(loadJSONFromAsset(activity, "carlist.json"), CarListModel::class.java)
        view.callFinished(model)
        view.hideLoading()
    }
}