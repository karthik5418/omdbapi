package com.karthik.demo.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by karthik on 6/4/17.
 */

public class DateUtils {

    public static String getTimeAgo(String timeFormat) throws Exception {

        SimpleDateFormat fromDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dateServer = fromDateFormat.parse(timeFormat);
        long time = dateServer.getTime();


        SimpleDateFormat toDateFormat = new SimpleDateFormat("MMM dd, yyyy");
        String userDate = toDateFormat.format(dateServer);


        final int SECOND_MILLIS = 1000;
        final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
        final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
        final int DAY_MILLIS = 24 * HOUR_MILLIS;


        if (time < 1000000000000L) {
            // if timestamp given in seconds, convert to millis
            time *= 1000;
        }

        //long now = getCurrentTime(ctx);
        long now = System.currentTimeMillis();
        if (time > now || time <= 0) {
            return null;
        }

        // TODO: localize
        final long diff = now - time;
        if (diff < MINUTE_MILLIS) {
            //return "just now";
            return "Today";
        } else if (diff < 2 * MINUTE_MILLIS) {
            //return "a minute ago";
            return "Today";
        } else if (diff < 50 * MINUTE_MILLIS) {
            //return diff / MINUTE_MILLIS + " minutes ago";
            return "Today";
        } else if (diff < 90 * MINUTE_MILLIS) {
            //return "an hour ago";
            return "Today";
        } else if (diff < 24 * HOUR_MILLIS) {
            // return diff / HOUR_MILLIS + " hours ago";
            return "today";
        } else if (diff < 48 * HOUR_MILLIS) {
            return "Yesterday";
        } else {
            //return diff / DAY_MILLIS + " days ago";
            return userDate;
        }
    }

    public static String formatReviewDate(String strDate) {

        String formattedDate = "";
        try {

            // "Monday, September 12, 2016",
            SimpleDateFormat fromDate = new SimpleDateFormat("EEEE, MMM dd, yyyy");
            Date date = fromDate.parse(strDate);


            SimpleDateFormat toDate = new SimpleDateFormat("MMM dd, yyyy");
            formattedDate = toDate.format(date);


        } catch (ParseException e) {
            e.printStackTrace();
        }

        return formattedDate;

    }

    public static String formatChatDate(String strDate) {

        String formattedDate = "";
        try {

            // "2017-04-10 17:12:10",
            SimpleDateFormat fromDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = fromDate.parse(strDate);


            SimpleDateFormat toDate = new SimpleDateFormat("MMM dd, yyyy hh:mm a");
            formattedDate = toDate.format(date);


        } catch (ParseException e) {
            e.printStackTrace();
        }

        return formattedDate;

    }

    public static String formatAutoSaveNotesDate() {
        String formattedDate = "";
        try {
            //SimpleDateFormat outputFormat = new SimpleDateFormat("MMM dd, yyyy 'at' h:mm a");
            SimpleDateFormat outputFormat = new SimpleDateFormat("'Last Updated at' h:mm:ss a");
            formattedDate = outputFormat.format(new Date());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return formattedDate;
    }

    public static String formatNowDate() {
        String formattedDate = "";
        try {
            // 2017-04-13 11:39:59
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
            formattedDate = outputFormat.format(new Date());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return formattedDate;
    }
}
