package com.oliverspryn.android.edgetoedge.photos

import io.reactivex.Single
import retrofit2.http.GET

interface PhotosApiService {
    @GET("/photos")
    fun getPhotos(): Single<List<PhotosModel>>
}
