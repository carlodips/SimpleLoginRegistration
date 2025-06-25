package com.carlodips.simpleloginregistration.data.local.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.carlodips.simpleloginregistration.data.AppDatabase
import com.carlodips.simpleloginregistration.data.local.entity.UserEntity
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class UserDaoTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: AppDatabase
    private lateinit var dao: UserDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()

        dao = database.userDao
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertUser_returnsTrue() = runTest {
        val userToBeAdded = UserEntity(
            userId = 1,
            username = "testuser1",
            email = "testuser1@gmail.com",
            password = "password",
        )

        dao.insertUser(userToBeAdded)

        val userAdded = dao.getUser(
            userInput = userToBeAdded.username,
            password = userToBeAdded.password
        )

        assertThat(userAdded?.userId).isEqualTo(userAdded?.userId)
    }

    @Test
    fun checkIfUsernameIsTaken_returnsTrue() = runTest {
        dao.insertUser(
            UserEntity(
                username = "testuser1",
                email = "testuser1@gmail.com",
                password = "password",
            )
        )

        val userToBeAdded = UserEntity(
            username = "testuser1",
            email = "testuser2@gmail.com",
            password = "password",
        )

        assertThat(dao.checkIfUsernameIsTaken(username = userToBeAdded.username)).isNotNull()
    }

    @Test
    fun checkIfUsernameIsTaken_returnsFalse() = runTest {
        dao.insertUser(
            UserEntity(
                username = "testuser1",
                email = "testuser1@gmail.com",
                password = "password",
            )
        )

        val userToBeAdded = UserEntity(
            username = "testuser2",
            email = "testuser2@gmail.com",
            password = "password",
        )

        assertThat(dao.checkIfUsernameIsTaken(username = userToBeAdded.username)).isNull()
    }

    @Test
    fun checkIfEmailIsTaken_returnsTrue() = runTest {
        dao.insertUser(
            UserEntity(
                username = "testuser1",
                email = "testuser1@gmail.com",
                password = "password",
            )
        )

        val userToBeAdded = UserEntity(
            username = "testuser2",
            email = "testuser1@gmail.com",
            password = "password",
        )

        assertThat(dao.checkIfEmailIsTaken(email = userToBeAdded.email)).isNotNull()
    }

    @Test
    fun checkIfEmailIsTaken_returnsFalse() = runTest {
        dao.insertUser(
            UserEntity(
                username = "testuser1",
                email = "testuser1@gmail.com",
                password = "password",
            )
        )

        val userToBeAdded = UserEntity(
            username = "testuser2",
            email = "testuser2@gmail.com",
            password = "password",
        )

        assertThat(dao.checkIfEmailIsTaken(email = userToBeAdded.email)).isNull()
    }
}