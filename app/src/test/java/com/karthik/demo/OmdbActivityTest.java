package com.karthik.demo;

import android.content.Context;

import com.karthik.demo.app.omdb.OMDBInteractor;
import com.karthik.demo.app.omdb.OMDBPresenter;
import com.karthik.demo.app.omdb.OMDB_MVP;
import com.karthik.demo.app.omdb.model.OmdbModel;
import com.karthik.demo.networking.RetrofitClient;
import com.karthik.demo.networking.RetrofitService;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashMap;

import rx.Observable;
import rx.Scheduler;
import rx.android.plugins.RxAndroidPlugins;
import rx.android.plugins.RxAndroidSchedulersHook;
import rx.schedulers.Schedulers;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by karthik on 24/4/17.
 */

@RunWith(MockitoJUnitRunner.class)
public class OmdbActivityTest {

    @Mock
    OMDB_MVP.View mView;

    @Mock
    OMDBInteractor mInteractor;

    @Mock
    Context mContext;

    OMDBPresenter mPresenter;

    RetrofitService retrofitService;

    Observable<OmdbModel> observable;

    HashMap<String, String> map = new HashMap<>();


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mPresenter = new OMDBPresenter(mView);
        retrofitService = RetrofitClient.getClient().create(RetrofitService.class);
        //observable = retrofitService.loadMovies();
        RxAndroidPlugins.getInstance().registerSchedulersHook(new RxAndroidSchedulersHook() {
            @Override
            public Scheduler getMainThreadScheduler() {
                return Schedulers.immediate();
            }
        });

    }

    @Test
    public void testNetworkError() {
        mPresenter.onNetworkError();
        verify(mView, times(1)).hideProgress();
        verify(mView, times(1)).showNoResult("NO INTERNET");
    }

    @Test
    public void testUnsubscriptionRetrofit() {
        verify(mPresenter, times(1)).onDestroy();
    }

    @After
    public void tearDown() {
        mPresenter = null;
        RxAndroidPlugins.getInstance().reset();
    }

}
