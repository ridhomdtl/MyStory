package com.example.mystoryapp.api

import com.example.mystoryapp.addstory.AddStoryResponse
import com.example.mystoryapp.authentication.LoginResponse
import com.example.mystoryapp.authentication.RegisterResponse
import com.example.mystoryapp.detail.DetailResponse
import com.example.mystoryapp.story.StoriesResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @FormUrlEncoded
    @POST("register")
    fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<RegisterResponse>

    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>

    @GET("stories?location=1")
    fun getStoriesLocation(): Call<StoriesResponse>

    @GET("stories")
    suspend fun getStoriesPager(
        @Query("page") page: Int,
        @Query("size") size: Int
    ): StoriesResponse

    @GET("stories/{id}")
    fun getDetailStory(
        @Path("id") id: String
    ): Call<DetailResponse>

    @Multipart
    @POST("stories")
    fun uploadStory(
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
    ): Call<AddStoryResponse>
}