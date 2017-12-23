package com.atminfotech.barcodestorenew;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.HashMap;

/**
 * Created by ABC on 08/04/2017.
 */

public class SessionManagement {
    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "Pref";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    // User name (make variable public to access from outside)
    public static final String KEY_NAME = "name";

    public static final String KEY_Mobile = "mobile";

    public static final String KEY_EmailId = "emailid";


    public static final String KEY_Imei = "imei";


    public SessionManagement(Context context){
        this._context=context;
        pref =_context.getSharedPreferences(PREF_NAME,PRIVATE_MODE);
        editor =pref.edit();
    }

    public void createRegisterationSession(String name,String mobile,String imei,String emailid){
        //store input value as a true
        editor.putBoolean(IS_LOGIN,true);

        //store name in pref
        editor.putString(KEY_NAME,name);

        editor.putString(KEY_Mobile,mobile);

        editor.putString(KEY_EmailId,emailid);

        editor.putString(KEY_Imei,imei);

        editor.commit();

    }

    public HashMap<String,String> getUserDetails(){
        HashMap<String,String> user =new HashMap<>();

        //user name
        user.put(KEY_NAME,pref.getString(KEY_NAME,null));

        user.put(KEY_Mobile,pref.getString(KEY_Mobile,null));


        user.put(KEY_EmailId,pref.getString(KEY_EmailId,null));

        user.put(KEY_Imei ,pref.getString(KEY_Imei,null));


        return user;
    }


    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN,false);
    }
}
