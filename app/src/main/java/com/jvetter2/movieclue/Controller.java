package com.jvetter2.movieclue;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Controller implements Callback<MovieResponse> {

    static final String BASE_URL = "https://api.themoviedb.org/3/movie/";

    public void start() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        MovieClueInterface gerritAPI = retrofit.create(MovieClueInterface.class);

        Call<MovieResponse> call = gerritAPI.getMovie();
        call.enqueue(this);

    }

    @Override
    public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
        if(response.isSuccessful()) {
            System.out.println("Hey");
            System.out.println(response);
//            List<MovieResponse> changesList = (List<MovieResponse>) response.body();
//            changesList.forEach(change -> System.out.println(change));
        } else {
            System.out.println(response.errorBody());
        }
    }

    @Override
    public void onFailure(Call<MovieResponse> call, Throwable t) {
        t.printStackTrace();
    }
}