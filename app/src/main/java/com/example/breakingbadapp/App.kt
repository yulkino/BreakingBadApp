package com.example.breakingbadapp

import android.app.Application
import android.content.Context
import androidx.fragment.app.Fragment
import com.example.breakingbadapp.di.AppModule
import com.example.breakingbadapp.di.Dagger
import com.example.breakingbadapp.di.DaggerDagger

class App: Application() {
    lateinit var dagger: Dagger

    override fun onCreate() {
        super.onCreate()
        dagger = DaggerDagger.builder()
            .appModule(AppModule(this))
            .build()
    }
}

fun Context.dagger() = (applicationContext as App).dagger
fun Fragment.viewModelFactory() = requireContext().dagger().viewModelFactory()