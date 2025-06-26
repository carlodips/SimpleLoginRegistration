package com.carlodips.simpleloginregistration.domain.validation

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class RegisterValidationUtilTest {
    @Test
    fun `empty username returns false`() {
        val result = RegisterValidationUtil.validateUsernameInput("").isValid

        assertThat(result).isFalse()
    }

    @Test
    fun `username with less than 3 characters returns false`() {
        val result = RegisterValidationUtil.validateUsernameInput("abc").isValid

        assertThat(result).isFalse()
    }

    @Test
    fun `valid username returns true`() {
        val result = RegisterValidationUtil.validateUsernameInput("abcd").isValid

        assertThat(result).isTrue()
    }

    @Test
    fun `empty email returns false`() {
        val result = RegisterValidationUtil.validateEmailInput("").isValid

        assertThat(result).isFalse()
    }

    @Test
    fun `invalid email pattern returns false`() {
        val result = RegisterValidationUtil.validateEmailInput("abc@@.com").isValid

        assertThat(result).isFalse()
    }

    @Test
    fun `valid email pattern returns true`() {
        val result = RegisterValidationUtil.validateEmailInput("carlo123@mail.com").isValid

        assertThat(result).isTrue()
    }

    @Test
    fun `empty password returns false`() {
        val result = RegisterValidationUtil.validatePassword("").isValid

        assertThat(result).isFalse()
    }

    @Test
    fun `password less than 7 characters returns false`() {
        val result = RegisterValidationUtil.validatePassword("test123").isValid

        assertThat(result).isFalse()
    }

    @Test
    fun `password with more than 7 characters but letters only returns false`() {
        val result = RegisterValidationUtil.validatePassword("abcdefghi").isValid

        assertThat(result).isFalse()
    }

    @Test
    fun `password with more than 7 characters but digits only returns false`() {
        val result = RegisterValidationUtil.validatePassword("1234567").isValid

        assertThat(result).isFalse()
    }

    @Test
    fun `password with more than 7 characters and is digits and letters returns false`() {
        val result = RegisterValidationUtil.validatePassword("test1234").isValid

        assertThat(result).isTrue()
    }

    @Test
    fun `empty confirm password returns false`() {
        val result = RegisterValidationUtil.doPasswordsMatch("test1234", "").isValid

        assertThat(result).isFalse()
    }

    @Test
    fun `empty password1 and password2 has value returns false`() {
        val result = RegisterValidationUtil.doPasswordsMatch("", "test1234").isValid

        assertThat(result).isFalse()
    }

    @Test
    fun `password1 and password2 does not match returns false`() {
        val result = RegisterValidationUtil.doPasswordsMatch("abc1234", "test1234").isValid

        assertThat(result).isFalse()
    }

    @Test
    fun `password1 and password2 is the same returns true`() {
        val result = RegisterValidationUtil.doPasswordsMatch("abc1234", "abc1234").isValid

        assertThat(result).isTrue()
    }
}
