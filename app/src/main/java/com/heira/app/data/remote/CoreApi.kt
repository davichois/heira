package com.heira.app.data.remote

import com.heira.app.data.remote.dto.ResponseApi
import com.heira.app.data.remote.dto.RouteCreationDTO
import com.heira.app.data.remote.dto.RouteDTO
import com.heira.app.data.remote.dto.RouteFavoriteDTO
import com.heira.app.data.remote.dto.UserDTO
import com.heira.app.data.remote.dto.UserPostCreation
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface CoreApi {

    @GET("api/route")
    suspend fun getRouteByTag(@Query("tag") tag: String): ResponseApi<List<RouteDTO>>

    @GET("api/route")
    suspend fun getRouteById(@Query("id") id: String): ResponseApi<RouteDTO>

    @GET("api/user/{idUser}/likes")
    suspend fun getRoutesLikedUser(@Path("idUser") idUser: String): ResponseApi<List<RouteFavoriteDTO>>

    @GET("api/user")
    suspend fun getUsername(@Query("username") username: String): ResponseApi<UserDTO>

    @POST("api/route/{idRoute}/like")
    suspend fun postLikeRouteByCodeUser(@Query("hId") code: String): ResponseApi<RouteFavoriteDTO>

    @POST("api/user")
    suspend fun postCreationUser(@Body user: UserPostCreation): ResponseApi<UserDTO>

    @POST("api/route")
    suspend fun postCreationRoute(@Body route: RouteCreationDTO): ResponseApi<RouteDTO>

}