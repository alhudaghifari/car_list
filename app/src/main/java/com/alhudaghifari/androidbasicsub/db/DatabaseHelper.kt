package com.alhudaghifari.androidbasicsub.db

import com.alhudaghifari.androidbasicsub.db.entity.Car


interface DatabaseHelper {

    suspend fun getFavoriteCar(): List<Car>

    suspend fun getCarById(idCar: Int): List<Car>

    suspend fun insertAll(car: List<Car>)

    suspend fun deleteData(car: Car)

}