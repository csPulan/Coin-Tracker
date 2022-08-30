package com.example.cryptoscholartracker;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PrefConfig {

    private static final String LIST_HISTORY = "list_history";

    public static void writeListInPref(Context context, ArrayList<historyRVModal> list) {
        Gson gson = new Gson();
        String jsonString = gson.toJson(list);

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(LIST_HISTORY, jsonString);
        editor.apply();
    }

    public static ArrayList<historyRVModal> readListFromPref(Context context) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        String jsonString = pref.getString(LIST_HISTORY, "");

        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<historyRVModal>>() {}.getType();
        ArrayList<historyRVModal> list = gson.fromJson(jsonString, type);
        return list;
    }

    public static void removeDatafromPref(Context context){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();
        editor.remove(LIST_HISTORY);
        editor.remove("unique_key");
        editor.apply();
    }
}
