package com.example.birlik.di

import android.app.Application
import com.example.birlik.data.AppLifecycleCallbacks
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    //Firebase Auth
    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = Firebase.auth

    //Firebase FireStore
    @Singleton
    @Provides
    fun provideFireStore(): FirebaseFirestore = Firebase.firestore

    //Firebase Storage
    @Singleton
    @Provides
    fun provideStorage(): FirebaseStorage = Firebase.storage



//    //Retrofit
//    @Singleton
//    @Provides
//    fun provideRetrofit(): Retrofit {
//        return Retrofit.Builder()
//            .baseUrl(BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//    }
//    @Singleton
//    @Provides
//    fun provideRetrofitApi(retrofit: Retrofit): RetrofitApi{
//        return retrofit.create(RetrofitApi::class.java)
//    }



    @Provides
    @Singleton
    fun provideAppLifecycleCallbacks(): AppLifecycleCallbacks {
        return AppLifecycleCallbacks()
    }

}