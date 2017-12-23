package com.atminfotech.barcodestorenew;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity
{
    EditText mail,mophone,usrusr,gst;
    Button sup;
    String className;
    TextView txtGetImei;
    String str;

    String NAMESPACE = "http://tempuri.org/";
    String URL = "http://atm-india.in/Service.asmx";
    String MethodName = "InsertAndroidUser";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        if (savedInstanceState == null) {
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            className = null;
        } else {
            className = extras.getString("ClassName");
        }
        } else {
        className = (String) savedInstanceState.getSerializable("ClassName");
        }

        init();

        sup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sup.setEnabled(false);
                if(TextUtils.isEmpty(mophone.getText())){
                    mophone.setError("This field is required");
                    sup.setEnabled(true);
                } else if(TextUtils.isEmpty(usrusr.getText())){
                    usrusr.setError("This field is required");
                    sup.setEnabled(true);
                }else if(TextUtils.isEmpty(mail.getText())){
                    mail.setError("This field is required");
                    sup.setEnabled(true);
                }else new ValidateUser().execute();
            }
        });
    }

    private void init() {

        sup = (Button) findViewById(R.id.sup);
        usrusr = (EditText) findViewById(R.id.user);
        mail = (EditText) findViewById(R.id.mail);
        mophone = (EditText) findViewById(R.id.mobphone);
        gst =(EditText) findViewById(R.id.gst);

        txtGetImei = (TextView) findViewById(R.id.txtgetimei);
        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        str = getDeviceID(tm);
        txtGetImei.setText(str);

        Typeface custom_font  = Typeface.createFromAsset(getAssets(), "fonts/LatoLight.ttf");
        Typeface custom_font1 = Typeface.createFromAsset(getAssets(), "fonts/LatoRegular.ttf");
        mophone.setTypeface(custom_font);
        sup.setTypeface(custom_font1);
        usrusr.setTypeface(custom_font);
        mail.setTypeface(custom_font);
        gst.setTypeface(custom_font);

    }

    private class ValidateUser extends AsyncTask<Void,Void,Void>{

        ProgressDialog progressDialog;
        TelephonyManager tm ;
        boolean success = false;
        String  mobile,name,email,str,gst1;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(RegisterActivity.this);
            progressDialog.setMessage("Please Wait while we Board you in..");
            progressDialog.setCancelable(false);
            progressDialog.show();

            //tm = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
            txtGetImei = (TextView) findViewById(R.id.txtgetimei);
            TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            str = getDeviceID(tm);
            txtGetImei.setText(str);

            mobile = mophone.getText().toString();
            name =  usrusr.getText().toString();
            email = mail.getText().toString();
            gst1 =gst.getText().toString();
            str =txtGetImei.getText().toString();

        }

        @Override
        protected Void doInBackground(Void... params) {

            SoapObject request = new SoapObject(NAMESPACE,MethodName);

            request.addProperty("mobile",mobile);
            request.addProperty("name",name);
            request.addProperty("email",email);
            request.addProperty("imei",str);
            request.addProperty("gst",gst1);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet= true;
            envelope.setOutputSoapObject(request);

            HttpTransportSE httpTransportSe = new HttpTransportSE(URL);
            try {

                httpTransportSe.call(NAMESPACE+MethodName,envelope);
                SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
                if(response.toString().equals("success")){
                    success = true;
                }

            }catch (Exception e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();
            if(success) {
                if(className.equals("CartActivity")) {
                  //  startActivity(new Intent(RegisterActivity.this, CartActivity.class));
                    Intent intent =new Intent(RegisterActivity.this,CartActivity.class);
                    SessionManagement session = new SessionManagement(getApplicationContext());
                    Map<String, String> map = new HashMap<>();
                    map.put("name", name);   //  ("name=(that use from web service in paas string name ", name=(that use from database colom name for this)
                    map.put("mobileNo", mobile);
                    map.put("email",email);
                    map.put("imeiNo", str);
                    session.createRegisterationSession(name,mobile,str,email);


                    startActivity(intent);
                }
                else
                {
                    Intent intent =new Intent(RegisterActivity.this,Rma.class);

                    startActivity(intent);
                }


            } else {
                Toast.makeText(RegisterActivity.this,
                        "There was some error please try again later", Toast.LENGTH_LONG).show();
                sup.setEnabled(true);
            }
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
    public void onBackPressed() {

    }
}
