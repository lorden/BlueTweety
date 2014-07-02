package com.diegolorden.apps.bluetweety;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.TwitterApi;

import android.content.Context;

import com.codepath.oauth.OAuthBaseClient;
import com.diegolorden.apps.bluetweety.models.User;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class TwitterClient extends OAuthBaseClient {
    public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class; // Change this
    public static final String REST_URL = "https://api.twitter.com/1.1"; // Change this, base API URL
    public static final String REST_CONSUMER_KEY = "N0WIu8GBLFe0yqMwkqWqJQ90x";       // Change this
    public static final String REST_CONSUMER_SECRET = "5UglgLymtHuXxyvqZ0dLf4WnmWLXratrRbFWO6mE7w4FhpN9CI"; // Change this
    public static final String REST_CALLBACK_URL = "oauth://bluetweety"; // Change this (here and in manifest)

    public TwitterClient(Context context) {
        super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
    }

    public void getHomeTimeline(AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("statuses/home_timeline.json");
        RequestParams params = new RequestParams();
        client.get(apiUrl, null, handler);
    }

    public void getHomeTimeline(Long max_id, AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("statuses/home_timeline.json");
        RequestParams params = new RequestParams();
        params.put("max_id", Long.toString(max_id));
        client.get(apiUrl, params, handler);
    }

    public void getUserTimeline(String screenName, Long maxId, AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("statuses/user_timeline.json");
        RequestParams params = new RequestParams();
        params.put("max_id", Long.toString(maxId));
        params.put("screen_name", screenName);
        client.get(apiUrl, params, handler);
    }
    public void getUserInfo(String screenName, AsyncHttpResponseHandler handler) {
        if (screenName == null || screenName.equals("")) {
            String apiUrl = getApiUrl("account/verify_credentials.json");
            client.get(apiUrl, null, handler);
        } else {
            String apiUrl = getApiUrl("users/show.json");
            RequestParams params = new RequestParams();
            params.put("screen_name", screenName);
            client.get(apiUrl, params, handler);
        }
    }

    public void postTweet(String status, AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("statuses/update.json");
        RequestParams params = new RequestParams();
        params.put("status", status);
        client.post(apiUrl, params, handler);

    }

    public void getMentionsTimeline(AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("statuses/mentions_timeline.json");
        RequestParams params = new RequestParams();
        client.get(apiUrl, null, handler);
    }

    public void getMentionsTimeline(Long max_id, AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("statuses/mentions_timeline.json");
        RequestParams params = new RequestParams();
        params.put("max_id", Long.toString(max_id));
        client.get(apiUrl, params, handler);
    }

    public void getUserTimeline(String screenName, AsyncHttpResponseHandler handler) {
        if (screenName == null || screenName.equals("")) {
            String apiUrl = getApiUrl("statuses/user_timeline.json");
            client.get(apiUrl, null, handler);
        } else {
            String apiUrl = getApiUrl("statuses/user_timeline.json");
            RequestParams params = new RequestParams();
            params.put("screen_name", screenName);
            client.get(apiUrl, params, handler);
        }
    }

    public void getUserBanner(User user, AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("users/profile_banner.json");
        RequestParams params = new RequestParams();
        params.put("user_id", Long.toString(user.getUid()));
        client.get(apiUrl, params, handler);
    }
}