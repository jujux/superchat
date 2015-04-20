package fr.ecp.sio.superchat.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import fr.ecp.sio.superchat.APIClient;
import fr.ecp.sio.superchat.AccountManager;
import fr.ecp.sio.superchat.fragments.LoginFragment;
import fr.ecp.sio.superchat.R;

/**
 * Created by yaj on 12/12/14.
 */
public class PostActivity extends ActionBarActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_activity);
        /* findViewById(R.layout.post_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tweet();
            }
        }); */
    }

    public void tweet(View view) {
        EditText _messageText = (EditText) findViewById(R.id.tweet);
        String _msg = _messageText.getText().toString();

        if (_msg.isEmpty()) {
            _messageText.setError(getString(R.string.required));
            _messageText.requestFocus();
            return;
        }

        new AsyncTask<String, Void, Boolean>() {

            @Override
            protected Boolean doInBackground(String... params) {
                try {
                    String _msg = params[0];
                    String handle = AccountManager.getUserHandle(PostActivity.this);
                    String token = AccountManager.getUserToken(PostActivity.this);
                    new APIClient().postTweet(handle, token, _msg);
                    return true;
                } catch (IOException e) {
                    Log.e(LoginFragment.class.getName(), "Tweet failed!", e);                }
                return false;
            }

            @Override
            protected void onPostExecute(Boolean result) {
                if (result) {
                    finish();
                    Toast.makeText(PostActivity.this, R.string.post_success, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(PostActivity.this, R.string.post_error, Toast.LENGTH_SHORT).show();
                }
            }
        }.execute(_msg);

    }
}
