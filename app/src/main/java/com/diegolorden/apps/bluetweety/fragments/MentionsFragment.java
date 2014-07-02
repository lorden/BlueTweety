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

public class MentionsFragment extends TweetListFragment {


    public MentionsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        populateMentions();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                loadMoreMentions();
            }
        });
        return v;
    }

    public void loadMoreMentions() {
        showProgressBar();
        client.getMentionsTimeline(getMaxId(), new JsonHttpResponseHandler() {
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

    public void populateMentions() {
        showProgressBar();
        client.getMentionsTimeline(new JsonHttpResponseHandler() {
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


}
