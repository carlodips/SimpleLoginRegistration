package com.carlodips.simpleloginregistration.domain.validation

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class ValidationUtilTest {
    @Test
    fun `empty username returns false`() {
        val result = ValidationUtil.validateUsernameInput("").isValid

        assertThat(result).isFalse()
    }

    @Test
    fun `username with less than 3 characters returns false`() {
        val result = ValidationUtil.validateUsernameInput("abc").isValid

        assertThat(result).isFalse()
    }

    @Test
    fun `valid username returns true`() {
        val result = ValidationUtil.validateUsernameInput("abcd").isValid

        assertThat(result).isTrue()
    }

    @Test
    fun `empty email returns false`() {
        val result = ValidationUtil.validateEmailInput("").isValid

        assertThat(result).isFalse()
    }

    @Test
    fun `invalid email pattern returns false`() {
        val result = ValidationUtil.validateEmailInput("abc@@.com").isValid

        assertThat(result).isFalse()
    }

    @Test
    fun `valid email pattern returns true`() {
        val result = ValidationUtil.validateEmailInput("carlo123@mail.com").isValid

        assertThat(result).isTrue()
    }

    @Test
    fun `empty password returns false`() {
        val result = ValidationUtil.validatePassword("").isValid

        assertThat(result).isFalse()
    }

    @Test
    fun `password less than 7 characters returns false`() {
        val result = ValidationUtil.validatePassword("test123").isValid

        assertThat(result).isFalse()
    }

    @Test
    fun `password with more than 7 characters but letters only returns false`() {
        val result = ValidationUtil.validatePassword("abcdefghi").isValid

        assertThat(result).isFalse()
    }

    @Test
    fun `password with more than 7 characters but digits only returns false`() {
        val result = ValidationUtil.validatePassword("1234567").isValid

        assertThat(result).isFalse()
    }

    @Test
    fun `password with more than 7 characters and is digits and letters returns false`() {
        val result = ValidationUtil.validatePassword("test1234").isValid

        assertThat(result).isTrue()
    }

    @Test
    fun `empty confirm password returns false`() {
        val result = ValidationUtil.doPasswordsMatch("test1234", "").isValid

        assertThat(result).isFalse()
    }

    @Test
    fun `empty password1 and password2 has value returns false`() {
        val result = ValidationUtil.doPasswordsMatch("", "test1234").isValid

        assertThat(result).isFalse()
    }

    @Test
    fun `password1 and password2 does not match returns false`() {
        val result = ValidationUtil.doPasswordsMatch("abc1234", "test1234").isValid

        assertThat(result).isFalse()
    }

    @Test
    fun `password1 and password2 is the same returns true`() {
        val result = ValidationUtil.doPasswordsMatch("abc1234", "abc1234").isValid

        assertThat(result).isTrue()
    }
}
