        package com.shop.veggies.activity;

        import android.content.Intent;
        import android.content.SharedPreferences;
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
        //    InstallStateUpdatedListener installStateUpdatedListener = new InstallStateUpdatedListener() {
        //        public void onStateUpdate(InstallState installState) {
        //            if (installState.installStatus() == 11) {
        //                SplashScreenActivity.this.popupSnackbarForCompleteUpdate();
        //            }
        //        }
        //    };

            /* access modifiers changed from: protected */

            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_splash_screen);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        SharedPreferences preferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
                        boolean isLoggedIn = preferences.getBoolean("isLoggedIn", false);

                        if (isLoggedIn) {
                            startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
                        } else {
                            startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class));
                        }
                        finish(); // Close SplashScreenActivity
                    }
                }, SPLASH_TIME_OUT);
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
