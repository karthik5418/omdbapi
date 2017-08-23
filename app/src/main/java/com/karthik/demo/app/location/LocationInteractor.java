package com.karthik.demo.app.location;

import com.karthik.demo.MyApp;
import com.karthik.demo.app.location.model.LocationModel;
import com.karthik.demo.constants.Constants;
import com.karthik.demo.constants.ServerStatus;
import com.karthik.demo.networking.RetrofitService;
import com.karthik.demo.util.CommonFunction;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.content.ContentValues.TAG;

/**
 * Created by karthik on 23/8/17.
 */

public class LocationInteractor implements Location_MVP.Interactor {


    private Location_MVP.PresenterForInteractor mPresenter;
    public RetrofitService retrofitService;

    private Subscription subscription;

    public LocationInteractor(LocationPresenter mPresenter) {
        this.mPresenter = mPresenter;
        retrofitService = MyApp.getRetrofitService();
    }


    @Override
    public void loadingNearByPlaces(double lat, double lon) {
        String url = Constants.PLACES_NEAR_BY
                + String.valueOf(lat)
                + ","
                + String.valueOf(lon)
                + Constants.RANK_BY
                + Constants.PLACES_RADIUS
                + Constants.PLACES_TYPES
                + Constants.PLACES_SENSOR
                + Constants.PLACES_KEY;


        Observable<LocationModel> observable = retrofitService.getNearByRestaurants(url);

        subscription = observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<LocationModel>() {
                    @Override
                    public void onCompleted() {
                        CommonFunction.printDebug(TAG, "completed");
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof UnknownHostException || e instanceof ConnectException) {
                            mPresenter.onNetworkError();
                        } else if (e instanceof SocketTimeoutException) {
                            mPresenter.onTimeOutError();
                        } else {
                            mPresenter.onServerError();
                        }
                    }

                    @Override
                    public void onNext(LocationModel model) {
                        if (model.getStatus().equals(ServerStatus.OK)) {
                            mPresenter.onSuccess(model.getResults());
                            return;
                        }
                        if (model.getStatus().equals(ServerStatus.FAILURE)) {
                            mPresenter.onFailure(model.getStatus());
                            return;
                        }
                        mPresenter.onServerError();
                    }
                });


    }

    @Override
    public void unSubscribeObservers() {

    }


}
