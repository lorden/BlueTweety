package com.diegolorden.apps.bluetweety.fragments;



import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.activeandroid.util.Log;
import com.diegolorden.apps.bluetweety.listeners.EndlessScrollListener;
import com.diegolorden.apps.bluetweety.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;

import java.util.ArrayList;

public class UserTimelineFragment extends TweetListFragment {

    private String screenName;

    public UserTimelineFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                loadMoreUserTweets();
            }
        });
        return v;
    }

    public void populateTimeline(String screenName) {
        showProgressBar();
        this.screenName = screenName;
        client.getUserTimeline(screenName, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONArray json) {
                populateTweets(Tweet.fromJSONArray(json));
                hideProgressBar();
            }

            @Override
            public void onFailure(Throwable e, String s) {
                Log.d("debug", e.toString());
                Log.d("debug", s.toString());
            }
        });
    }

    public void loadMoreUserTweets() {
        showProgressBar();
        client.getUserTimeline(this.screenName, getMaxId(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONArray json) {
                ArrayList<Tweet> newTweets = Tweet.fromJSONArray(json);
                addAll(newTweets);
                setMaxId(newTweets.get(newTweets.size() - 1).getUid());
                hideProgressBar();
            }

            @Override
            public void onFailure(Throwable e, String s) {
                Log.d("debug", e.toString());
                Log.d("debug", s.toString());
            }
        });
    }


}
