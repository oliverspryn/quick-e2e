package com.oliverspryn.android.quicke2e.sample.di.modules

import android.content.Context
import android.view.LayoutInflater
import com.oliverspryn.android.quicke2e.sample.mvc.ViewMvcFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext

@Module
@InstallIn(ActivityComponent::class)
class MvcModule {
    @Provides
    fun provideLayoutInflater(
        @ActivityContext context: Context
    ): LayoutInflater = LayoutInflater.from(
        context
    )

    @Provides
    fun provideViewMvcFactory(
        layoutInflater: LayoutInflater
    ) = ViewMvcFactory(
        layoutInflater = layoutInflater
    )
}
