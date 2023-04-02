package com.fadli.newsupdate.di

import android.app.Application
import androidx.room.Room
import com.fadli.newsupdate.api.Api
import com.fadli.newsupdate.data.Database
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    // Instantiating Retrofit
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(Api.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    // Instantiating NewsApi
    @Provides
    @Singleton
    fun provideNewsApi(retrofit: Retrofit): Api =
        retrofit.create(Api::class.java)

    // Instantiating NewsArticleDatabase
    @Provides
    @Singleton
    fun provideDatabase(app: Application): Database =
        Room.databaseBuilder(app, Database::class.java, "news_article_database")
            .fallbackToDestructiveMigration()
            .build()
}