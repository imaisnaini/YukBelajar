package com.unsia.yukbelajar.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    private static final String PREF_NAME = "user_session";
    private static final String KEY_IS_LOGIN = "is_login";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_JENIS_KELAMIN = "jenis_kelamin";
    private static final String KEY_FOTO_PROFILE = "foto_profile";

    private final SharedPreferences prefs;

    public SessionManager(Context context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public void saveLogin(String userId, String username, String email, String foto) {
        prefs.edit()
                .putBoolean(KEY_IS_LOGIN, true)
                .putString(KEY_USER_ID, userId)
                .putString(KEY_USERNAME, username)
                .putString(KEY_JENIS_KELAMIN, email)
                .putString(KEY_FOTO_PROFILE, foto)
                .apply();
    }

    public boolean isLoggedIn() {
        return prefs.getBoolean(KEY_IS_LOGIN, false);
    }

    public void logout() {
        prefs.edit().clear().apply();
    }
}
