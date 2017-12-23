package com.atminfotech.barcodestorenew;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.Arrays;

public class NonWarrantyIntermediate extends AppCompatActivity {

    private static final String URL = "http://atm-india.in/Service.asmx";

    private static final String NAMESPACE = "http://tempuri.org/";

    private static final String Register_SOAP_ACTIONN = "http://tempuri.org/GetModelNonWarranty";

    private static final String METHOD_NAMEN = "GetModelNonWarranty";

    private static final String Register_SOAP_ACTION2 = "http://tempuri.org/GetSerialNonWarranty";

    private static final String METHOD_NAMEN2 = "GetSerialNonWarranty";

    AutoCompleteTextView modelNo;
    Button submit;
    ImageView imageView;
    AutoCompleteTextView billNO, category;
    String item = "", companyname;
    String[] categories = new String[]{"Zebra Barcode Printers", "Zebra Barcode Scanners", "Mobile Scanners", "Mobile Computers", "Id Card Printers", "Id Card Accessories", "One Care Pack"}, serialNumbers, model;
    Snackbar snackbar;
    ArrayAdapter<String> adapter1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.non_warranty_intermediate);

        imageView = (ImageView) findViewById(R.id.imageViewnon);

        Picasso.with(NonWarrantyIntermediate.this)
                .load("http://atm-india.in/img/CoverImages/nonwarranty.png")
                .into(imageView);

        category = (AutoCompleteTextView) findViewById(R.id.Categorynon);

        modelNo = (AutoCompleteTextView) findViewById(R.id.nonmodelNo);

        billNO = (AutoCompleteTextView) findViewById(R.id.nonbillno);

        submit = (Button) findViewById(R.id.submit);

        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(NonWarrantyIntermediate.this, android.R.layout.simple_list_item_1, categories);

        category.setAdapter(adapter3);

        new GetMobileNumber().execute();

        modelNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (category.getText().toString().equals("")) {

                    snackbar = Snackbar.make(findViewById(android.R.id.content), "Please select category", Snackbar.LENGTH_LONG);
                    FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) snackbar.getView().getLayoutParams();
                    params.gravity = Gravity.TOP;
                    snackbar.getView().setLayoutParams(params);
                    snackbar.show();
                } else {
                    new GetModelNonWarranty(companyname).execute();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        billNO.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (modelNo.getText().toString().equals("")) {

                    snackbar = Snackbar.make(findViewById(android.R.id.content), "Please select model number", Snackbar.LENGTH_LONG);
                    FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) snackbar.getView().getLayoutParams();
                    params.gravity = Gravity.TOP;
                    snackbar.getView().setLayoutParams(params);
                    snackbar.show();
                } else {
                    new GetSrNo(companyname).execute();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!adapter1.isEmpty()) {
                    if (item.equals("")) {

                        snackbar = Snackbar.make(findViewById(android.R.id.content), "Please select model number", Snackbar.LENGTH_LONG);
                        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) snackbar.getView().getLayoutParams();
                        params.gravity = Gravity.TOP;
                        snackbar.getView().setLayoutParams(params);
                        snackbar.show();
                    } else if (billNO.getText().toString().isEmpty()) {
                        snackbar = Snackbar.make(findViewById(android.R.id.content), "Please select serial number", Snackbar.LENGTH_LONG);
                        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) snackbar.getView().getLayoutParams();
                        params.gravity = Gravity.TOP;
                        snackbar.getView().setLayoutParams(params);
                        snackbar.show();
                    } else if (Arrays.asList(model).contains(modelNo.getText().toString()) && Arrays.asList(serialNumbers).contains(billNO.getText().toString())) {
                        Intent i = new Intent(NonWarrantyIntermediate.this, Warranty.class);
                        startActivity(i);
                    } else {
                        snackbar = Snackbar.make(findViewById(android.R.id.content), "Oops..Serial number or Model number does not belongs to you.", Snackbar.LENGTH_LONG);
                        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) snackbar.getView().getLayoutParams();
                        params.gravity = Gravity.TOP;
                        snackbar.getView().setLayoutParams(params);
                        snackbar.show();
                    }
                }else Toast.makeText(NonWarrantyIntermediate.this, "Please purchase some item first.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    protected class GetMobileNumber extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... params) {
            try {
                TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

                SoapObject request = new SoapObject(NAMESPACE, "GetMobileNumber");

                request.addProperty("methodName", "GetMobileNumber");

                request.addProperty("imei", getDeviceID(tm));

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

                envelope.dotNet = true;

                envelope.setOutputSoapObject(request);
                HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

                androidHttpTransport.call("http://tempuri.org/GetMobileNumber", envelope);

                SoapPrimitive objs = (SoapPrimitive) envelope.getResponse();

                if (!objs.toString().contentEquals("UserDoesNotExist")) {

                    companyname = objs.toString();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }
    }


    private class GetModelNonWarranty extends AsyncTask<Void, Void, Void> {

        ArrayAdapter<String> adapter2;
        String companyname, categorry;

        GetModelNonWarranty(String companyname) {
            this.companyname = companyname;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            categorry = category.getText().toString();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAMEN);

                request.addProperty("co_name", companyname);
                request.addProperty("category", categorry);

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                        SoapEnvelope.VER11);
                envelope.dotNet = true;

                envelope.setOutputSoapObject(request);
                HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

                androidHttpTransport.call(Register_SOAP_ACTIONN, envelope);

                SoapPrimitive objs = (SoapPrimitive) envelope.getResponse();

                model = objs.toString().split(",");

                adapter2 = new ArrayAdapter<>(NonWarrantyIntermediate.this, android.R.layout.simple_list_item_1, model);

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            modelNo.setAdapter(adapter2);
            billNO.setVisibility(View.VISIBLE);
        }
    }


    private class GetSrNo extends AsyncTask<Void, Void, Void> {

        String companyname;

        GetSrNo(String companyname) {
            this.companyname = companyname;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            item = modelNo.getText().toString();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAMEN2);

                request.addProperty("co_name", companyname);
                request.addProperty("Model", item);

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                        SoapEnvelope.VER11);
                envelope.dotNet = true;

                envelope.setOutputSoapObject(request);
                HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);


                androidHttpTransport.call(Register_SOAP_ACTION2, envelope);

                SoapPrimitive objs = (SoapPrimitive) envelope.getResponse();

                serialNumbers = objs.toString().split(",");

                adapter1 = new ArrayAdapter<>(NonWarrantyIntermediate.this, android.R.layout.simple_list_item_1, serialNumbers);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            billNO.setAdapter(adapter1);
        }
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_home,menu);
        return true;
    }

    public void onHomeClicked(MenuItem menu){
        startActivity(new Intent(NonWarrantyIntermediate.this,HomeActivity.class));
        finish();
    }

    @Override
    public void onBackPressed() {

    }
}
