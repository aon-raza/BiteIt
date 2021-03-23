package Apis.controllers;

import Apis.models.user;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface Api {

    @POST("api/v1/auth/login")
    Observable<Object> login(@Body user body);
}
