package com.example.cupcake.network

import okhttp3.Request
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface LoginAPI {


    @POST("login")
    suspend fun loginUser( @Body requestBody: LoginBody): Response<LoginUser>

    @POST("register")
    suspend fun registerUser(@Body requestBody: LoginBody): Response<RegisterUser>

    @Headers(
        "Authorization: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiI2MjdhMzFkMDY4NGM3OGM1MjE5MWNiZTIiLCJ1c2VybmFtZSI6ImNoYXJsb3R0ZSIsImFjY291bnRObyI6Ijg0NjAtNTI0LTk1NDAiLCJpYXQiOjE2NTI0Mjg5MTIsImV4cCI6MTY1MjQzOTcxMn0.HntFDBWBjk9YnDtkcnI3tZorTOMhsHj8MtYOMlpTeBs"
    )
    @POST("transfer")
    suspend fun transferFunds(@Body requestBody: TransferBody ): Response<TransferFund>

    @Headers(
        "Authorization: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiI2MjdhMzFkMDY4NGM3OGM1MjE5MWNiZTIiLCJ1c2VybmFtZSI6ImNoYXJsb3R0ZSIsImFjY291bnRObyI6Ijg0NjAtNTI0LTk1NDAiLCJpYXQiOjE2NTI0Mjg5MTIsImV4cCI6MTY1MjQzOTcxMn0.HntFDBWBjk9YnDtkcnI3tZorTOMhsHj8MtYOMlpTeBs"
    )
    @GET("payees")
    suspend fun getPayee (): Response<GetPayee>

    @Headers(
        "Authorization: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiI2MjdhMzFkMDY4NGM3OGM1MjE5MWNiZTIiLCJ1c2VybmFtZSI6ImNoYXJsb3R0ZSIsImFjY291bnRObyI6Ijg0NjAtNTI0LTk1NDAiLCJpYXQiOjE2NTI0Mjg5MTIsImV4cCI6MTY1MjQzOTcxMn0.HntFDBWBjk9YnDtkcnI3tZorTOMhsHj8MtYOMlpTeBs"
    )
    @GET("transactions")
    suspend fun getTransactions (): Response<UserTransaction>

    @Headers(
        "Authorization: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiI2MjdhMzFkMDY4NGM3OGM1MjE5MWNiZTIiLCJ1c2VybmFtZSI6ImNoYXJsb3R0ZSIsImFjY291bnRObyI6Ijg0NjAtNTI0LTk1NDAiLCJpYXQiOjE2NTI0Mjg5MTIsImV4cCI6MTY1MjQzOTcxMn0.HntFDBWBjk9YnDtkcnI3tZorTOMhsHj8MtYOMlpTeBs"
    )
    @GET("balance")
    suspend fun getBalance( @HeaderMap headers: Map<String, String>): Response<UserBalance>
}