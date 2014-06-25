package com.diegolorden.apps.bluetweety;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.util.Log;

import com.diegolorden.apps.bluetweety.models.User;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONObject;

public class ComposeActivity extends Activity {

    private EditText etCompose;
    private MenuItem miRemainingChars;
    private ImageView ivUserPicture;
    private TextView tvUserName;
    private TextView tvUserHandle;
    private TwitterClient client;
    private Button btnTweet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
        client = TwitterApp.getRestClient();
        etCompose = (EditText) findViewById(R.id.etCompose);
        ivUserPicture = (ImageView) findViewById(R.id.ivUserPicture);
        tvUserName = (TextView) findViewById(R.id.tvUserName);
        tvUserHandle = (TextView) findViewById(R.id.tvUserHandle);
        btnTweet = (Button) findViewById(R.id.btnTweet);

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(etCompose, InputMethodManager.SHOW_IMPLICIT);

        etCompose.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                int action = keyEvent.getAction();
                if (action == KeyEvent.ACTION_UP || action == KeyEvent.ACTION_DOWN) {
                    int charsLeft = 140 - etCompose.length();
                    miRemainingChars.setTitle(Integer.toString(charsLeft));
                }
                return false;
            }
        });


        btnTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                client.postTweet(etCompose.getText().toString(), new JsonHttpResponseHandler(){
                    @Override
                    public void onSuccess(JSONObject json) {
                        backToTimeline();
                    }

                    @Override
                    public void onFailure(Throwable e, String s) {
                        Log.d("debug", e.toString());
                        Log.d("debug", s.toString());
                    }

                });
            }
        });

        client.getUserInfo(new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(JSONObject json) {
                Log.d("RESPONSE", json.toString());
                User currentUser = User.fromJSON(json);
                tvUserHandle.setText("@" + currentUser.getScreenName());
                tvUserName.setText(currentUser.getName());
                ivUserPicture.setImageResource(android.R.color.transparent);
                ImageLoader imageLoader = ImageLoader.getInstance();
                imageLoader.displayImage(currentUser.getProfileImageUrl(), ivUserPicture);
            }

            @Override
            public void onFailure(Throwable e, String s) {
                Log.d("debug", e.toString());
                Log.d("debug", s.toString());
            }
        });

    }

    public void backToTimeline() {
        Intent i = new Intent(this, TimelineActivity.class);
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.compose, menu);
        miRemainingChars = menu.findItem(R.id.remaining_chars);
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
}
