package com.oliverspryn.android.quicke2e.sample.di.modules

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.reactivex.rxjava3.disposables.CompositeDisposable

@Module
@InstallIn(SingletonComponent::class)
class RxJavaModule {
    @Provides
    fun provideCompositeDisposable() = CompositeDisposable()
}
