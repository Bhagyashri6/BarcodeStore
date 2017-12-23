package com.atminfotech.barcodestorenew.splash;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.atminfotech.barcodestorenew.ConnectivityReceiver;
import com.atminfotech.barcodestorenew.CustomFonts;
import com.atminfotech.barcodestorenew.HomeActivity;
import com.atminfotech.barcodestorenew.R;

import static android.os.Build.VERSION_CODES.M;

public class SplashScreen extends AppCompatActivity implements  ConnectivityReceiver.ConnectivityReceiverListener{

    String NAMESPACE = "http://tempuri.org/";
    String URL = "http://atm-india.in/Service.asmx";
    String  MethodName = "InsertAndroidUser";
    ImageView Enter;
    ViewPager viewPager;
    View child=null;
    RelativeLayout item = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        checkConnection();

        PackageManager pm = getPackageManager();
        if (pm.checkPermission(Manifest.permission.READ_PHONE_STATE, getPackageName()) != PackageManager.PERMISSION_GRANTED) {
            checkPermission();
        }

        Enter = (ImageView) findViewById (R.id.enter_app);
        Enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SplashScreen.this, HomeActivity.class));
            }
        });

        viewPager = (ViewPager) findViewById(R.id.vp_main);
        viewPager.setAdapter(new MainPagerAdapter(getSupportFragmentManager()));
        viewPager.setOffscreenPageLimit(2);
    }

    public boolean checkPermission()
    {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if(currentAPIVersion>= M)
        {
            if (ContextCompat.checkSelfPermission(SplashScreen.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(SplashScreen.this, Manifest.permission.READ_PHONE_STATE)) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(SplashScreen.this);
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle("Permission necessary");
                    alertBuilder.setMessage("Read state of your phone!!!");
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(SplashScreen.this, new String[]{Manifest.permission.READ_PHONE_STATE}, M);
                        }
                    });
                    AlertDialog alert = alertBuilder.create();
                    alert.show();
                } else {
                    ActivityCompat.requestPermissions(SplashScreen.this, new String[]{Manifest.permission.READ_PHONE_STATE},123 );
                }
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 123:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(SplashScreen.this, "welcome", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SplashScreen.this, "Exiting the app", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        showSnack(isConnected);
    }

    private void showSnack(boolean isConnected) {

        if (!isConnected) {
            item = (RelativeLayout)findViewById(R.id.activity_splash_screen);
            child = getLayoutInflater().inflate(R.layout.activity_no_internet, null);
            item.addView(child);
        }else{
            if(child!=null) {
                View namebar = findViewById(R.id.internet);
                ((ViewGroup) namebar.getParent()).removeView(namebar);
            }

        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        // register connection status listener
        CustomFonts.getInstance().setConnectivityListener(this);
    }


     //Callback will be triggered when there is change in
     //network connection

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showSnack(isConnected);
    }

    String getDeviceID(TelephonyManager phonyManager){

        String id = phonyManager.getDeviceId();
        if (id == null){
            id = "not available";
        }

        int phoneType = phonyManager.getPhoneType();
        switch(phoneType){
            case TelephonyManager.PHONE_TYPE_NONE:
                return id;

            case TelephonyManager.PHONE_TYPE_GSM:
                return id;

            case TelephonyManager.PHONE_TYPE_CDMA:
                return  id;

            default:
                return  id;
        }
    }
}
