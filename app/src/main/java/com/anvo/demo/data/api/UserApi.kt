package com.anvo.demo.data.api

import retrofit2.http.GET
import retrofit2.http.Header

interface UserApi {
    @GET("v1/auth/me")
    fun getUsers(
        @Header(value = "Content-Type")
        contentType: String = "application/x-www-form-urlencoded"
    ): UserProfile[]
}