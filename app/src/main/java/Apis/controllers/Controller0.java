package Apis.controllers;

import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Controller0 {
    public static String API_BASE_URL = "";
    Controller0() {
    }
    public static Api getApi() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        //Set your desired log level
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder mOkHttpClient = new OkHttpClient.Builder();
        mOkHttpClient.connectTimeout(50, TimeUnit.SECONDS).writeTimeout(2, TimeUnit.MINUTES).readTimeout(50, TimeUnit.SECONDS);
        mOkHttpClient.hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String s, SSLSession sslSession) {
                return true;
            }
        });
        Retrofit.Builder mBuilder = new Retrofit.Builder().baseUrl(API_BASE_URL).addConverterFactory(ScalarsConverterFactory.create()).addConverterFactory(GsonConverterFactory.create());
        Retrofit mRetrofit = mBuilder.client(mOkHttpClient.addInterceptor(httpLoggingInterceptor).build()).build();
        //Create a very simple rest adapter which points the github api endpoints
        Api mClient = mRetrofit.create(Api.class);
        return mClient;
    }
}
