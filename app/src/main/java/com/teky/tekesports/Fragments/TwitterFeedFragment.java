package com.teky.tekesports.Fragments;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import com.teky.tekesports.Components.Constants;
import com.teky.tekesports.MainActivity;
import com.teky.tekesports.R;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;
import com.twitter.sdk.android.tweetui.TweetUi;
import com.twitter.sdk.android.tweetui.UserTimeline;

import butterknife.ButterKnife;
import io.fabric.sdk.android.Fabric;

/**
 * Created by JoshuaMabry on 12/6/16.
 * toornament-android.
 */

public class TwitterFeedFragment extends ListFragment {

    private static String TITLE = "title";
    private static String title;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_twitter_feed, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        ButterKnife.bind(getActivity());
        if (getArguments() != null) {
            title = getArguments().getString(TITLE);
            ((MainActivity) getActivity()).getToolbar().setTitle(title);
        }

        TwitterAuthConfig authConfig = new TwitterAuthConfig(Constants.TWITTER_KEY, Constants.TWITTER_SECRET);
        Fabric.with(getActivity(), new TwitterCore(authConfig), new TweetUi());

        final UserTimeline userTimeline = new UserTimeline.Builder()
                .screenName("ESPN_Esports")
                .build();
        final TweetTimelineListAdapter adapter = new TweetTimelineListAdapter.Builder(getActivity()).setViewStyle(R.style.CustomStyleTweet)
                .setTimeline(userTimeline)
                .build();
        setListAdapter(adapter);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.home_button).setVisible(false);
    }

    public static TwitterFeedFragment newInstance() {

        Bundle args = new Bundle();
        args.putString(TITLE, title);
        TwitterFeedFragment fragment = new TwitterFeedFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
