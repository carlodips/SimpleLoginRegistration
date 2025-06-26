package com.carlodips.simpleloginregistration.domain.validation

class RegexPatternUtil {
    companion object {
        const val LETTERS_NUMBERS_ONLY = "[A-Za-z0-9]+"
        const val EMAIL = "^[_A-Za-z0-9\\-]+(\\.[_A-Za-z0-9\\-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
    }
}