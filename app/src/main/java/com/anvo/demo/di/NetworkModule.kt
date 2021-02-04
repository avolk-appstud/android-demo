package com.dupuis.webtoonfactory.di

import com.anvo.demo.BuildConfig
import com.anvo.demo.interceptor.ConnectivityInterceptor
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.Date
import java.util.concurrent.TimeUnit

const val READ_TIMEOUT_IN_MINUTES: Long = 3L
const val DI_MOCK_ENABLED = "mockEnabled"

val networkModule = module {

    single(named(DI_MOCK_ENABLED)) { BuildConfig.MOCK_ENABLED }

    // Initialise the network
    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASEURL)
            .client(get<OkHttpClient>())
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    // Initialize OK HTTP Client
    single<OkHttpClient> {
        OkHttpClient.Builder()
            .apply {
                readTimeout(READ_TIMEOUT_IN_MINUTES, TimeUnit.MINUTES)

                addInterceptor(ConnectivityInterceptor(get()))

                // API Key
                addInterceptor { chain ->
                    val requestBuilder = chain.request().newBuilder()
                    requestBuilder.header("x-api-key", "61e1f008-a969-4033-866a-8d811da183c7")
                    chain.proceed(requestBuilder.build())
                }

                // Access token
                addInterceptor(TokenInterceptor(get(), BuildConfig.API_BASEURL, get()))

                if (get(named(DI_MOCK_ENABLED))) {
                    addInterceptor((get<MockInterceptor>()))
                }

                // Logger
                if (BuildConfig.DEBUG) {
                    val logger = HttpLoggingInterceptor()
                    logger.level = HttpLoggingInterceptor.Level.BODY
                    addInterceptor(logger)
                }
            }.build()
    }

    // MOCK INTERCEPTOR
    factory<MockInterceptor> { DebugMockInterceptor(get()) }

    // Initialize Moshi
    single<Moshi> {
        Moshi.Builder()
            .add(Date::class.java, Rfc3339DateJsonAdapter().nullSafe())
            .build()
    }
}
