package com.tama.githubuser

import android.provider.Contacts.SettingsColumns.KEY
import androidx.viewbinding.BuildConfig
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {


    @GET("search/users")
    @Headers("Authorization: token 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 ")
    fun getUser(
        @Query("q") query : String
    ) : Call<UserResponse>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getDetail(
        @Path("username") username : String
    ) : Call<DetailResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getFollowers(
        @Path("username") username : String
    ) : Call<ArrayList<User>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getFollowing(
        @Path("username") username : String
    ) : Call<ArrayList<User>>
}