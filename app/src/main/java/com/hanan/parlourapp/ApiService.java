package com.hanan.parlourapp;



import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {
    @FormUrlEncoded
    @POST("signup.php")
    Call<ApiResponse> signup(
            @Field("name") String name,
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("loginn.php")
    Call<ApiResponse> login(
            @Field("email") String email,
            @Field("password") String password
    );
}
