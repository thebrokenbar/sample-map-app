package pl.brokenpipe.vozillatest.di.module

import dagger.Module
import dagger.Provides
import timber.log.Timber

/**
 * Created by gwierzchanowski on 20.02.2018.
 */
@Module
class AppModule {
    @Provides
    fun provideTimberTree(): Timber.Tree {
        return Timber.DebugTree()
    }

}