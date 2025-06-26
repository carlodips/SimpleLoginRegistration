package com.carlodips.simpleloginregistration.domain.repository

import com.carlodips.simpleloginregistration.data.local.entity.UserEntity
import com.carlodips.simpleloginregistration.domain.model.UserBean

interface UserRepository {
    var loggedInUser: UserBean?

    suspend fun insertUser(user: UserBean): Long

    suspend fun getUser(userInput: String, password: String): UserEntity?

    suspend fun checkIfUsernameIsTaken(username: String): Boolean

    suspend fun checkIfEmailIsTaken(email: String): Boolean

    fun setLoggedInUser(userEntity: UserEntity) {
        loggedInUser = userEntity.toDomain()
    }

    fun clearLoggedInUser() {
        loggedInUser = null
    }
}
