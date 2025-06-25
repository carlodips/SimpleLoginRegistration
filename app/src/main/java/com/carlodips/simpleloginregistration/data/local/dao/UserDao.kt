package com.carlodips.simpleloginregistration.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.carlodips.simpleloginregistration.data.local.entity.UserEntity

@Dao
interface UserDao {
    // For Register
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity): Long

    @Query("SELECT * FROM Users WHERE username = :username")
    suspend fun checkIfUsernameIsTaken(username: String): UserEntity?

    @Query("SELECT * FROM Users WHERE email = :email")
    suspend fun checkIfEmailIsTaken(email: String): UserEntity?

    // For Login
    @Query("SELECT * FROM Users WHERE (username = :userInput OR email = :userInput)" +
            " AND password = :password")
    suspend fun getUser(userInput: String, password: String): UserEntity?
}
