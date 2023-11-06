package com.heira.app.presentation.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.heira.app.core.Resource
import com.heira.app.data.remote.dto.RouteCreationDTO
import com.heira.app.domain.model.RouteModel
import com.heira.app.domain.use_case.getRouteById.GetRouteByIdUseCase
import com.heira.app.domain.use_case.getRouteByTag.GetRouteByTagUseCase
import com.heira.app.domain.use_case.getRouteOneToOne.GetRouteOneToOneUseCase
import com.heira.app.domain.use_case.postCreationRoute.PostCreationRouteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getRouteByIdUseCase: GetRouteByIdUseCase,
    private val getRouteByTagUseCase: GetRouteByTagUseCase,
    private val postCreationRouteUseCase: PostCreationRouteUseCase,
    private val getRouteOneToOneUseCase: GetRouteOneToOneUseCase,
): ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> = _isError

    private val _isCreationCorrect = MutableLiveData<Boolean>()
    val isCreationCorrect: LiveData<Boolean> = _isCreationCorrect

    private val _resultRouteTag = MutableLiveData<List<RouteModel>?>()
    val resultRouteTag: LiveData<List<RouteModel>?> = _resultRouteTag

    private val _resultRouteId = MutableLiveData<RouteModel?>()
    val resultRouteId: LiveData<RouteModel?> = _resultRouteId

    private val _resultCoordinates = MutableLiveData<String>()
    val resultCoordinates: LiveData<String> = _resultCoordinates

    private val _resultCoordinatesRouteOneToOne = MutableLiveData<List<List<Double>>>()
    val resultCoordinatesRouteOneToOne: LiveData<List<List<Double>>> = _resultCoordinatesRouteOneToOne


    fun coordinates(value: String) {
        viewModelScope.launch {
            _resultCoordinates.postValue(value)
        }
    }

    fun postCreationRoute(route: RouteCreationDTO) {
        _isLoading.postValue(true)
        viewModelScope.launch {
            delay(1500)
            when(val result = postCreationRouteUseCase(route = route)){
                is Resource.Success -> {
                    _isCreationCorrect.postValue(true)
                }
                is Resource.Error -> {
                    _isError.postValue(true)
                }
                else -> {
                    _isLoading.postValue(true)
                }
            }
            _isLoading.postValue(false)
        }
    }

    fun getRouteByTag(tag: String) {
        _isLoading.postValue(true)
        viewModelScope.launch {
            when(val result = getRouteByTagUseCase(tag = tag)){
                is Resource.Success -> {
                    _resultRouteTag.postValue(result.data)
                }
                is Resource.Error -> {
                    _isError.postValue(true)
                }
                else -> {
                    _isLoading.postValue(true)
                }
            }
            _isLoading.postValue(false)
        }
    }

    fun getRouteById(id: String) {
        _isLoading.postValue(true)
        viewModelScope.launch {
            delay(1500)
            when(val result = getRouteByIdUseCase(id = id)){
                is Resource.Success -> {
                    _resultRouteId.postValue(result.data)
                }
                is Resource.Error -> {
                    _isError.postValue(true)
                }
                else -> {
                    _isLoading.postValue(true)
                }
            }
            _isLoading.postValue(false)
        }
    }

    fun getCoordinateForRoteOneToOne(start: String, end: String) {
        _isLoading.postValue(true)
        viewModelScope.launch {
            when(val result = getRouteOneToOneUseCase(start = start, end = end)) {
                is Resource.Success -> {
                    _resultCoordinatesRouteOneToOne.postValue(result.data!!.coordinates)
                }
                is Resource.Error -> {
                    _isError.postValue(true)
                }

                else -> {
                    _isLoading.postValue(true)
                }
            }
            _isLoading.postValue(false)
        }
    }

}