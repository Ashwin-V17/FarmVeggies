package com.shop.veggies.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AppPreferences {
    private long Dayinsec = 43200000;
    private Context mContext;
    private SharedPreferences.Editor mPrefsEditor;
    private SharedPreferences mSharedPrefs;

    public AppPreferences(Context context) {
        this.mContext = context;
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        this.mSharedPrefs = defaultSharedPreferences;
        this.mPrefsEditor = defaultSharedPreferences.edit();
    }

    public void Set_FCMToken(String FCMToken) {
        this.mPrefsEditor.putString("FCMToken", FCMToken);
        this.mPrefsEditor.commit();
    }

    public String Get_FCMToken() {
        return this.mSharedPrefs.getString("FCMToken", "");
    }

    public void Set_Mobile(String Mobile) {
        this.mPrefsEditor.putString("mobile", Mobile);
        this.mPrefsEditor.commit();
    }

    public String Get_Mobile() {
        return this.mSharedPrefs.getString("mobile", "");
    }

    public void Set_completed(String completed) {
        this.mPrefsEditor.putString("completed", completed);
        this.mPrefsEditor.commit();
    }

    public String Get_completed() {
        return this.mSharedPrefs.getString("completed", "");
    }

    public void Set_swipe(String completed) {
        this.mPrefsEditor.putString("swipe", completed);
        this.mPrefsEditor.commit();
    }

    public String Get_swipe() {
        return this.mSharedPrefs.getString("swipe", "");
    }

    public void Set_TimelyDeliveryBoysPrivacy(boolean TimelyDeliveryBoysPrivacy) {
        this.mPrefsEditor.putBoolean("TimelyDeliveryBoysPrivacy", TimelyDeliveryBoysPrivacy);
        this.mPrefsEditor.commit();
    }

    public boolean Get_TimelyDeliveryBoysPrivacy() {
        return this.mSharedPrefs.getBoolean("TimelyDeliveryBoysPrivacy", false);
    }

    public void Set_TimelyDeliveryBoysupdatetime() {
        this.mPrefsEditor.putString("TimelyDeliveryBoysupdatetime", new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime()));
        this.mPrefsEditor.commit();
    }

    public boolean Get_TimelyDeliveryBoysupdatetime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String Getdate = this.mSharedPrefs.getString("TimelyDeliveryBoysupdatetime", "");
        if (!(Getdate == null || Getdate == "")) {
            try {
                if (CheckDifference(simpleDateFormat.parse(Getdate)) < this.Dayinsec) {
                    return true;
                }
                return false;
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public void Set_Socialvalues(String likevalues) {
        this.mPrefsEditor.putString("Socialvalues", likevalues);
        this.mPrefsEditor.commit();
    }

    public String Get_Socialvalues() {
        return this.mSharedPrefs.getString("Socialvalues", "");
    }

    public void Set_Socialupdatetime() {
        this.mPrefsEditor.putString("Socialupdatetime", new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime()));
        this.mPrefsEditor.commit();
    }

    public boolean Get_Socialupdatetime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String Getdate = this.mSharedPrefs.getString("Socialupdatetime", "");
        if (!(Getdate == null || Getdate == "")) {
            try {
                if (CheckDifference(simpleDateFormat.parse(Getdate)) < this.Dayinsec) {
                    return true;
                }
                return false;
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public long CheckDifference(Date startDate) {
        try {
            return new SimpleDateFormat("yyyyMMddHHmmss").parse(new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime())).getTime() - startDate.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public void Set_Versionupdatetime() {
        this.mPrefsEditor.putString("Versionupdatetime", new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime()));
        this.mPrefsEditor.commit();
    }

    public boolean Get_Versionupdatetime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String Getdate = this.mSharedPrefs.getString("Versionupdatetime", "");
        if (!(Getdate == null || Getdate == "")) {
            try {
                if (CheckDifference(simpleDateFormat.parse(Getdate)) < this.Dayinsec) {
                    return true;
                }
                return false;
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
