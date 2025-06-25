package com.carlodips.simpleloginregistration.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.carlodips.simpleloginregistration.data.local.dao.UserDao
import com.carlodips.simpleloginregistration.data.local.entity.UserEntity

@Database(
    entities = [
        UserEntity::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract val userDao: UserDao

    companion object {
        const val DATABASE_NAME = "simple_login_registration_room.db"
    }
}