package com.carlodips.simpleloginregistration.di

import android.app.Application
import androidx.room.Room
import com.carlodips.simpleloginregistration.data.AppDatabase
import com.carlodips.simpleloginregistration.data.local.repository.UserRepositoryImpl
import com.carlodips.simpleloginregistration.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideAppDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideUserRepository(database: AppDatabase): UserRepository {
        return UserRepositoryImpl(database.userDao)
    }
}
