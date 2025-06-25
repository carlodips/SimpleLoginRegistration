package com.carlodips.simpleloginregistration.domain.model

data class UserBean(
    val userId: Int? = null,

    val username: String,

    val email: String,

    val password: String
)