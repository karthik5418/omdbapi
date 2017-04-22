/*
 *
 *  * Copyright (C) 2014 Antonio Leiva Gordillo.
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *      http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package com.karthik.demo.app.omdb;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.karthik.demo.R;
import com.karthik.demo.app.omdb.model.OmdbModel;
import com.karthik.demo.listener.OnTryAgainListener;
import com.karthik.demo.util.CommonFunction;
import com.karthik.demo.util.ErrorUtil;

import java.util.ArrayList;
import java.util.List;


public class OMDBActivity extends AppCompatActivity implements OMDB_MVP.View, View.OnClickListener, OnTryAgainListener, AdapterView.OnItemSelectedListener {

    //Views
    private RecyclerView rvOmdb;
    private View inLoader;
    private View inError;
    private Dialog dialog;
    private ImageView ivBack;
    private TextView tvTitle;
    private Spinner spinnerType;
    private ImageView ivSearch;
    private ImageView ivCancel;
    private TextView tvSearch;
    private EditText etSearch;


    // ZoomPresenter
    private OMDB_MVP.PresenterForView mPresenter;

    // Display
    private DisplayMetrics metrics;
    private float mHeight;
    private float mWidth;

    // booleanprivate boolean isPasswordShown = false;

    // List, Adapter
    private OMDBAdapter adapter;
    private List<OmdbModel> list;
    private OmdbModel model;

    // Parent type
    private int mParentType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_omdb);
        findViews();
        init();
        setOnClick();
    }


    private void findViews() {
        rvOmdb = (RecyclerView) findViewById(R.id.rv_omdb);
        inLoader = findViewById(R.id.in_loader);
        inError = findViewById(R.id.in_error);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        spinnerType = (Spinner) findViewById(R.id.sp_type);
        ivSearch = (ImageView) findViewById(R.id.iv_search);
        ivCancel = (ImageView) findViewById(R.id.iv_cancel);
        tvSearch = (TextView) findViewById(R.id.tv_search);
        etSearch = (EditText) findViewById(R.id.et_search);
        dialog = CommonFunction.getCustomProgress(this, "", true);
    }

    private void init() {
        //Getting device DisplayMetrics.
        metrics = getApplicationContext().getResources().getDisplayMetrics();
        mHeight = metrics.heightPixels;
        mWidth = metrics.widthPixels;

        // Operations on views
        tvTitle.setText(getString(R.string.movies));
        rvOmdb.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();

        mPresenter = new OMDBPresenter(this);

        // Creating adapter for spinner
        List<String> type = new ArrayList<>(3);
        type.add("Movie");
        type.add("Series");
        type.add("Episode");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, type);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(dataAdapter);
        spinnerType.setOnItemSelectedListener(this);
    }


    private void setOnClick() {
        tvSearch.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_search:
                CommonFunction.hideKeyboard(this, tvSearch);
                ErrorUtil.hideInsetError(rvOmdb, inError);
                mPresenter.setSearchString(etSearch.getText().toString().trim());
                mPresenter.loadMovies(null);
                break;
        }
    }


    @Override
    public void showProgress() {
        ErrorUtil.showInsetLoader(rvOmdb, inLoader);
    }

    @Override
    public void hideProgress() {
        ErrorUtil.hideInsetLoader(rvOmdb, inLoader);
    }


    @Override
    public void showNetworkError() {

        ErrorUtil.showNetworkError(rvOmdb, inError, this);
    }

    @Override
    public void showTimeOutError() {

        ErrorUtil.showNetworkError(rvOmdb, inError, this);
    }

    @Override
    public void showServerError() {

        ErrorUtil.showServerError(rvOmdb, inError, this);
    }

    @Override
    public void showFailure(Object object) {

        ErrorUtil.showServerError(rvOmdb, inError, this);
    }

    @Override
    public void showNoResult(Object object) {
        ErrorUtil.showNoResultsError(rvOmdb, inError, (String) object, R.drawable.ic_error);
    }


    @Override
    public void showSuccess(Object object) {
        model = (OmdbModel) object;
        list.add(model);
        adapter = new OMDBAdapter(this, list, mHeight);
        rvOmdb.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPresenter.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPresenter.onStop();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mPresenter.onRestart();
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onTryAgain() {
        CommonFunction.hideKeyboard(this, tvSearch);
        ErrorUtil.hideInsetError(rvOmdb, inError);
        mPresenter.loadMovies(null);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        mPresenter.setSearchType(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
