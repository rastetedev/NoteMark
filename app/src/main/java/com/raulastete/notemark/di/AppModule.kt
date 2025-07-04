package com.raulastete.notemark.di

import android.content.SharedPreferences
import androidx.room.Room
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.raulastete.notemark.data.local.NoteDao
import com.raulastete.notemark.data.local.NoteMarkDatabase
import com.raulastete.notemark.data.mapper.NoteMapper
import com.raulastete.notemark.data.remote.service.ktor.NoteRemoteService
import com.raulastete.notemark.data.validator.EmailPatternValidator
import com.raulastete.notemark.domain.PatternValidator
import com.raulastete.notemark.domain.validator.UserDataValidator
import com.raulastete.notemark.presentation.NoteMarkApp
import kotlinx.coroutines.CoroutineScope
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import kotlin.jvm.java

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

    single<CoroutineScope> {
        (androidApplication() as NoteMarkApp).applicationScope
    }

    single<NoteMarkDatabase> {
        Room.databaseBuilder(androidContext(), NoteMarkDatabase::class.java, "notemark.db")
            .build()
    }


    single<NoteDao> {
        val database: NoteMarkDatabase = get()
        database.noteDao()
    }

    single { NoteMapper() }

    single { NoteRemoteService(get()) }

    factory {
        UserDataValidator(get())
    }

    factory<PatternValidator> {
        EmailPatternValidator()
    }
}