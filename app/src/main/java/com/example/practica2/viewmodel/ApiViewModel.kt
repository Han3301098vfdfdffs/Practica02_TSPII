package com.example.practica2.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practica2.network.Api
import kotlinx.coroutines.launch
import java.io.IOException

sealed interface ApiUiState{
    data class Success(val photos:String) : ApiUiState
    object Error: ApiUiState
    object Loading: ApiUiState
}
class ApiViewModel:ViewModel() {
    var apiUiState: ApiUiState by mutableStateOf(ApiUiState.Loading)
    private set

    private val apiKey = "AIzaSyA9WJ3eBBq5Uo94ZT_Y66DtrIQGAMAgiPg"
    private val cx = "068de69f3c5a743b0"

    init{
        searchImages("sharks")
    }

    fun searchImages(query: String) {
        viewModelScope.launch {
            try {
                val result = Api.retrofitService.searchImages(
                    apiKey = apiKey,
                    cx = cx,
                    query = query
                )
                apiUiState = ApiUiState.Success(result)
            } catch (e: IOException) {
                apiUiState = ApiUiState.Error
            }
        }
    }

//    fun getApiPhotos(){
//        viewModelScope.launch{
//            val listResult = Api.retrofitService.getPhotos()
//            apiUiState = listResult
//        }
//    }
}

