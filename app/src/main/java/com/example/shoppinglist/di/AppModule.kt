package com.example.shoppinglist.di

import android.app.Application
import android.util.Log
import com.example.shoppinglist.repository.AuthRepository
import com.example.shoppinglist.repository.MyStorageRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth() = FirebaseAuth.getInstance()



    @Provides
    @Singleton
    fun provideMyStorageRepository(): MyStorageRepository {
        Log.d("s","provideMyStorageRepository")
        return MyStorageRepository( provideFirebaseAuth(), provideFireStoreInstance())

    }

  @Provides
  @Singleton
  fun provideFireStoreInstance (): FirebaseFirestore{
      return FirebaseFirestore.getInstance()
  }

    @Provides
    @Singleton
    fun provideAuthRepository(): AuthRepository {
        return AuthRepository(provideFirebaseAuth())
    }


}