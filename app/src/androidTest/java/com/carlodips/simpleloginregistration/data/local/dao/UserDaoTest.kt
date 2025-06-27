package com.carlodips.simpleloginregistration.data.local.dao

import androidx.test.filters.SmallTest
import com.carlodips.simpleloginregistration.data.AppDatabase
import com.carlodips.simpleloginregistration.data.local.entity.UserEntity
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@SmallTest
@HiltAndroidTest
class UserDaoTest {

    @Inject
    @Named("test_db") //Added named since there is also database from main
    lateinit var database: AppDatabase
    private lateinit var dao: UserDao

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setup() {
        hiltRule.inject()
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

        assertThat(dao.checkIfUsernameIsTaken(username = userToBeAdded.username)).isTrue()
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

        assertThat(dao.checkIfUsernameIsTaken(username = userToBeAdded.username)).isFalse()
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

        assertThat(dao.checkIfEmailIsTaken(email = userToBeAdded.email)).isTrue()
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

        assertThat(dao.checkIfEmailIsTaken(email = userToBeAdded.email)).isFalse()
    }
}