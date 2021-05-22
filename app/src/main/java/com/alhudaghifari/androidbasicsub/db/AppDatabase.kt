package com.alhudaghifari.androidbasicsub.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.alhudaghifari.androidbasicsub.db.dao.CarDao
import com.alhudaghifari.androidbasicsub.db.entity.Car


@Database(entities = [Car::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun carDao(): CarDao

}