package com.alhudaghifari.androidbasicsub.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.alhudaghifari.androidbasicsub.db.entity.Car


@Dao
interface CarDao {

    @Query("SELECT * FROM car")
    suspend fun getAll(): List<Car>

    @Query("SELECT * FROM car where id = :idCar")
    suspend fun getDataById(idCar: Int): List<Car>

    @Insert
    suspend fun insertAll(cars: List<Car>)

    @Delete
    suspend fun delete(car: Car)

}