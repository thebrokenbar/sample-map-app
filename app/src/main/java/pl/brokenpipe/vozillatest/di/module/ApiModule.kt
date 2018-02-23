package pl.brokenpipe.vozillatest.di.module

import dagger.Module
import dagger.Provides
import io.swagger.client.ApiClient
import io.swagger.client.api.MapApi
import javax.inject.Singleton

/**
 * Created by gwierzchanowski on 23.02.2018.
 */
@Module
class ApiModule {
    @Provides
    @Singleton
    fun provideApiClient(): ApiClient {
        return ApiClient() // No Authorization needed
    }

    @Provides
    @Singleton
    fun provideMapApi(apiClient: ApiClient) : MapApi {
        return apiClient.createService(MapApi::class.java)
    }
}