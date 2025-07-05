package com.oliverspryn.android.quicke2e.sample.photos

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface PhotosApiService {
    @GET("/photos")
    fun getPhotos(): Single<List<PhotosModel>>
}
