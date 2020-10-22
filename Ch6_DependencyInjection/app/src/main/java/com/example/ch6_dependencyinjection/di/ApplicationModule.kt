package com.example.ch6_dependencyinjection.di

import com.example.ch6_dependencyinjection.di.qualifier.AppHash
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
object ApplicationModule {

    @AppHash
    @Provides
    fun provideHash() = hashCode().toString()
}