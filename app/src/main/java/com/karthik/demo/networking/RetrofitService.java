package com.karthik.demo.networking;


import com.karthik.demo.app.location.model.LocationModel;
import com.karthik.demo.app.omdb.model.FeedModel;

import java.util.HashMap;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by karthik on 30/08/16.
 */
public interface RetrofitService {


    // Feed list      http://www.techmorphosis-development.com/procure_meet/mobile1.0/web/viewPostList.php
    @POST("viewPostList.php")
    @FormUrlEncoded
    Observable<FeedModel> getFeedList(@FieldMap HashMap<String, String> map);


    @GET
    Observable<LocationModel> getNearByRestaurants(@Url String url);
}
