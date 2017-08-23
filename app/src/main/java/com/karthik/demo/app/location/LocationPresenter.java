package com.karthik.demo.app.location;

import java.lang.ref.WeakReference;

/**
 * Created by karthik on 23/8/17.
 */

public class LocationPresenter implements Location_MVP.PresenterForView, Location_MVP.PresenterForInteractor {


    private WeakReference<Location_MVP.View> mView;
    Location_MVP.Interactor mInteractor;


    public LocationPresenter(Location_MVP.View view) {
        mView = new WeakReference<>(view);
        mInteractor = new LocationInteractor(this);
    }

    private Location_MVP.View getView() throws NullPointerException {
        if (mView != null) {
            return mView.get();
        } else {
            throw new NullPointerException("View is unavailable");
        }
    }


    @Override
    public void loadNearByPlaces(double lat,double lon) {
        getView().showProgress();
        mInteractor.loadingNearByPlaces(lat,lon);
    }

    @Override
    public void cancelGallery() {

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

    }

    @Override
    public void onNetworkError() {
        getView().hideProgress();
        getView().showNetworkError();
    }

    @Override
    public void onTimeOutError() {
        getView().hideProgress();
        getView().showNetworkError();
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
}
