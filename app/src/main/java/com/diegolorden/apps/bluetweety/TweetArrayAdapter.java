package com.diegolorden.apps.bluetweety;

import android.content.Context;
import java.text.ParseException;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.activeandroid.util.Log;
import com.diegolorden.apps.bluetweety.models.Tweet;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class TweetArrayAdapter extends ArrayAdapter<Tweet>{
    public TweetArrayAdapter(Context context, List<Tweet> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Tweet tweet = getItem(position);

        View v;
        if (convertView == null) {
            LayoutInflater inflator = LayoutInflater.from(getContext());
            v = inflator.inflate(R.layout.tweet_item, parent, false);
        } else {
            v = convertView;
        }

        ImageView ivProfileImage = (ImageView) v.findViewById(R.id.ivProfileImage);
        ivProfileImage.setImageResource(android.R.color.transparent);
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.displayImage(tweet.getUser().getProfileImageUrl(), ivProfileImage);

        TextView tvUserName = (TextView) v.findViewById(R.id.tvUserName);
        tvUserName.setText(tweet.getUser().getName());

        TextView tvUserHandle = (TextView) v.findViewById(R.id.tvUserHandle);
        tvUserHandle.setText("@" + tweet.getUser().getScreenName());

        TextView tvBody = (TextView) v.findViewById(R.id.tvBody);
        tvBody.setText(tweet.getBody());

        TextView tvTimestamp = (TextView) v.findViewById(R.id.tvTimestamp);
        tvTimestamp.setText(getRelativeTimeAgo(tweet.getCreatedAt()));

        return v;
    }

    public String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
            if (relativeDate.startsWith("in")) {
                relativeDate = "now";
            } else {
                relativeDate = relativeDate.substring(0, relativeDate.indexOf(" ") + 2).replace(" ", "");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;
    }
}
