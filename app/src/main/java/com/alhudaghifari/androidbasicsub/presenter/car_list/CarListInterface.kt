package com.alhudaghifari.androidbasicsub.presenter.car_list

import com.alhudaghifari.androidbasicsub.model.CarListModel
import com.alhudaghifari.androidbasicsub.presenter.ConnStatus

interface CarListInterface: ConnStatus {
    fun callFinished(model: CarListModel)
}