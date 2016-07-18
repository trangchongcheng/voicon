package com.cheng.robotchat.voiconchat.ultils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by chientruong on 6/7/16.
 */
public class UserPreference {
    public static String IS_LOGIN = "user";
    private SharedPreferences sharedPreferences;
    private Context context;
    private static UserPreference preferences;

    public UserPreference(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("times_preferences", 0);
    }
    public static UserPreference getInstance(Context context) {
        if (preferences == null) {
            preferences = new UserPreference(context);
        }
        return preferences;
    }
    public void setLoginTrue() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(IS_LOGIN, true);
        Log.d("SetTimes", "setIsTimesTrue: ");
        editor.apply();
    }
    public void setLoginFalse(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Log.d("SetTimes", "setIsTimesFalse: ");
        editor.putBoolean(IS_LOGIN, false);
        editor.apply();
    }
    public boolean getIsLogin(){
        return sharedPreferences.getBoolean(IS_LOGIN,false);

    }
}
