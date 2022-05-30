package com.example.breakingbadapp.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.breakingbadapp.recyclerview.listitems.QuoteListItem
import com.example.domain.models.Quote
import com.example.domain.repo.SettingsRepository
import com.example.domain.usecases.GetAllQuotesUsecase
import com.example.domain.usecases.SaveQuotesToDbUsecase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class QuotesViewModel @Inject constructor(
    private val getAllQuotesFromDb: GetAllQuotesUsecase.FromDb,
    private val getAllQuotesFromApi: GetAllQuotesUsecase.FromApi,
    private val saveAllQuotes: SaveQuotesToDbUsecase,
    private val settingsRepository: SettingsRepository,
): ViewModel() {
    val list = MutableLiveData<List<QuoteListItem>>(emptyList())
    val loading = MutableLiveData(false)
    val error = MutableLiveData("")

    private var allItems = emptyList<QuoteListItem>()
    private var searchQuery = ""
    private var id = 0

    fun load() {
        viewModelScope.launch(exceptionHandler()) {
            loading.value = true
            error.value = ""
            val cache = settingsRepository.getOption(SettingsRepository.DB_SETTINGS_KEY)
            val api = settingsRepository.getOption(SettingsRepository.API_SETTINGS_KEY)
            if (!api && !cache) {
                error.value = "You disallowed both of caching and api requests. Please, change settings"
            } else {
                var fromApi = emptyList<Quote>()
                var fromDb = emptyList<Quote>()
                if (cache) {
                    fromDb = withContext(Dispatchers.IO) { getAllQuotesFromDb() }
                    allItems = fromDb.map { QuoteListItem.fromDomain(++id, it) {} }
                    filterList(searchQuery)
                }
                if (api) {
                    fromApi = withContext(Dispatchers.IO) { getAllQuotesFromApi() }
                    allItems = fromApi.map { QuoteListItem.fromDomain(++id, it) {} }
                    filterList(searchQuery)
                }
                if (api && cache) {
                    val toSave = fromApi.toMutableList()
                    withContext(Dispatchers.IO) {
                        toSave.removeAll(fromDb)
                        saveAllQuotes(toSave)
                    }
                }
            }
            loading.value = false
        }
    }

    fun search(query: String) {
        searchQuery = query
        filterList(query)
    }

    private fun filterList(query: String) {
        list.value = allItems.filter { q ->
            query.isEmpty() ||
                    query.lowercase() in q.author.lowercase() ||
                    query.lowercase() in q.text.lowercase()
        }
    }

    private fun exceptionHandler() = CoroutineExceptionHandler { _, t ->
        error.value = "Failed to connect, check your internet"
        loading.value = false
        t.printStackTrace()
    }
}