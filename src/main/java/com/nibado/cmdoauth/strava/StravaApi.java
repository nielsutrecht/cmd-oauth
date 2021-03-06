package com.nibado.cmdoauth.strava;

import com.nibado.cmdoauth.strava.model.*;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

import java.util.List;

public interface StravaApi {

    @GET("api/v3/athlete")
    Call<Athlete> getAthlete(@Header("Authorization") BearerToken token);

    @GET("api/v3/athlete/activities")
    Call<List<Activity>> getAthleteActivities(@Header("Authorization") BearerToken token);

    @POST("oauth/token")
    Call<TokenExchangeResponse> tokenExchange(@Body TokenExchangeRequest user);

    static StravaApi create() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.strava.com/")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        return retrofit.create(StravaApi.class);
    }
}
