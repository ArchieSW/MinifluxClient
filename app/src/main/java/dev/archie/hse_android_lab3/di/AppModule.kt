package dev.archie.hse_android_lab3.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.archie.hse_android_lab3.api.client.EntryClient
import dev.archie.hse_android_lab3.api.client.FeedClient
import dev.archie.hse_android_lab3.api.client.UserClient
import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.Interceptor.*
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    const val USERNAME = ""
    const val PASSWORD = ""
    const val BASE_URL = ""

    class AuthInterceptor: Interceptor {
        override fun intercept(chain: Chain): Response {
            val request = chain.request().newBuilder()
                .addHeader("Authorization", Credentials.basic(USERNAME, PASSWORD))
                .build()
            return chain.proceed(request)
        }
    }

    @Provides
    @Singleton
    fun provideUserClient() : UserClient {
        val okHttpClient = getOkHttpClient()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(UserClient::class.java);
    }

    @Provides
    @Singleton
    fun provideFeedClient() : FeedClient {
        val okHttpClient = getOkHttpClient()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(FeedClient::class.java);
    }

    @Provides
    @Singleton
    fun provideEntryClient() : EntryClient {
        val okHttpClient = getOkHttpClient()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(EntryClient::class.java);
    }

    private fun getOkHttpClient(): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor())
            .build()
        return okHttpClient
    }

}