package fr.ecp.sio.superchat.activities;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TabHost;
import android.widget.TextView;

import fr.ecp.sio.superchat.R;
import fr.ecp.sio.superchat.fragments.UsersFragment;

/**
 * Created by yaj on 15/04/15.
 */
public class TabHostActivity extends TabActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tabhost);
        Log.i("VODVOD", "Test2");


        TabHost tabHost = (TabHost)findViewById(android.R.id.tabhost);


        TabHost.TabSpec tabFollowings = tabHost.newTabSpec(getString(R.string.followings));
        TabHost.TabSpec tabTweets = tabHost.newTabSpec(getString(R.string.tweets));
        TabHost.TabSpec tabFollowers = tabHost.newTabSpec(getString(R.string.followers));

        Intent intent = new Intent(this, FollowActivity.class);
        intent.putExtras(getIntent().getExtras());
        intent.putExtra(UsersFragment.LIST_TYPE, UsersFragment.FOLLOWINGS);

        tabFollowings.setIndicator(getString(R.string.followings));
        tabFollowings.setContent(intent);

        intent = new Intent(this, TweetsActivity.class);
        intent.putExtras(getIntent().getExtras());

        tabTweets.setIndicator(getString(R.string.tweets));
        tabTweets.setContent(intent);

        intent = new Intent(this, FollowActivity.class);
        intent.putExtras(getIntent().getExtras());
        intent.putExtra(UsersFragment.LIST_TYPE, UsersFragment.FOLLOWERS);

        tabFollowers.setIndicator(getString(R.string.followers));
        tabFollowers.setContent(intent);

        tabHost.addTab(tabFollowings);
        tabHost.addTab(tabTweets);
        tabHost.addTab(tabFollowers);

        tabHost.setCurrentTabByTag(getString(R.string.tweets));

        for(int i=0;i<tabHost.getTabWidget().getChildCount();i++)
        {
            TextView tv = (TextView) tabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
            tv.setTextColor(getResources().getColor(R.color.super_chat_deep_purple));
            Log.i("VODVOD", "Test");
        }
    }
}
