package com.diegolorden.apps.bluetweety;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.content.Intent;
import android.os.Bundle;

import com.diegolorden.apps.bluetweety.fragments.HomeTimelineFragment;
import com.diegolorden.apps.bluetweety.fragments.MentionsFragment;
import com.diegolorden.apps.bluetweety.listeners.FragmentTabListener;

import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class TimelineActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_timeline);
        setupTabs();
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

//    public void onRefreshClick(MenuItem mi) {
//        populateTimeline();
//    }

    private void setupTabs() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowTitleEnabled(true);

        Tab homeTab = actionBar
                .newTab()
                .setText("Home")
                .setTag("HomeTimelineFragment")
                .setTabListener(
                        new FragmentTabListener<HomeTimelineFragment>(R.id.flContainer, this, "home",
                                HomeTimelineFragment.class));

        actionBar.addTab(homeTab);
        actionBar.selectTab(homeTab);

        Tab mentionsTab = actionBar
                .newTab()
                .setText("Mentions")
                .setTag("MentionsTimelineFragment")
                .setTabListener(
                        new FragmentTabListener<MentionsFragment>(R.id.flContainer, this, "mention",
                                MentionsFragment.class));

        actionBar.addTab(mentionsTab);
    }

    public void onProfileView(MenuItem mi) {
        Intent i = new Intent(this, ProfileActivity.class);
        startActivity(i);
    }

    public void showProfile(View v) {
        RelativeLayout rlParent = (RelativeLayout) v.getParent();
        TextView tvTweetUserHandle = (TextView) rlParent.findViewById(R.id.tvTweetUserHandle);
        String screenName = tvTweetUserHandle.getText().toString().substring(1);
        Intent i = new Intent(this, ProfileActivity.class);
        i.putExtra("screenName", screenName);
        startActivity(i);
    }

}
