package com.jvetter2.movieclue;


import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

//https://api.themoviedb.org/3/movie/upcoming?api_key=39eded7c924a15985f6ce7c11be25a40

public interface MovieClueInterface {
    @GET("upcoming?api_key=39eded7c924a15985f6ce7c11be25a40")
    Observable<MovieResponse> getMovie();
    //Call<List<Change>> loadChanges(@Query("q") String status);
}
