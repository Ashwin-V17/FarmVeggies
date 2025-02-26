package com.shop.veggies.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.shop.veggies.activity.MainActivity;

import java.io.PrintStream;

public class BSession {
    private static final String KEY_IS_LOGGEDIN = "isLoggedIn";
    private static String TAG = BSession.class.getSimpleName();
    public static BSession bSession;
    Context _context;
    SharedPreferences.Editor editor;
    private String pincode;
    SharedPreferences prefs;
    private String profileimage;
    private String refer_id;
    private String sessionId;
    private String user_address;
    private String user_email;
    private String user_id;
    private String user_mobile;
    private String user_name;

    public static BSession getInstance() {
        if (bSession == null) {
            synchronized (BSession.class) {
                bSession = new BSession();
            }
        }
        return bSession;
    }

    public void initialize(Context context, String user_id2, String user_name2, String user_mobile2, String user_address2, String profileimage2, String refer_id2, String sessionId2) {
        SharedPreferences.Editor editor2 = context.getSharedPreferences("Krishnan Store", 0).edit();
        editor2.putString("user_id", user_id2);
        editor2.putString("user_name", user_name2);
        editor2.putString("user_mobile", user_mobile2);
        editor2.putString("user_address", user_address2);
        editor2.putString("profileimage", profileimage2);
        editor2.putString("sessionId", sessionId2);
        editor2.putString("refer_id", refer_id2);
        editor2.apply();
        this.user_id = user_id2;
        this.user_name = user_name2;
        this.user_mobile = user_mobile2;
        this.user_address = user_address2;
        this.profileimage = profileimage2;
        this.refer_id = refer_id2;
        this.sessionId = sessionId2;
    }

    public void destroy(Context context) {
        context.getSharedPreferences("Krishnan Store", 0).edit().clear().apply();
        this.user_id = null;
        this.user_name = null;
        this.user_mobile = null;
        this.user_address = null;
        this.profileimage = null;
        this.refer_id = null;
        this.sessionId = null;
    }

    public String[] getSession(Context context) {
        SharedPreferences prefs2 = context.getSharedPreferences("Krishnan Store", 0);
        String string = prefs2.getString("user_id", "");
        this.user_id = string;
        String string2 = prefs2.getString("user_name", "");
        this.user_name = string2;
        String string3 = prefs2.getString("user_mobile", "");
        this.user_mobile = string3;
        String string4 = prefs2.getString("user_address", "");
        this.user_address = string4;
        String string5 = prefs2.getString("profileimage", "");
        this.profileimage = string5;
        String string6 = prefs2.getString("refer_id", "");
        this.refer_id = string6;
        String string7 = prefs2.getString("sessionId", "");
        this.sessionId = string7;
        return new String[]{string, string2, string3, string4, string5, string6, string7};
    }

    public String getRefer_id(Context context) {
        getSession(context);
        return this.refer_id;
    }

    public void setRefer_id(String refer_id2) {
        this.refer_id = refer_id2;
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
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
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

    public String getUser_id(Context context) {
        getSession(context);
        return this.user_id;
    }

    public void setUser_id(String user_id2) {
        this.user_id = user_id2;
    }

    public String getUser_name(Context context) {
        getSession(context);
        return this.user_name;
    }

    public void setUser_name(String user_name2) {
        this.user_name = user_name2;
    }

    public String getUser_mobile(Context context) {
        getSession(context);
        return this.user_mobile;
    }

    public void setUser_mobile(String user_mobile2) {
        this.user_mobile = user_mobile2;
    }

    public String getUser_email(Context context) {
        getSession(context);
        return this.user_email;
    }

    public void setUser_email(String user_email2) {
        this.user_email = user_email2;
    }

    public String getUser_address(Context context) {
        getSession(context);
        return this.user_address;
    }

    public void setUser_address(String user_address2) {
        this.user_address = user_address2;
    }

    public String getProfileimage(Context context) {
        getSession(context);
        return this.profileimage;
    }

    public void setProfileimage(String profileimage2) {
        this.profileimage = profileimage2;
    }
}
