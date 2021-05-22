package com.alhudaghifari.androidbasicsub.db

import com.alhudaghifari.androidbasicsub.db.entity.Car


class DatabaseHelperImpl(private val appDatabase: AppDatabase) : DatabaseHelper {

    override suspend fun getFavoriteCar(): List<Car> = appDatabase.carDao().getAll()

    override suspend fun getCarById(idCar: Int): List<Car> = appDatabase.carDao().getDataById(idCar)

    override suspend fun insertAll(car: List<Car>) = appDatabase.carDao().insertAll(car)

    override suspend fun deleteData(car: Car) = appDatabase.carDao().delete(car)

}