package com.heira.app.core

import com.heira.app.data.remote.CoreApi
import com.heira.app.data.remote.OpenRouteApi
import com.heira.app.data.repository.CoreRepositoryImpl
import com.heira.app.data.repository.OpenRouteRepositoryImpl
import com.heira.app.domain.repository.CoreRepository
import com.heira.app.domain.repository.OpenRouteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    // API developed intern for the application
    @Singleton
    @Named("heiraAPI")
    @Provides
    fun provideRetrofitHeira(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // API extern the open route service
    @Singleton
    @Named("openRouteServiceApiExtern")
    @Provides
    fun provideRetrofitORS(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.openrouteservice.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Module's dependence injection API
    @Singleton
    @Provides
    fun coreApi(@Named("heiraAPI") retrofit: Retrofit): CoreApi {
        return retrofit.create(CoreApi::class.java)
    }

    @Singleton
    @Provides
    fun openRouteApi(@Named("openRouteServiceApiExtern") retrofit: Retrofit): OpenRouteApi {
        return retrofit.create(OpenRouteApi::class.java)
    }

    // Repository injection module
    @Provides
    @Singleton
    fun coreRepository(api: CoreApi): CoreRepository {
        return CoreRepositoryImpl(api = api)
    }

    @Provides
    @Singleton
    fun openRouteRepository(api: OpenRouteApi): OpenRouteRepository {
        return OpenRouteRepositoryImpl(api = api)
    }

}