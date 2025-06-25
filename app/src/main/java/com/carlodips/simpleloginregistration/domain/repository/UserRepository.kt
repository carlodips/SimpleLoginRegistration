package com.carlodips.simpleloginregistration.domain.repository

import com.carlodips.simpleloginregistration.data.local.entity.UserEntity
import com.carlodips.simpleloginregistration.domain.model.UserBean

interface UserRepository {
    suspend fun insertUser(user: UserBean): Long

    suspend fun getUser(userInput: String, password: String): UserEntity?
}
