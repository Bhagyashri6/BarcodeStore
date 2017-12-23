package com.atminfotech.barcodestorenew;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.ksoap2.serialization.SoapObject;

import java.util.HashMap;
import java.util.Map;

public class SoftwareDetails extends AppCompatActivity implements View.OnClickListener {

    ImageView ScreenShotOne, ScreenShotTwo, ScreenShotThree, ScreenShotFour, ScreenShotFive, AppLogo, Cart, Playstore;
    TextView Appname,Apptype,ApproxTime,Overview,Clients;
    int[] i = {R.id.ss_one, R.id.ss_two, R.id.ss_three, R.id.ss_four, R.id.ss_four};
    int j,position;
    String AppName, AppType;
    String[] dataModels = new String[10];

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showcase_layout_design);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();

            AppName = extras.getString("AppName");
            AppType = extras.getString("AppType");

        } else {
            AppType = (String) savedInstanceState.getSerializable("AppName");
            AppType = (String) savedInstanceState.getSerializable("AppType");
        }

        new AppDataFetch().execute();

        ScreenShotOne = (ImageView) findViewById(R.id.ss_one);
        ScreenShotTwo = (ImageView) findViewById(R.id.ss_two);
        ScreenShotThree = (ImageView) findViewById(R.id.ss_three);
        ScreenShotFour = (ImageView) findViewById(R.id.ss_four);
        ScreenShotFive = (ImageView) findViewById(R.id.ss_five);
        AppLogo = (ImageView) findViewById(R.id.app_logo);
        Cart = (ImageView) findViewById(R.id.add_to_cart);
        Playstore = (ImageView) findViewById(R.id.playstore);
        Appname = (TextView) findViewById(R.id.app_title);
        Apptype = (TextView) findViewById(R.id.app_type);
        ApproxTime = (TextView) findViewById(R.id.time_required);
        Overview = (TextView) findViewById(R.id.app_description);
        Clients = (TextView) findViewById(R.id.clients_using);
        ScreenShotOne.setOnClickListener(this);
        ScreenShotTwo.setOnClickListener(this);
        ScreenShotThree.setOnClickListener(this);
        ScreenShotFour.setOnClickListener(this);
        ScreenShotFive.setOnClickListener(this);

        Cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListModel.setList(dataModels[4], dataModels[0], dataModels[2], "On Demand", "-", 1);
                Toast.makeText(SoftwareDetails.this, "Added to Cart", Toast.LENGTH_SHORT).show();
            }
        });

        Playstore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!dataModels[5].equals("anyType{}")) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(dataModels[5])));
                }else Toast.makeText(SoftwareDetails.this, "Coming Soon...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {

        j = (v.getId() == R.id.ss_one ? 0 : v.getId() == R.id.ss_two ? 1 : v.getId() == R.id.ss_three ? 2 : v.getId() == R.id.ss_four ? 3 : 4);

        switch (v.getId()) {

            case R.id.ss_one:
            case R.id.ss_two:
            case R.id.ss_three:
            case R.id.ss_four:
            case R.id.ss_five:

                final Dialog nagDialog = new Dialog(SoftwareDetails.this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
                nagDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                nagDialog.setContentView(R.layout.dialog_full_image);
                ImageView ivPreview = (ImageView) nagDialog.findViewById(R.id.imageView1);
                ImageView ivCancle = (ImageView) nagDialog.findViewById(R.id.cancle);
                Picasso.with(SoftwareDetails.this)
                        .load(dataModels[7].split(",")[j])
                        .into(ivPreview);
                nagDialog.show();
                nagDialog.setCancelable(true);
                ivCancle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        nagDialog.dismiss();
                    }
                });
                break;
        }
    }

    private class AppDataFetch extends AsyncTask<Void, Void, Void>

    {
        ProgressDialog progressDialog;
        Map<String, String> mapProps;
        boolean success = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mapProps = new HashMap<>();
            mapProps.put("AppName",AppName);
            mapProps.put("AppType",AppType);
            progressDialog = new ProgressDialog(SoftwareDetails.this);
            progressDialog.setMessage("Please wait while we load the list");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            SoapObject response = WebService.invokeWSWithProperties(mapProps, null, "GetAllAppData");

            if (response != null && response.getPropertyCount() > 0) {
                success = true ;
                for (int i = 0; i < response.getPropertyCount(); i++) {

                    SoapObject obj = (SoapObject) response.getProperty(i);

                    dataModels[0] = obj.getProperty("Quality").toString();
                    dataModels[1] = obj.getProperty("Design").toString();
                    dataModels[2] = obj.getProperty("Shade").toString();
                    dataModels[3] = obj.getProperty("Meters").toString();
                    dataModels[4] = obj.getProperty("Rate").toString();
                    dataModels[5] = obj.getProperty("Amount").toString();
                    dataModels[6] = obj.getProperty("TotalAmount").toString();
                    dataModels[7] = obj.getProperty("Discount").toString();
                    dataModels[8] = obj.getProperty("VatAmount").toString();
                    dataModels[9] = obj.getProperty("OtherCharges").toString();

                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();
            if(success) {
                Picasso.with(SoftwareDetails.this)
                        .load(dataModels[7].split(",")[0])
                        .resize(400, 800)
                        .into(ScreenShotOne);

                Picasso.with(SoftwareDetails.this)
                        .load(dataModels[7].split(",")[1])
                        .resize(400, 800)
                        .into(ScreenShotTwo);

                Picasso.with(SoftwareDetails.this)
                        .load(dataModels[7].split(",")[2])
                        .resize(400, 800)
                        .into(ScreenShotThree);

                Picasso.with(SoftwareDetails.this)
                        .load(dataModels[7].split(",")[3])
                        .resize(400, 800)
                        .into(ScreenShotFour);

                Picasso.with(SoftwareDetails.this)
                        .load(dataModels[7].split(",")[4])
                        .resize(400, 800)
                        .into(ScreenShotFive);

                Picasso.with(SoftwareDetails.this)
                        .load(dataModels[4])
                        .into(AppLogo);

                Appname.setText(dataModels[0]);
                Apptype.setText(dataModels[2]);
                ApproxTime.setText(dataModels[6]);
                Overview.setText(dataModels[8]);
                Clients.setText(dataModels[9]);
            }
        }
    }


}
