package com.karthik.demo.app.omdb;

import com.karthik.demo.MyApp;
import com.karthik.demo.app.omdb.model.OmdbModel;
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

        if (!mSearchString.equals("")) {
            callWebService();
        }

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

        Observable<OmdbModel> observable = retrofitService.loadMovies(mSearchString, "short", mSearchType);

        subscriptionMovies = observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<OmdbModel>() {
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
                    public void onNext(OmdbModel model) {
                        if (model.getResponse().equalsIgnoreCase(ServerStatus.SUCCESS)) {
                            mPresenter.onSuccess(model);
                            return;
                        }
                        if (model.getResponse().equalsIgnoreCase(ServerStatus.NO_RESULT)) {
                            mPresenter.onNoResult(model.getError());
                            return;
                        }
                        mPresenter.onServerError();
                    }
                });


    }

}
