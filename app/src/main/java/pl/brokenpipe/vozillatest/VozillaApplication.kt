package pl.brokenpipe.vozillatest

import android.app.Application
import pl.brokenpipe.vozillatest.di.component.AppComponent
import pl.brokenpipe.vozillatest.di.component.DaggerAppComponent
import pl.brokenpipe.vozillatest.di.module.AppModule
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by gwierzchanowski on 20.02.2018.
 */
class VozillaApplication : Application() {
    @Inject
    lateinit var timberTree: Timber.Tree

    lateinit var appComponent: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
                .appModule(AppModule())
                .build()

        appComponent.inject(this)

        Timber.plant(timberTree)
    }
}