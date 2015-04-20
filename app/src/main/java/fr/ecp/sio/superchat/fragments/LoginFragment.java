package fr.ecp.sio.superchat.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import fr.ecp.sio.superchat.APIClient;
import fr.ecp.sio.superchat.AccountManager;
import fr.ecp.sio.superchat.R;
import fr.ecp.sio.superchat.activities.MainActivity;

/**
 * Created by yaj on 12/12/14.
 */
public class LoginFragment extends DialogFragment implements DialogInterface.OnShowListener {

    private EditText mHandleText;
    private EditText mPasswordText;



    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View _view = LayoutInflater.from(getActivity()).inflate(R.layout.login_fragment, null);
        mHandleText = (EditText) _view.findViewById(R.id.handle);
        mPasswordText = (EditText) _view.findViewById(R.id.password);

        Dialog _dialog = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.login)
                .setView(_view)
                .setPositiveButton(android.R.string.ok, null)
                .setNegativeButton(android.R.string.cancel, null)
                .create();
        _dialog.setOnShowListener(this);
        return _dialog ;
    }


    /**
     * Ne referme pas le bouton qu'on clique dans le vide.
     * @param dialog
     */
    @Override
    public void onShow(DialogInterface dialog) {
        ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        login();
                    }
                });
    }

    private void login() {
        final String _handle = mHandleText.getText().toString();
        final String _password = mPasswordText.getText().toString();

        if (_handle.isEmpty()) {
            mHandleText.setError(getString(R.string.required));
            mHandleText.requestFocus();
            return;
        }
        if (_password.isEmpty()) {
            mPasswordText.setError(getString(R.string.required));
            mPasswordText.requestFocus();
            return;
        }

        new AsyncTask<String,Void,String>() {
            @Override
            protected String doInBackground(String... params) {
                try {
                    String _handle = params[0];
                    String _password = params[1];
                    return new APIClient().login(_handle, _password);
                } catch (IOException e) {
                    Log.e(LoginFragment.class.getName(), "Login failed", e);
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String token) {
                if (token != null) {
                   AccountManager.login(getActivity(), token, _handle);
                   dismiss(); // fermer le dialog et afficher connexion ok
                   Toast.makeText(getActivity(), R.string.login_success, Toast.LENGTH_SHORT).show();
                } else { // Erreur de connexion
                    Toast.makeText(getActivity(), R.string.login_error, Toast.LENGTH_SHORT).show();
                }
                ((MainActivity) getActivity()).updateUserList();
                Fragment target = getTargetFragment();
                if (target != null) {
                    target.onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, null);
                }
            }
        }.execute(_handle, _password);

    }
}
