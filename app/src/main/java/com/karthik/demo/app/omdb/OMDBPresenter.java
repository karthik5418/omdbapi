package com.karthik.demo.app.omdb;

import java.lang.ref.WeakReference;

/**
 * Created by karthik on 4/1/17.
 */

public class OMDBPresenter implements OMDB_MVP.PresenterForView, OMDB_MVP.PresenterForInteractor {


    private WeakReference<OMDB_MVP.View> mView;
    OMDB_MVP.Interactor mInteractor;


    public OMDBPresenter(OMDB_MVP.View view) {
        mView = new WeakReference<>(view);
        mInteractor = new OMDBInteractor(this);
    }

    private OMDB_MVP.View getView() throws NullPointerException {
        if (mView != null) {
            return mView.get();
        } else {
            throw new NullPointerException("View is unavailable");
        }
    }

    @Override
    public void loadMovies(Object object) {
        getView().showProgress();
        mInteractor.loadingMovies(object);
    }

    @Override
    public void setSearchString(String searchString) {
        mInteractor.settingSearchString(searchString);
    }

    @Override
    public void setSearchType(int position) {
        mInteractor.settingSearchType(position);
    }

    @Override
    public void cancelGallery() {
        mInteractor.unSubscribeObservers();
    }


    @Override
    public void onNetworkError() {

        getView().hideProgress();
        getView().showNetworkError();

    }

    @Override
    public void onTimeOutError() {

        getView().hideProgress();
        getView().showTimeOutError();

    }


    @Override
    public void onServerError() {

        getView().hideProgress();
        getView().showServerError();

    }

    @Override
    public void onFailure(Object object) {

        getView().hideProgress();
        getView().showFailure(object);

    }

    @Override
    public void onSuccess(Object object) {

        getView().hideProgress();
        getView().showSuccess(object);

    }

    @Override
    public void onNoResult(Object object) {
        getView().hideProgress();
        getView().showNoResult(object);
    }


    @Override
    public void onStart() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onRestart() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {
        mInteractor.unSubscribeObservers();
        mView = null;
    }
}
