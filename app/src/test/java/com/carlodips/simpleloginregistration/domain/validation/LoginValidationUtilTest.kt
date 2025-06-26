package com.carlodips.simpleloginregistration.domain.validation

import com.google.common.truth.Truth
import org.junit.Test

class LoginValidationUtilTest {
    @Test
    fun `empty userId returns false`() {
        val result = LoginValidationUtil.validateUserId("").isValid

        Truth.assertThat(result).isFalse()
    }

    @Test
    fun `valid userid returns true`() {
        val result = LoginValidationUtil.validateUserId("testuser123").isValid

        Truth.assertThat(result).isTrue()
    }

    @Test
    fun `empty password returns false`() {
        val result = LoginValidationUtil.validatePassword("").isValid

        Truth.assertThat(result).isFalse()
    }

    @Test
    fun `valid password returns true`() {
        val result = LoginValidationUtil.validateUserId("password123").isValid

        Truth.assertThat(result).isTrue()
    }
}
