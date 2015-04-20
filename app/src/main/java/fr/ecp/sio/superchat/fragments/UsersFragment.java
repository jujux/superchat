package fr.ecp.sio.superchat.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

import fr.ecp.sio.superchat.AccountManager;
import fr.ecp.sio.superchat.R;
import fr.ecp.sio.superchat.activities.PostActivity;
import fr.ecp.sio.superchat.activities.TabHostActivity;
import fr.ecp.sio.superchat.activities.TweetsActivity;
import fr.ecp.sio.superchat.adapters.UsersAdapter;
import fr.ecp.sio.superchat.loaders.UsersLoader;
import fr.ecp.sio.superchat.models.User;

/**
 * Created by YaJ on 05/12/2014.
 */
public class UsersFragment extends ListFragment implements LoaderManager.LoaderCallbacks<List<User>> {

    private static final int LOADER_USERS = 1000;
    private static final int REQUEST_LOGIN_FOR_POST = 1;
    public static final int FOLLOWERS = 66;
    public static final int FOLLOWINGS = 99;
    public static final int ALL_USERS = 0;
    public static final String LIST_TYPE = "listUsers";
    public static final String HANDLE = "handle";

    //private ListView mListView;
    private UsersAdapter mListAdapter;
    private boolean mIsMasterDetailsMode;
    private int mListType = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.users_fragment, container, false);
    }

    /*@Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ListView listView = new ListView(getActivity());
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        );
        listView.setLayoutParams(params);
        return listView;
    }*/

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mIsMasterDetailsMode = getActivity().findViewById(R.id.tweets_content) != null;
        //mListView = (ListView) view.findViewById(android.R.id.list);
        mListAdapter = new UsersAdapter();
        setListAdapter(mListAdapter);
        //if (mIsMasterDetailsMode) {
            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        //}
        view.findViewById(R.id.post).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                post();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        getLoaderManager().initLoader(LOADER_USERS, null, this);
    }

    @Override
    public Loader<List<User>> onCreateLoader(int id, Bundle args) {
        String handle = AccountManager.getUserHandle(getActivity());
        if (getArguments() != null) {
            mListType = getArguments().getInt(LIST_TYPE);
            if (getArguments().containsKey(TweetsFragment.ARG_USER)) {
                User user = getArguments().getParcelable(TweetsFragment.ARG_USER);
                handle = user.getHandle();
            }
        }
        return new UsersLoader(getActivity(), mListType, handle);    }

    @Override
    public void onLoadFinished(Loader<List<User>> loader, List<User> users) {
        mListAdapter.setUsers(users);
    }

    @Override
    public void onLoaderReset(Loader<List<User>> loader) { }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        User user = mListAdapter.getItem(position);
        if (!mIsMasterDetailsMode && false) {
            Intent intent = new Intent(getActivity(), TweetsActivity.class);
            intent.putExtras(TweetsFragment.newArguments(user));
            startActivity(intent);
        } else {
            Intent intent = new Intent(getActivity(), TabHostActivity.class);
            intent.putExtras(TweetsFragment.newArguments(user));
            startActivity(intent);
        }
    }

    private void post() {
        if (AccountManager.isConnected(getActivity())) {
            startActivity(new Intent(getActivity(), PostActivity.class));
        } else {
            LoginFragment _fragment = new LoginFragment();
            _fragment.setTargetFragment(this, REQUEST_LOGIN_FOR_POST);
            _fragment.show(getFragmentManager(),"login_dialog");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_LOGIN_FOR_POST && resultCode == PostActivity.RESULT_OK) {
            post();
        }
    }
}