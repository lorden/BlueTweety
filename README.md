BlueTweety
===========

Simple twitter client for Android.

* User can sign in to Twitter using OAuth login
* User can view the tweets from their home timeline
    * User is able to see the username, name, body and timestamp for each tweet
    * User is displayed the relative timestamp for a tweet "8m", "7h"
    * User can view more tweets as they scroll with infinite pagination
    * Links in tweets are clickable and will launch the web browser
* User can compose a new tweet
    * User can click the "Compose" icon in the Action Bar on the top right
    * User can then enter a new tweet and post this to twitter
    * User is taken back to home timeline with new tweet visible in timeline
    * User can see a counter with total number of characters left for tweet
* User can refresh tweets timeline by clicking the refresh icon
* Improved the user interface and theme the app to feel "twitter branded"


Time spent: ~10 hours.

Walkthrough
-----------
![Video Walkthrough](walkthrough.gif)


BlueTweety V2
=============

Added stories:
* User can switch between Timeline and Mention views using tabs.
    * User can view their home timeline tweets.
    * User can view the recent mentions of their username.
    * User can scroll to bottom of either of these lists and new tweets will load ("infinite scroll")
* User can navigate to view their own profile
    * User can see picture, tagline, # of followers, # of following, and tweets on their profile.
* User can click on the profile image in any tweet to see another user's profile.
    * User can see picture, tagline, # of followers, # of following, and tweets of clicked user.
    * Profile view should include that user's timeline
* When a network request is sent, user sees an indeterminate progress indicator


Time spent: ~10 hours.

Walkthrough
-----------
![Video Walkthrough](walkthrough-v2.gif)
