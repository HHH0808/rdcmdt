package com.example.cupcake.network

import retrofit2.Response
import retrofit2.http.*

interface LoginAPI {


    @POST("login")
    suspend fun loginUser( @Body requestBody: LoginBody): Response<LoginUser>

    @POST("register")
    suspend fun registerUser(@Body requestBody: LoginBody): Response<RegisterUser>


    @POST("transfer")
    suspend fun transferFunds(@Body requestBody: TransferBody, @Header("Authorization") Authorization: String? = "" ): Response<TransferFund>


    @GET("payees")
    suspend fun getPayee (@Header("Authorization") Authorization: String? = ""): Response<GetPayee>


    @GET("transactions")
    suspend fun getTransactions (@Header("Authorization") Authorization: String? = ""): Response<UserTransaction>


    @GET("balance")
    suspend fun getBalance(@Header("Authorization") Authorization: String? = ""): Response<UserBalance>
}