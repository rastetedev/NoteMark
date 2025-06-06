package com.raulastete.notemark.domain

interface PatternValidator {
    fun matches(value: String): Boolean
}