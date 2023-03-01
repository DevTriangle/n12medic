package com.triangle.n12medic.viewmodel

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.triangle.n12medic.common.ApiService
import com.triangle.n12medic.model.Analysis
import com.triangle.n12medic.model.News
import kotlinx.coroutines.launch

@SuppressLint("MutableCollectionMutableState")
class HomeViewModel:ViewModel() {
    // ViewModel для получения информации о новостях и анализах с сервера
    // Дата создания: 01.03.2023 09:47
    // Автор: Triangle

    val newsErrorMessage = MutableLiveData<String>()
    val analyzesErrorMessage = MutableLiveData<String>()

    val analyzesCategories = arrayListOf(
        "Популярные",
        "Covid",
        "Комплексные",
        "Чекапы",
        "Биохимия",
        "Гормоны",
        "Иммунитет",
        "Витамины",
        "Аллергены",
        "Анализ крови",
        "Анализ мочи",
        "Анализ кала",
        "Только в клинике"
    )

    private val _news: MutableList<News> = mutableStateListOf()
    val news: List<News> by mutableStateOf(_news)

    private val _analyzes: MutableList<Analysis> = mutableStateListOf()
    val analyzes: List<Analysis> by mutableStateOf(_analyzes)

    fun loadNews() { // Метод для получения новостей и акций с сервера
        _news.clear()
        newsErrorMessage.value = null
        val apiService = ApiService.getInstance()

        viewModelScope.launch {
            try {
                val json = apiService.loadNews()
                _news.addAll(json)
            } catch (e: java.lang.Exception) {
                Log.d(TAG, "loadNews: $e")
                newsErrorMessage.value = e.message
            }
        }
    }

    fun loadCatalog() { // Метод для получения каталога
        _analyzes.clear()
        analyzesErrorMessage.value = null

        val apiService = ApiService.getInstance()

        viewModelScope.launch {
            try {
                val json = apiService.loadCatalog()
                _analyzes.addAll(json)
            } catch (e: java.lang.Exception) {
                Log.d(TAG, "loadCatalog: $e")
                analyzesErrorMessage.value = e.message
            }
        }
    }
}