package fr.ecp.sio.superchat;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by yaj on 12/12/14.
 */
public class AccountManager {

    private static final String PREF_API_TOKEN = "apiToken";
    private static final String PREF_API_HANDLE = "apiHandle";

    /**
     * vérifier si un user est connecté
     * @param pContext
     * @return True si l'user est connecté false sinon
     */
    public static boolean isConnected(Context pContext) {
        return getUserToken(pContext) != null;
    }

    /**
     * récupérer un token
     * @param pContext
     * @return le token de l'utilisateur
     */
    public static String getUserToken(Context pContext) {
        SharedPreferences _pref = PreferenceManager.getDefaultSharedPreferences(pContext);
        return _pref.getString(PREF_API_TOKEN, null);
    }

    public static String getUserHandle(Context pContext) {
        SharedPreferences _pref = PreferenceManager.getDefaultSharedPreferences(pContext);
        return _pref.getString(PREF_API_HANDLE, null);
    }

    /**
     * Se logger correspond à créer et sauver un token
     * @param pContext
     * @param token
     */
    public static void login(Context pContext,String token, String handle) {
        SharedPreferences _pref = PreferenceManager.getDefaultSharedPreferences(pContext);
        _pref.edit()
                .putString(PREF_API_TOKEN, token)
                .putString(PREF_API_HANDLE, handle)
                .apply();
    }
    public static void logout(Context pContext) {
        SharedPreferences _pref = PreferenceManager.getDefaultSharedPreferences(pContext);
        _pref.edit()
                .remove(PREF_API_TOKEN)
                .remove(PREF_API_HANDLE)
                .apply();
    }


    /**
     * Supprimer un token, cad déconnecter un user
     * @param pContext
     */
    public static void clearUserToke(Context pContext) {
        SharedPreferences _pref = PreferenceManager.getDefaultSharedPreferences(pContext);
        _pref.edit()
                .remove(PREF_API_TOKEN)
                .apply();
    }


}
