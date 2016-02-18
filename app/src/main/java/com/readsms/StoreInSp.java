package com.readsms;

import android.content.Context;
import android.content.SharedPreferences;


public class StoreInSp {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;
    int PRIVATE_MODE = 0;

    public static final String KEY_FIRST_TIME= "first";

    private static final String PREF_NAME = "ReadSmsSP";

    // Constructor
    public StoreInSp(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void storeInSp(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }

    public String getStoredData(String key) {
        String value = null;
        value = pref.getString(key, null);
        return value;
    }


}
