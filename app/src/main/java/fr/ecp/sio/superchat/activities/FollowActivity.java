package fr.ecp.sio.superchat.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;

import fr.ecp.sio.superchat.R;
import fr.ecp.sio.superchat.fragments.UsersFragment;

/**
 * Created by yaj on 15/04/15.
 */
public class FollowActivity extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.follow_activity);
        if (savedInstanceState == null) {
            Fragment userFragment = new UsersFragment();
            userFragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.follow_data, userFragment)
                    .commit();
        }
    }
}
