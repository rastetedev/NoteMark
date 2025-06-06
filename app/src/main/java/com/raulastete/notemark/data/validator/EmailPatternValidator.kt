package com.raulastete.notemark.data.validator

import android.util.Patterns
import com.raulastete.notemark.domain.PatternValidator

class EmailPatternValidator : PatternValidator {

    override fun matches(value: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(value).matches()
    }
}