package com.mexiti.catphotoapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mexiti.catphotoapp.model.CatPhoto
import com.mexiti.catphotoapp.network.CatApi
import kotlinx.coroutines.launch
import java.io.IOException

sealed interface CatUiState{
    data class Success(val photos:List<CatPhoto>) : CatUiState // se tiene una lista de gatos
    object Error: CatUiState
    object Loading: CatUiState
}
class CatViewModel:ViewModel(){
    var catUiState:CatUiState  by mutableStateOf(CatUiState.Loading)
        private set

    init{
        getCatPhotos()
    }

   private  fun getCatPhotos(){
         viewModelScope.launch {
            catUiState = try {
                 val listResult = CatApi.retrofitService.getPhotos()
                CatUiState.Success(listResult) //llama  ala lista de gatos
             } catch (e: IOException){
                 CatUiState.Error
             }

         }
    }

}