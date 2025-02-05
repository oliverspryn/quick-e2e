package com.oliverspryn.android.edgetoedge.di.modules

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.reactivex.disposables.CompositeDisposable

@Module
@InstallIn(SingletonComponent::class)
class RxJavaModule {
    @Provides
    fun provideCompositeDisposable() = CompositeDisposable()
}
