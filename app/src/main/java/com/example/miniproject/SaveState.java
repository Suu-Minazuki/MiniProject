package com.example.miniproject;

import android.content.Context;
import android.content.SharedPreferences;

public class SaveState {

    Context context;
    String saveName;
    SharedPreferences sharedPreferences;

    public SaveState(Context context, String saveName) {
        this.context = context;
        this.saveName = saveName;
        sharedPreferences = context.getSharedPreferences(saveName, context.MODE_PRIVATE);
    }

    public void setState(int key){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("Key", key);
        editor.apply();
    }

    public int getState(){
        return sharedPreferences.getInt("Key", 0);
    }

}
