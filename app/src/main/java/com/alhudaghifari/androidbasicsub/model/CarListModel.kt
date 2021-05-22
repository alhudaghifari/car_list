package com.alhudaghifari.androidbasicsub.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CarListModel(

	@field:SerializedName("results")
	val results: MutableList<CarItem>? = null
) : Parcelable

@Parcelize
data class CarItem(

	@field:SerializedName("num_models")
	val numModels: Int? = null,

	@field:SerializedName("img_url")
	val imgUrl: String? = null,

	@field:SerializedName("desc")
	val desc: String? = null,

	@field:SerializedName("avg_horsepower")
	val avgHorsepower: Double? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("avg_price")
	val avgPrice: Double? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("max_car_id")
	val maxCarId: Int? = null
) : Parcelable
