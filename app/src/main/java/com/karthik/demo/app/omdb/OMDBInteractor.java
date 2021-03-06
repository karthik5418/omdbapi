package com.karthik.demo.app.omdb;

import com.karthik.demo.MyApp;
import com.karthik.demo.app.omdb.model.FeedModel;
import com.karthik.demo.constants.ServerStatus;
import com.karthik.demo.networking.RetrofitService;
import com.karthik.demo.util.CommonFunction;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.HashMap;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by karthik on 4/1/17.
 */

public class OMDBInteractor implements OMDB_MVP.Interactor {

    private static final String TAG = "Login";

    private OMDB_MVP.PresenterForInteractor mPresenter;
    public RetrofitService retrofitService;

    // Subscription
    private Subscription subscriptionMovies;
    private String mSearchType = "";
    private String mSearchString = "";

    public OMDBInteractor(OMDBPresenter mPresenter) {
        this.mPresenter = mPresenter;
        retrofitService = MyApp.getRetrofitService();
    }

    @Override
    public void loadingMovies(Object object) {


            callWebService();


    }

    @Override
    public void settingSearchString(String searchString) {
        mSearchString = searchString;
    }

    @Override
    public void settingSearchType(int position) {
        if (position == 0) {
            mSearchType = "movie";
        } else if (position == 1) {
            mSearchType = "series";
        } else {
            mSearchType = "episode";
        }
    }

    @Override
    public void unSubscribeObservers() {
        if (subscriptionMovies != null && !subscriptionMovies.isUnsubscribed()) {
            subscriptionMovies.unsubscribe();
        }
    }


    private void callWebService() {

        HashMap<String, String> map = new HashMap<>();
        map.put("os", "Android");
        map.put("make", "Moto");
        map.put("model", "G4 plus");
        map.put("userId", "97");
        map.put("token", "0e16ad8d450d6b943160cd2ffb79f4ee");
        map.put("currentListSize", "0");


        Observable<FeedModel> observable = retrofitService.getFeedList(map);

        subscriptionMovies = observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<FeedModel>() {
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
                    public void onNext(FeedModel model) {
                        if (model.getStatus().equals(ServerStatus.SUCCESS)) {
                            mPresenter.onSuccess(model.getResponse());
                            return;
                        }
                        if (model.getStatus().equals(ServerStatus.FAILURE)) {
                            mPresenter.onFailure(model.getMessage());
                            return;
                        }
                        mPresenter.onServerError();
                    }
                });


    }

}
