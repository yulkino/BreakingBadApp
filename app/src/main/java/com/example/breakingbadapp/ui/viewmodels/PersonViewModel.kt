package com.example.breakingbadapp.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.breakingbadapp.recyclerview.listitems.PersonListItem
import com.example.domain.models.Person
import com.example.domain.repo.SettingsRepository
import com.example.domain.usecases.GetAllCharsUsecase
import com.example.domain.usecases.SaveCharsToDbUsecase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PersonViewModel @Inject constructor(
    private val getAllCharsFromDb: GetAllCharsUsecase.FromDb,
    private val getAllCharsFromApi: GetAllCharsUsecase.FromApi,
    private val saveAllChars: SaveCharsToDbUsecase,
    private val settingsRepository: SettingsRepository,
): ViewModel() {
    val list = MutableLiveData<List<PersonListItem>>(emptyList())
    val loading = MutableLiveData(false)
    val error = MutableLiveData("")
    val infoToShow = MutableLiveData<Person?>(null)

    private var allItems = emptyList<PersonListItem>()
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
                var fromApi = emptyList<Person>()
                var fromDb = emptyList<Person>()
                if (cache) {
                    fromDb = withContext(Dispatchers.IO) { getAllCharsFromDb() }
                    allItems = fromDb.map { PersonListItem.fromDomain(++id, it, ::open) }
                    filterList(searchQuery)
                }
                if (api) {
                    fromApi = withContext(Dispatchers.IO) { getAllCharsFromApi() }
                    allItems = fromApi.map { PersonListItem.fromDomain(++id, it, ::open) }
                    filterList(searchQuery)
                }
                if (api && cache) {
                    val toSave = fromApi.toMutableList()
                    withContext(Dispatchers.IO) {
                        toSave.removeAll(fromDb)
                        saveAllChars(toSave)
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

    private fun open(id: Int) {
        val person = allItems.firstOrNull { it.id == id }?.toDomain()
        infoToShow.value = person
    }

    private fun filterList(query: String) {
        list.value = allItems.filter { p ->
            query.isEmpty() ||
                    query.lowercase() in p.name.lowercase() ||
                    query.lowercase() in p.occupation.lowercase()
        }
    }

    private fun exceptionHandler() = CoroutineExceptionHandler { _, t ->
        error.value = "Failed to connect, check your internet"
        loading.value = false
        t.printStackTrace()
    }
}