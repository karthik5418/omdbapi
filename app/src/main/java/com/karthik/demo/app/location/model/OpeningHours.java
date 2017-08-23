
package com.karthik.demo.app.location.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;


@SuppressWarnings("unused")
public class OpeningHours {

    @SerializedName("open_now")
    private Boolean mOpenNow;
    @SerializedName("weekday_text")
    private List<Object> mWeekdayText;

    public Boolean getOpenNow() {
        return mOpenNow;
    }

    public void setOpenNow(Boolean open_now) {
        mOpenNow = open_now;
    }

    public List<Object> getWeekdayText() {
        return mWeekdayText;
    }

    public void setWeekdayText(List<Object> weekday_text) {
        mWeekdayText = weekday_text;
    }

}
