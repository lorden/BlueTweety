package com.diegolorden.apps.bluetweety.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.activeandroid.util.Log;
import com.diegolorden.apps.bluetweety.listeners.EndlessScrollListener;
import com.diegolorden.apps.bluetweety.R;
import com.diegolorden.apps.bluetweety.TweetArrayAdapter;
import com.diegolorden.apps.bluetweety.TwitterApp;
import com.diegolorden.apps.bluetweety.TwitterClient;
import com.diegolorden.apps.bluetweety.models.Tweet;

import java.util.ArrayList;

public class TweetListFragment extends Fragment {

    private ArrayList<Tweet> tweets;
    protected TweetArrayAdapter aTweets;
    protected ListView lvTweets;
    protected Long maxId;
    protected TwitterClient client;

    public TweetListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tweets = new ArrayList<Tweet>();
        aTweets = new TweetArrayAdapter(getActivity(), tweets);
        client = TwitterApp.getRestClient();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_tweet_list, container, false);
        lvTweets = (ListView) v.findViewById(R.id.lvTweets);
        lvTweets.setAdapter(aTweets);

        return v;
    }

    public void addAll(ArrayList<Tweet> tweets) {
        aTweets.addAll(tweets);
    }

    public void clear() {
        aTweets.clear();
    }

    public void setMaxId(Long id) {
        maxId = id;
    }

    public Long getMaxId() {
        return maxId;
    }

    public void setOnScrollListener(EndlessScrollListener listener) {
        lvTweets.setOnScrollListener(listener);
    }

    public void populateTweets(ArrayList<Tweet> tweets) {
        clear();
        addAll(tweets);
        setMaxId(tweets.get(tweets.size() - 1).getUid());
    }

    public void showProgressBar() {
        getActivity().setProgressBarIndeterminateVisibility(true);
    }

    // Should be called when an async task has finished
    public void hideProgressBar() {
       getActivity().setProgressBarIndeterminateVisibility(false);
    }

}
