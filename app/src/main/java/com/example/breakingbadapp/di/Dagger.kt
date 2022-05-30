package com.example.breakingbadapp.di

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.breakingbadapp.MainActivity
import com.example.breakingbadapp.ui.SettingsFragment
import com.example.breakingbadapp.ui.viewmodels.EpisodesViewModel
import com.example.breakingbadapp.ui.viewmodels.PersonViewModel
import com.example.breakingbadapp.ui.viewmodels.QuotesViewModel
import com.example.domain.repo.EpisodeRepository
import com.example.domain.repo.PersonRepository
import com.example.domain.repo.QuotesRepository
import com.example.domain.repo.SettingsRepository
import com.example.domain.usecases.*
import com.example.domain.usecases.impl.*
import com.example.infrastructure.api.RetrofitClient
import com.example.infrastructure.db.Database
import com.example.infrastructure.repository.EpisodeRepoImpl
import com.example.infrastructure.repository.PersonRepoImpl
import com.example.infrastructure.repository.QuoteRepoImpl
import com.example.infrastructure.repository.SettingsRepoImpl
import com.example.infrastructure.storage.Storage
import com.example.infrastructure.storage.StorageImpl
import dagger.*
import dagger.multibindings.IntoMap
import javax.inject.Inject
import javax.inject.Provider
import kotlin.reflect.KClass

@Component(modules = [AppModule::class])
interface Dagger {
    fun inject(settings: SettingsFragment)
    fun inject(activity: MainActivity)
    fun viewModelFactory(): ViewModelFactory
}

@Module(includes = [
    ViewModelBindsModule::class,
    RepoModule::class,
    RoomModule::class,
    RetrofitModule::class,
    UsecaseModule::class,
    StorageModule::class,
])
class AppModule(private val context: Context) {
    @Provides fun provideContext() = context
    @Provides fun provideSharedPrefs(context: Context): SharedPreferences =
        context.getSharedPreferences("settings", 0)
}

@Module
abstract class StorageModule {
    @Binds abstract fun bindStorage(storageImpl: StorageImpl): Storage
}

@Module
abstract class UsecaseModule {
    @Binds abstract fun bindGetAllPersonsFromApi(impl: GetAllCharsFromApi): GetAllCharsUsecase.FromApi
    @Binds abstract fun bindGetAllPersonsFromDb(impl: GetAllCharsFromDb): GetAllCharsUsecase.FromDb
    @Binds abstract fun bindSaveAllPersons(impl: SaveAllChars): SaveCharsToDbUsecase
    @Binds abstract fun bindGetAllEpisodesFromApi(impl: GetAllEpisodesFromApi): GetAllEpisodesUsecase.FromApi
    @Binds abstract fun bindGetAllEpisodesFromDb(impl: GetAllEpisodesFromDb): GetAllEpisodesUsecase.FromDb
    @Binds abstract fun bindSaveAllEpisodes(impl: SaveAllEpisodes): SaveEpisodesToDbUsecase
    @Binds abstract fun bindGetAllQuotesFromApi(impl: GetAllQuotesFromApi): GetAllQuotesUsecase.FromApi
    @Binds abstract fun bindGetAllQuotesFromDb(impl: GetAllQuotesFromDb): GetAllQuotesUsecase.FromDb
    @Binds abstract fun bindSaveAllQuotes(impl: SaveAllQuotes): SaveQuotesToDbUsecase
}

@Module
class RetrofitModule {
    @Provides fun provideRetrofitClient() = RetrofitClient()
    @Provides fun provideRetrofitServices(client: RetrofitClient) = client.retrofitService
}

@Module
class RoomModule {
    @Provides fun provideDatabase(context: Context) =
        Room.databaseBuilder(context, Database::class.java, "db")
            .fallbackToDestructiveMigration().build()
    @Provides fun providePersonDao(db: Database) = db.personDao()
    @Provides fun provideEpisodesDao(db: Database) = db.episodeDao()
    @Provides fun provideQuotesDao(db: Database) = db.quoteDao()
}

@Module
abstract class RepoModule {
    @Binds abstract fun bindPersonRepo(repo: PersonRepoImpl): PersonRepository
    @Binds abstract fun bindEpisodesRepo(repo: EpisodeRepoImpl): EpisodeRepository
    @Binds abstract fun bindQuotesRepo(repo: QuoteRepoImpl): QuotesRepository
    @Binds abstract fun bindSettingsRepo(repo: SettingsRepoImpl): SettingsRepository
}

@Module
abstract class ViewModelBindsModule {
    @Binds abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(PersonViewModel::class)
    abstract fun personViewModel(sampleViewModel: PersonViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(QuotesViewModel::class)
    abstract fun quotesViewModel(sampleViewModel: QuotesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(EpisodesViewModel::class)
    abstract fun episodesViewModel(sampleViewModel: EpisodesViewModel): ViewModel
}

@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER,
)
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@MapKey
internal annotation class ViewModelKey(val value: KClass<out ViewModel>)

@Suppress("UNCHECKED_CAST")
class ViewModelFactory @Inject constructor(
    private val viewModels: MutableMap<Class<out ViewModel>, Provider<ViewModel>>
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val viewModelProvider = viewModels[modelClass]
            ?: throw IllegalStateException("ViewModel $modelClass not found")
        return viewModelProvider.get() as T
    }
}