package com.sam.paging3practice.hilt

import android.content.Context
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.sam.paging3practice.repo.AttractionApi
import com.sam.paging3practice.repo.repository.BaseRepository
import com.sam.paging3practice.room.RoomDB
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {
    private val API_URL = "https://www.travel.taipei"

    @Singleton
    @Provides
    fun provideMoshi() = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @Singleton
    @Provides
    fun provideOkHttpClient() = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
        .build()

    @Singleton
    @Provides
    fun provideRetrofit(moshi: Moshi, client: OkHttpClient) = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .baseUrl(API_URL)
        .client(client)
        .build()

    @Singleton
    @Provides
    fun provideAttractionApi(retrofit: Retrofit) = retrofit.create(AttractionApi::class.java)

    @Singleton
    @Provides
    fun provideRoomDB(@ApplicationContext context: Context) = RoomDB.getInstance(context)

    @Singleton
    @Provides
    fun provideAttractionDao(roomDB: RoomDB) = roomDB.attractionDao()

    @Singleton
    @Provides
    fun provideRepository(api: AttractionApi, roomDB: RoomDB) = BaseRepository(api, roomDB)


}