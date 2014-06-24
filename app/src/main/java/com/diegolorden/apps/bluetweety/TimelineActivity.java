package com.diegolorden.apps.bluetweety;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.diegolorden.apps.bluetweety.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import org.json.JSONArray;

import java.util.ArrayList;

public class TimelineActivity extends Activity {

    private TwitterClient client;
    private ArrayList<Tweet> tweets;
    private TweetArrayAdapter aTweets;
    private ListView lvTweets;
    private Long maxId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        client = TwitterApp.getRestClient();
        populateTimeline();
        lvTweets = (ListView) findViewById(R.id.lvTweets);
        tweets = new ArrayList<Tweet>();
        aTweets = new TweetArrayAdapter(this, tweets);
        lvTweets.setAdapter(aTweets);

        lvTweets.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                loadOlderTimeline();
            }
        });
    }

    public void loadOlderTimeline() {
        client.getHomeTimeline(maxId, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONArray json) {
                ArrayList<Tweet> newTweets = Tweet.fromJSONArray(json);
                aTweets.addAll(newTweets);
                maxId = newTweets.get(newTweets.size() - 1).getUid();
            }

            @Override
            public void onFailure(Throwable e, String s) {
                Log.d("debug", e.toString());
                Log.d("debug", s.toString());
            }
        });
    }

    public void populateTimeline() {
        client.getHomeTimeline(new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(JSONArray json) {
                ArrayList<Tweet> newTweets = Tweet.fromJSONArray(json);
                aTweets.addAll(newTweets);
                maxId = newTweets.get(newTweets.size() - 1).getUid();
            }

            @Override
            public void onFailure(Throwable e, String s) {
                Log.d("debug", e.toString());
                Log.d("debug", s.toString());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.timeline, menu);
        return true;
    }

    public void onComposeClick(MenuItem mi) {
        Intent i = new Intent(this, ComposeActivity.class);
        startActivity(i);
    }

}
