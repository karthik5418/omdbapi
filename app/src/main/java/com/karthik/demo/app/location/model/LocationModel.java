
package com.karthik.demo.app.location.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

@SuppressWarnings("unused")
public class LocationModel {

    @SerializedName("html_attributions")
    private List<Object> mHtmlAttributions;
    @SerializedName("next_page_token")
    private String mNextPageToken;
    @SerializedName("results")
    private List<Result> mResults;
    @SerializedName("status")
    private String mStatus;

    public List<Object> getHtmlAttributions() {
        return mHtmlAttributions;
    }

    public void setHtmlAttributions(List<Object> html_attributions) {
        mHtmlAttributions = html_attributions;
    }

    public String getNextPageToken() {
        return mNextPageToken;
    }

    public void setNextPageToken(String next_page_token) {
        mNextPageToken = next_page_token;
    }

    public List<Result> getResults() {
        return mResults;
    }

    public void setResults(List<Result> results) {
        mResults = results;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

}
