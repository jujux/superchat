package fr.ecp.sio.superchat.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;

import fr.ecp.sio.superchat.AccountManager;
import fr.ecp.sio.superchat.R;

/**
 * Created by yaj on 12/12/14.
 */

@SuppressWarnings("deprecation")
public class SettingsActivity extends PreferenceActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        Preference logoutPref = findPreference("logout");
        logoutPref.setEnabled(AccountManager.isConnected(this));
        logoutPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(final Preference preference) {
                new AlertDialog.Builder(SettingsActivity.this)
                        .setTitle(R.string.logout)
                        .setMessage(R.string.logout_confirm)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                AccountManager.logout(SettingsActivity.this);
                                preference.setEnabled(false);
                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .show();
                AccountManager.logout(SettingsActivity.this);
                return true;
            }
        });
    }
}
