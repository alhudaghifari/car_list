package com.alhudaghifari.androidbasicsub.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Car(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "avg_horsepower") val avg_horsepower: String?,
    @ColumnInfo(name = "avg_price") val avg_price: String?,
    @ColumnInfo(name = "posterPath") val posterPath: String?,
    @ColumnInfo(name = "desc") val desc: String?,
)