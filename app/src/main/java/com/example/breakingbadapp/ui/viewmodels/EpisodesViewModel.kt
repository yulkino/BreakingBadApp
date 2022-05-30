package com.example.breakingbadapp.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.breakingbadapp.recyclerview.listitems.EpisodeListItem
import com.example.domain.models.Episode
import com.example.domain.repo.SettingsRepository
import com.example.domain.usecases.GetAllEpisodesUsecase
import com.example.domain.usecases.SaveEpisodesToDbUsecase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class EpisodesViewModel @Inject constructor(
    private val getAllEpisodesFromDb: GetAllEpisodesUsecase.FromDb,
    private val getAllEpisodesFromApi: GetAllEpisodesUsecase.FromApi,
    private val saveAllEpisodes: SaveEpisodesToDbUsecase,
    private val settingsRepository: SettingsRepository,
): ViewModel() {
    val list = MutableLiveData<List<EpisodeListItem>>(emptyList())
    val loading = MutableLiveData(false)
    val error = MutableLiveData("")
    val infoToShow = MutableLiveData<Episode?>(null)

    private var allItems = emptyList<EpisodeListItem>()
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
                var fromApi = emptyList<Episode>()
                var fromDb = emptyList<Episode>()
                if (cache) {
                    fromDb = withContext(Dispatchers.IO) { getAllEpisodesFromDb() }
                    allItems = fromDb.map { EpisodeListItem.fromDomain(++id, it, ::open) }
                    filterList(searchQuery)
                }
                if (api) {
                    fromApi = withContext(Dispatchers.IO) { getAllEpisodesFromApi() }
                    allItems = fromApi.map { EpisodeListItem.fromDomain(++id, it, ::open) }
                    filterList(searchQuery)
                }
                if (api && cache) {
                    val toSave = fromApi.toMutableList()
                    withContext(Dispatchers.IO) {
                        toSave.removeAll(fromDb)
                        saveAllEpisodes(toSave)
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
        list.value = allItems.filter { e ->
            query.isEmpty() || query.lowercase() in e.title.lowercase() ||
                    query.lowercase() in e.number.lowercase()
        }
    }

    private fun exceptionHandler() = CoroutineExceptionHandler { _, t ->
        error.value = "Failed to connect, check your internet"
        loading.value = false
        t.printStackTrace()
    }
}