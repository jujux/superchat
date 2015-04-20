package fr.ecp.sio.superchat.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.View;

import java.util.List;

import fr.ecp.sio.superchat.activities.TweetsActivity;
import fr.ecp.sio.superchat.adapters.TweetsAdapter;
import fr.ecp.sio.superchat.loaders.TweetsLoader;
import fr.ecp.sio.superchat.models.Tweet;
import fr.ecp.sio.superchat.models.User;

/**
 * Created by YaJ on 05/12/2014.
 */
public class TweetsFragment extends ListFragment implements LoaderManager.LoaderCallbacks<List<Tweet>> {

    private static final int LOADER_TWEETS = 1000;

    public static final String ARG_USER = "user";

    private User mUser;
    private TweetsAdapter mListAdapter;

    public static Bundle newArguments(User user) {
        Bundle args = new Bundle();
        args.putParcelable(ARG_USER, user);
        return args;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUser = getArguments().getParcelable(ARG_USER);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mListAdapter = new TweetsAdapter();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity() instanceof TweetsActivity) {
            getActivity().setTitle(mUser.getHandle());
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        getLoaderManager().initLoader(LOADER_TWEETS, null, this);
    }

    @Override
    public Loader<List<Tweet>> onCreateLoader(int id, Bundle args) {
        return new TweetsLoader(getActivity(), mUser.getHandle());
    }

    @Override
    public void onLoadFinished(Loader<List<Tweet>> loader, List<Tweet> tweets) {
        mListAdapter.setTweets(tweets);
        setListAdapter(mListAdapter);
    }

    @Override
    public void onLoaderReset(Loader<List<Tweet>> loader) { }

}