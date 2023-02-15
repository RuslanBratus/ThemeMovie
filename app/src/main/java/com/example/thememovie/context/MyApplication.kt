package com.example.thememovie.context

import android.app.Application
import com.example.thememovie.main.view.MainFragment
import com.example.thememovie.main.viewmodel.injection.ApiModule
import com.example.thememovie.main.viewmodel.injection.AppModule
import com.example.thememovie.openedmovie.OpenedMovieFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, ApiModule::class])
interface AppComponent {
    fun inject(mainFragment: MainFragment)
    fun inject(openedMovieFragment: OpenedMovieFragment)
}

class MyApplication: Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }
}