package com.meskitah.itmc.domain.di

import com.meskitah.itmc.domain.repository.FlickerRepository
import com.meskitah.itmc.domain.use_case.FlickerUseCases
import com.meskitah.itmc.domain.use_case.GetImages
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object FlickerDomainModule {

    @Provides
    @ViewModelScoped
    fun provideSportsUseCases(repository: FlickerRepository): FlickerUseCases {
        return FlickerUseCases(
            getImages = GetImages(repository)
        )
    }
}