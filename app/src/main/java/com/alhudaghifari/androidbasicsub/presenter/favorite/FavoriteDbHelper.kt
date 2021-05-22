package com.alhudaghifari.androidbasicsub.presenter.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alhudaghifari.androidbasicsub.db.DatabaseHelper
import com.alhudaghifari.androidbasicsub.db.entity.Car
import com.alhudaghifari.androidbasicsub.model.CarItem
import com.alhudaghifari.androidbasicsub.presenter.Resource
import kotlinx.coroutines.launch

class FavoriteDbHelper(private val dbHelper: DatabaseHelper) :
    ViewModel() {
    private val cars = MutableLiveData<Resource<List<Car>>>()
    private val singleCar = MutableLiveData<Resource<List<Car>>>()
    private val changeStatusFavorite = MutableLiveData<Resource<Boolean>>()

    fun fetchCars() {
        viewModelScope.launch {
            cars.postValue(Resource.loading(null))
            try {
                val carsFromDb = dbHelper.getFavoriteCar()
                if (carsFromDb.isNotEmpty()) {
                    cars.postValue(Resource.success(carsFromDb))
                } else {
                    cars.postValue(Resource.success(listOf()))
                }
            } catch (e: Exception) {
                cars.postValue(Resource.error("Something went wrong", null))
                e.printStackTrace()
            }
        }
    }

    fun fetchCarById(idCar: Int) {
        viewModelScope.launch {
            singleCar.postValue(Resource.loading(null))
            try {
                val carsFromDb = dbHelper.getCarById(idCar)
                if (carsFromDb.isNotEmpty()) {
                    singleCar.postValue(Resource.success(carsFromDb))
                } else {
                    singleCar.postValue(Resource.success(listOf()))
                }
            } catch (e: Exception) {
                singleCar.postValue(Resource.error("Something went wrong", null))
                e.printStackTrace()
            }
        }
    }

    fun addFavorite(itemCar: CarItem) {
        viewModelScope.launch {
            changeStatusFavorite.postValue(Resource.loading(null))
            try {
                val car = Car(
                    itemCar.id!!,
                    itemCar.name,
                    itemCar.avgHorsepower.toString(),
                    itemCar.avgPrice.toString(),
                    itemCar.imgUrl,
                    itemCar.desc
                )
                val carToInsertInDb = mutableListOf<Car>()
                carToInsertInDb.add(car)

                dbHelper.insertAll(carToInsertInDb)
                changeStatusFavorite.postValue(Resource.success(true))
            } catch (e: Exception) {
                changeStatusFavorite.postValue(Resource.error("Something went wrong", null))
                e.printStackTrace()
            }
        }
    }

    fun deleteFavorite(itemCar: CarItem) {
        viewModelScope.launch {
            changeStatusFavorite.postValue(Resource.loading(null))
            try {
                val car = Car(
                    itemCar.id!!,
                    itemCar.name,
                    itemCar.avgHorsepower.toString(),
                    itemCar.avgPrice.toString(),
                    itemCar.imgUrl,
                    itemCar.desc
                )

                dbHelper.deleteData(car)
                changeStatusFavorite.postValue(Resource.success(false))
            } catch (e: Exception) {
                changeStatusFavorite.postValue(Resource.error("Something went wrong", null))
                e.printStackTrace()
            }
        }
    }

    fun getCars(): LiveData<Resource<List<Car>>> {
        return cars
    }

    fun getSingleCar(): LiveData<Resource<List<Car>>> {
        return singleCar
    }

    fun getStatusFavorite() : LiveData<Resource<Boolean>> {
        return changeStatusFavorite
    }
}