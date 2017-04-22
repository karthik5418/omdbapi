package com.karthik.demo.app.omdb;

/**
 * Created by karthik on 4/1/17.
 */

public interface OMDB_MVP {

    interface View {
        void showProgress();

        void hideProgress();

        void showNetworkError();

        void showTimeOutError();

        void showServerError();

        void showFailure(Object object);

        void showSuccess(Object object);

        void showNoResult(Object object);
    }

    interface PresenterForView {

        void loadMovies(Object object);

        void setSearchString(String searchString);
        void setSearchType(int position);

        void cancelGallery();

        void onStart();

        void onResume();

        void onRestart();

        void onPause();

        void onStop();

        void onDestroy();
    }

    interface PresenterForInteractor {


        void onNetworkError(); // If network problem during webservice call.

        void onTimeOutError(); // If network problem during webservice call.

        void onServerError(); // If any error occurs in server response.

        void onFailure(Object object); // If server sends failure response.

        void onSuccess(Object object); // If server sends success response.

        void onNoResult(Object object); // If no data from server.

    }

    interface Interactor {
        void loadingMovies(Object object);

        void settingSearchString(String searchString);

        void settingSearchType(int position);



        // unsubscribe all subscription to observers when Activity/Fragment is destroyed to prevent memory leaks.
        void unSubscribeObservers();
    }
}
