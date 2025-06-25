package com.carlodips.simpleloginregistration.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.carlodips.simpleloginregistration.domain.model.UserBean

@Entity(
    tableName = "Users"
)

data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "userId")
    val userId: Int? = null,

    @ColumnInfo(name = "username")
    val username: String,

    @ColumnInfo(name = "email")
    val email: String,

    @ColumnInfo(name = "password")
    val password: String
) {
    companion object {
        fun toEntity(user: UserBean) = UserEntity(
            userId = user.userId,
            username = user.username,
            email = user.email,
            password = user.password
        )
    }

    fun toDomain() = UserBean(
        userId = userId,
        username = username,
        email = email,
        password = password
    )
}
