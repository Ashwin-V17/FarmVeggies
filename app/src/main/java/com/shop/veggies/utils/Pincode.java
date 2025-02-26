package com.shop.veggies.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.shop.veggies.activity.MainActivity;

import java.io.PrintStream;

public class Pincode {
    private static final String KEY_IS_LOGGEDIN = "isLoggedIn";
    private static String TAG = Pincode.class.getSimpleName();
    public static Pincode bSession;
    Context _context;
    SharedPreferences.Editor editor;
    private String pincode;
    SharedPreferences prefs;
    private String profileimage;
    private String sessionId;
    private String user_address;
    private String user_email;
    private String user_id;
    private String user_mobile;
    private String user_name;

    public static Pincode getInstance() {
        if (bSession == null) {
            synchronized (Pincode.class) {
                bSession = new Pincode();
            }
        }
        return bSession;
    }

    public void initialize(Context context, String pincode2, String sessionId2) {
        SharedPreferences.Editor editor2 = context.getSharedPreferences("Krishnan Store", 0).edit();
        editor2.putString("pincode", pincode2);
        editor2.putString("sessionId", sessionId2);
        editor2.apply();
        this.pincode = pincode2;
        this.sessionId = sessionId2;
    }

    public void destroy(Context context) {
        context.getSharedPreferences("Krishnan Store", 0).edit().clear().apply();
        this.pincode = null;
        this.sessionId = null;
    }

    public String[] getSession(Context context) {
        SharedPreferences prefs2 = context.getSharedPreferences("Krishnan Store", 0);
        String string = prefs2.getString("pincode", "");
        this.pincode = string;
        String string2 = prefs2.getString("sessionId", "NosessionId");
        this.sessionId = string2;
        return new String[]{string, string2};
    }

    public String getPincode(Context context) {
        getSession(context);
        return this.pincode;
    }

    public void setPincode(String pincode2) {
        this.pincode = pincode2;
    }

    public String getKey(Context context) {
        return context.getSharedPreferences("Krishnan Store", 0).getString("key", "noKey");
    }

    public Boolean isAuthenticated(Context context) {
        String str = this.sessionId;
        if (str != null && this.user_name != null && !str.isEmpty() && !this.user_name.isEmpty() && !this.sessionId.equals("NosessionId") && !this.user_name.equals("Nouser_name")) {
            return true;
        }
        Intent intent = new Intent(context.getApplicationContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
        return false;
    }

    public Boolean isApplicationExit(Context context) {
        getSession(context);
        PrintStream printStream = System.out;
        printStream.println("====" + this.sessionId + "UN" + this.user_name);
        String str = this.user_name;
        if (str == null || str.isEmpty() || this.user_name.equals("Nouser_name")) {
            return false;
        }
        PrintStream printStream2 = System.out;
        printStream2.println("====" + this.sessionId + "UN--true" + this.user_name);
        return true;
    }

    public String getSessionId(Context context) {
        getSession(context);
        return this.sessionId;
    }

    public void setSessionId(String sessionId2) {
        this.sessionId = sessionId2;
    }
}
