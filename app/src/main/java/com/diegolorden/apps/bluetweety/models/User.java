package com.diegolorden.apps.bluetweety.models;

import org.json.JSONException;
import org.json.JSONObject;

public class User {

    private String name;
    private long uid;
    private String screenName;
    private String profileImageUrl;
    private String profileBackgroundUrl;
    private String description;
    private int tweetCount;
    private int followingCount;
    private int followersCount;

    public String getName() {
        return name;
    }

    public long getUid() {
        return uid;
    }

    public String getScreenName() {
        return screenName;
    }

    public String getHandle() {
        return "@" + screenName;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public String getProfileBackgroundUrl() {
        return profileBackgroundUrl;
    }

    public void setProfileBackgroundUrl(String url) {
        profileBackgroundUrl = url;
    }

    public static User fromJSON(JSONObject json) {
        User user = new User();
        try {
            user.name = json.getString("name");
            user.uid = json.getLong("id");
            user.screenName = json.getString("screen_name");
            user.profileImageUrl = json.getString("profile_image_url");
            user.profileBackgroundUrl = json.getString("profile_background_image_url");
            user.tweetCount = json.getInt("statuses_count");
            user.followersCount = json.getInt("followers_count");
            user.followingCount = json.getInt("friends_count");
            user.description = json.getString("description");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        return user;
    }

    public int getTweetCount() {
        return tweetCount;
    }

    public void setTweetCount(int tweetCount) {
        this.tweetCount = tweetCount;
    }

    public int getFollowingCount() {
        return followingCount;
    }

    public void setFollowingCount(int followingCount) {
        this.followingCount = followingCount;
    }

    public int getFollowersCount() {
        return followersCount;
    }

    public void setFollowersCount(int followersCount) {
        this.followersCount = followersCount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
