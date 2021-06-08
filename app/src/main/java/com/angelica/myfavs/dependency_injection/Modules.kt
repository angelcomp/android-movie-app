package com.angelica.myfavs.dependency_injection

import android.app.Application
import androidx.room.Room
import com.angelica.myfavs.database.AppDatabase
import com.angelica.myfavs.database.FavoriteDao
import com.angelica.myfavs.database.Favoritedb
import com.angelica.myfavs.services.APIRepository
import com.angelica.myfavs.services.FavoritesRepository
import com.angelica.myfavs.services.ServiceAPI
import com.angelica.myfavs.viewmodel.DetailViewModel
import com.angelica.myfavs.viewmodel.HomeViewModel
import com.angelica.myfavs.viewmodel.SearchViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val roomDataBaseModule = module {

    fun provideDataBase(application: Application): AppDatabase {
        return Room.databaseBuilder(application, AppDatabase::class.java, "myfavsdb")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

    fun provideFavoritesDao(database: AppDatabase): FavoriteDao = database.favoritosDao()
    fun provideFavoritesDb(dao: FavoriteDao): Favoritedb = Favoritedb(dao)

    single { provideDataBase(androidApplication()) }

    single { provideFavoritesDao(get()) }
    single { provideFavoritesDb(get()) }
}


val repositoryModule = module {
    single { FavoritesRepository(get()) }
    single { APIRepository(get(), get()) }
}

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { DetailViewModel(get(), get()) }
    viewModel { SearchViewModel(get()) }
}

val retrofitModule = module {

    fun provideRetrofit(): Retrofit {
        val apiUrl = "http://www.omdbapi.com/"

        val logger = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
        logger.redactHeader("Authorization")
        logger.redactHeader("Cookie")

        val client = OkHttpClient.Builder()
            .addInterceptor(logger)
            .build()

        return Retrofit.Builder()
            .baseUrl(apiUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun provideAPI(retrofit: Retrofit): ServiceAPI = retrofit.create(ServiceAPI::class.java)

    single { provideRetrofit() }
    single { provideAPI(get()) }

}