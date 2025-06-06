package com.raulastete.notemark.di

import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.raulastete.notemark.data.validator.EmailPatternValidator
import com.raulastete.notemark.domain.PatternValidator
import com.raulastete.notemark.domain.UserDataValidator
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val appModule = module {
    single<SharedPreferences> {
        var masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

        EncryptedSharedPreferences.create(
            "secret_shared_prefs",
            masterKeyAlias,
            androidApplication(),
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    factory {
        UserDataValidator(get())
    }

    factory<PatternValidator> {
        EmailPatternValidator()
    }
}