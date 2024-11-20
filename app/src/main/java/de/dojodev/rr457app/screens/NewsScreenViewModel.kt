package de.dojodev.rr457app.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.dojodev.rr457app.data.model.News
import de.dojodev.rr457app.data.repositories.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewsScreenViewModel @Inject constructor(
    private val newsRepository: NewsRepository
): ViewModel() {
    private val _news = MutableStateFlow(listOf<News>())
    val news: StateFlow<List<News>> get() = _news

    fun load() {
        viewModelScope.launch(Dispatchers.IO) {
            newsRepository.load().collect { data ->
                _news.value = data
            }
        }
    }
}