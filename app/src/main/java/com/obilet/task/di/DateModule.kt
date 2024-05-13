package com.obilet.task.di

import com.obilet.task.utilities.DateHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DateModule {

    @Singleton
    @Provides
    fun provideDateHelper(): DateHelper {
        return DateHelper()
    }
}