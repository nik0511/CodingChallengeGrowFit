package com.example.ramon_sifer.codingchallengegrowfit.interfaces;
import com.example.ramon_sifer.codingchallengegrowfit.modelclasses.ListDataResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
public interface ApiCall {


    @Headers({"Content-type: text/html"})
    @GET("59b6a65a0f0000e90471257d")
    Call<ListDataResponse> getData();

}