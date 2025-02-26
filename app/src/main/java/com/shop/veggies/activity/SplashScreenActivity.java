package com.shop.veggies.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.play.core.appupdate.AppUpdateManager;
import com.shop.veggies.R;

import java.io.PrintStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SplashScreenActivity extends AppCompatActivity {
    private static final int FLEXIBLE_APP_UPDATE_REQ_CODE = 123;
    private static int SPLASH_TIME_OUT = 3000;
    AppUpdateManager appUpdateManager;
   /* InstallStateUpdatedListener installStateUpdatedListener = new InstallStateUpdatedListener() {
        public void onStateUpdate(InstallState installState) {
            if (installState.installStatus() == 11) {
                SplashScreenActivity.this.popupSnackbarForCompleteUpdate();
            }
        }
    };
*/
    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_splash_screen);
        Date date1 = null;
        try {
            date1 = new SimpleDateFormat("dd-MM-yyyy").parse("20-10-2021");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date1);
        calendar.add(6, 10);
        Date newDate = calendar.getTime();
        String formateDate = new SimpleDateFormat("dd-MM-yyyy").format(newDate);
        PrintStream printStream = System.out;
        printStream.println("calcenter0" + newDate);
        PrintStream printStream2 = System.out;
        printStream2.println("calcenter0" + formateDate);
       // inAppUpdate();
        new Handler().postDelayed(new Runnable() {
            public void run() {
                SplashScreenActivity.this.startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
                SplashScreenActivity.this.finish();
            }
        }, (long) SPLASH_TIME_OUT);
    }

   /* private void inAppUpdate() {
        AppUpdateManager create = AppUpdateManagerFactory.create(this);
        this.appUpdateManager = create;
        create.getAppUpdateInfo().addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>() {
            public void onSuccess(AppUpdateInfo appUpdateInfo) {
                Log.e("AVAILABLE_VERSION_CODE", appUpdateInfo.availableVersionCode() + "");
                if (appUpdateInfo.updateAvailability() == 2 && appUpdateInfo.isUpdateTypeAllowed(1)) {
                    try {
                        SplashScreenActivity.this.appUpdateManager.startUpdateFlowForResult(appUpdateInfo, 1, (Activity) SplashScreenActivity.this, 123);
                    } catch (IntentSender.SendIntentException e) {
                    }
                }
            }
        });
        this.appUpdateManager.registerListener(this.installStateUpdatedListener);
    }

    *//* access modifiers changed from: private *//*
    public void popupSnackbarForCompleteUpdate() {
        Snackbar snackbar = Snackbar.make(findViewById(16908290), (CharSequence) "Update almost finished!", -2);
        snackbar.setAction((CharSequence) getString(R.string.restart), (View.OnClickListener) this.appUpdateManager.completeUpdate());
        snackbar.setActionTextColor(getResources().getColor(R.color.colorPrimary));
        snackbar.show();
    }*/
}
