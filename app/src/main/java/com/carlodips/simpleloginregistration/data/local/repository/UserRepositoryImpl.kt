package com.carlodips.simpleloginregistration.data.local.repository

import com.carlodips.simpleloginregistration.data.local.dao.UserDao
import com.carlodips.simpleloginregistration.data.local.entity.UserEntity
import com.carlodips.simpleloginregistration.domain.model.UserBean
import com.carlodips.simpleloginregistration.domain.repository.UserRepository

class UserRepositoryImpl(
    private val dao: UserDao
) : UserRepository {
    override var loggedInUser: UserBean? = null

    override suspend fun insertUser(user: UserBean): Long {
        return dao.insertUser(UserEntity.toEntity(user))
    }

    override suspend fun getUser(userInput: String, password: String): UserEntity? {
        val user = dao.getUser(userInput = userInput, password = password)

        if (user != null) {
            setLoggedInUser(user)
        }

        return user
    }

    override suspend fun checkIfUsernameIsTaken(username: String): Boolean {
        return dao.checkIfUsernameIsTaken(username)
    }

    override suspend fun checkIfEmailIsTaken(email: String): Boolean {
        return dao.checkIfEmailIsTaken(email)
    }
}
