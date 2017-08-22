package com.karthik.demo.networking;


import com.karthik.demo.app.omdb.model.FeedModel;
import com.karthik.demo.app.omdb.model.OmdbModel;

import java.util.HashMap;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by karthik on 30/08/16.
 */
public interface RetrofitService {

    // Albums (getting albums data): http://www.omdbapi.com/?t=hellboy&plot=short&type=movie
    @GET("?")
    Observable<OmdbModel> loadMovies(@Query("t") String movieTitle, @Query("plot") String plot, @Query("type") String type);


    // Feed list      http://www.techmorphosis-development.com/procure_meet/mobile1.0/web/viewPostList.php
    @POST("viewPostList.php")
    @FormUrlEncoded
    Observable<FeedModel> getFeedList(@FieldMap HashMap<String, String> map);


}
