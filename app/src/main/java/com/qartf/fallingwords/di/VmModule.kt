package com.qartf.fallingwords.di

import android.content.Context
import com.qartf.fallingwords.data.LocalData
import com.qartf.fallingwords.data.Repository
import com.qartf.fallingwords.ui.GameViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val vmModule = module {

    viewModel { GameViewModel(get()) }
}

val dataModule = module {

    single { createRepository(get(), get()) }

    single { createLocalData(get()) }

    single { createIoDispatcher() }
}

fun createIoDispatcher() = Dispatchers.Default

fun createLocalData(context: Context): LocalData {
    return LocalData(context)
}

fun createRepository(
    localData: LocalData,
    ioDispatcher: CoroutineDispatcher
): Repository {
    return Repository(localData, ioDispatcher)
}