package com.karthik.demo.networking;


import com.karthik.demo.app.omdb.model.OmdbModel;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by karthik on 30/08/16.
 */
public interface RetrofitService {

    // Albums (getting albums data): http://www.omdbapi.com/?t=hellboy&plot=short&type=movie
    @GET("?")
    Observable<OmdbModel> loadMovies(@Query("t") String movieTitle,@Query("plot") String plot,@Query("type") String type);

}
