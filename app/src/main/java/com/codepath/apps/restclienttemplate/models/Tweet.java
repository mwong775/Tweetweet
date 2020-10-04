package com.codepath.apps.restclienttemplate.models;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class Tweet {
    public static final String TAG = "Tweet";

    public String body;
    public String createdAt;
    public long id;
    public User user;

    public static Tweet fromJson(JSONObject jsonObject) throws JSONException {
        Tweet tweet = new Tweet();
        tweet.body = jsonObject.getString("text");
        tweet.createdAt = jsonObject.getString("created_at");
        tweet.id = jsonObject.getLong("id");
        tweet.user = User.fromJson(jsonObject.getJSONObject("user"));
        return tweet;
    }

    public static List<Tweet> fromJsonArray(JSONArray jsonArray) throws JSONException {
        List<Tweet> tweets = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            tweets.add(fromJson(jsonArray.getJSONObject(i)));
        }
        return tweets;
    }

    public static String getElapsed(String createdAt) {
        try {
            Date date = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy" , Locale.US).parse(createdAt);
            Log.d(TAG, "Parsed date: " + date + " -> " + date.toString());
            Date now = new Date();
            long diffInMillies = Math.abs(now.getTime() - date.getTime());
            long diff = TimeUnit.MINUTES.convert(diffInMillies, TimeUnit.MILLISECONDS);
            if (diff > 60) {
                // convert to hours
                diff /= 60;
                if (diff >= 24) {
                    // convert to days
                    diff /= 24;
                    return Long.toString(diff) + "d";
                }
                return Long.toString(diff) + "h";
            }
            // return elapsed in minutes
            return Long.toString(diff) + "m";
        } catch (ParseException e) {
            Log.e(TAG, "Failed to parse date" + createdAt);
            e.printStackTrace();
            return "Date error :(";
        }
    }
}
