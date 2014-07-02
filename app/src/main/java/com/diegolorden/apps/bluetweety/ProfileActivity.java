package com.diegolorden.apps.bluetweety;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.activeandroid.util.Log;
import com.diegolorden.apps.bluetweety.fragments.UserTimelineFragment;
import com.diegolorden.apps.bluetweety.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONException;
import org.json.JSONObject;

public class ProfileActivity extends FragmentActivity {

    User user;
    UserTimelineFragment userTimelineFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_profile);
        String screenName = getIntent().getStringExtra("screenName");
        loadProfileInfo(screenName);
        userTimelineFragment = (UserTimelineFragment) getSupportFragmentManager().findFragmentById(R.id.fgUserTimeline);
        userTimelineFragment.populateTimeline(screenName);
    }

    public void loadProfileInfo(String screenName){
        showProgressBar();
        TwitterApp.getRestClient().getUserInfo(screenName, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONObject json) {
                user = User.fromJSON(json);
                TwitterApp.getRestClient().getUserBanner(user, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(JSONObject json) {
                        try {
                            String banner = json.getJSONObject("sizes").getJSONObject("mobile_retina").get("url").toString();
                            user.setProfileBackgroundUrl(banner);
                            populateProfileHeader(user);
                            hideProgressBar();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Throwable throwable, String s) {
                        Log.d("debug", throwable.toString());
                        Log.d("debug", s);
                    }
                });
            }
        });
    }

    public void populateProfileHeader(User u) {
        TextView tvProfileName = (TextView) findViewById(R.id.tvProfileName);
        tvProfileName.setText(u.getName());
        TextView tvProfileScreenName = (TextView) findViewById(R.id.tvProfileDescription);
        tvProfileScreenName.setText(u.getDescription());
        getActionBar().setTitle(u.getHandle());
        ImageView ivProfileImage = (ImageView) findViewById(R.id.ivProfilePicture);
        ImageLoader.getInstance().displayImage(u.getProfileImageUrl(), ivProfileImage);
        ImageView ivProfileBackground = (ImageView) findViewById(R.id.ivProfileBackground);
        ImageLoader.getInstance().displayImage(user.getProfileBackgroundUrl(), ivProfileBackground);
        TextView tvTweetCount = (TextView) findViewById(R.id.tvProfileTweets);
        tvTweetCount.setText(Integer.toString(user.getTweetCount()) + "\r\nTWEETS");
        TextView tvFollowingCount = (TextView) findViewById(R.id.tvProfileFollowing);
        tvFollowingCount.setText(Integer.toString(user.getFollowingCount()) + "\r\nFOLLOWING");
        TextView tvFollowersCount = (TextView) findViewById(R.id.tvProfileFollowers);
        tvFollowersCount.setText(Integer.toString(user.getFollowersCount()) + "\r\nFOLLOWERS");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void showProfile(View v) {
        // Not allowed to view profile in this activity
    }


    public void showProgressBar() {
        setProgressBarIndeterminateVisibility(true);
    }

    public void hideProgressBar() {
        setProgressBarIndeterminateVisibility(false);
    }
}
