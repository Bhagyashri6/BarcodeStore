package com.atminfotech.barcodestorenew;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class Warranty extends ListActivity  {

    private static final String URL = "http://atm-india.in/Service.asmx";

    private static final String NAMESPACE = "http://tempuri.org/";

    private static final String Register_SOAP_ACTION = "http://tempuri.org/GetComplainWarranty";

    Button saveButton;
    String pass[];
    boolean answer = false;
    int j=0,s=0;
    int count[]  ;
    EditText other;
    String companyName;
    AdapterView.OnItemClickListener myListViewClicked = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample_main);
        setListAdapter(new MyAdapter());

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        GetMobileNumber();

        other = (EditText)findViewById(R.id.others);
        pass = new String[13];
        count= new int[13];
        for(int i=0;i<count.length;i++)
        { count[i] = 0;}

        myListViewClicked = new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                if (position == 12){

                    if(other.getVisibility()==View.VISIBLE){

                        other.setVisibility(View.INVISIBLE);
                        s--;
                    }
                    else if(other.getVisibility()==View.INVISIBLE){

                        other.setVisibility(View.VISIBLE);
                        s++;
                    }}

                if (count[position] == 0) {
                    count[position] = 1;
                    s++;
                } else if (count[position] == 1) {
                    count[position] = 0;
                    s--;
                }


            }


        };

        ListView propertylistview = (ListView) findViewById(android.R.id.list);
        propertylistview.setOnItemClickListener(  myListViewClicked );

        saveButton= (Button)findViewById(R.id.saveb);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveButton.setEnabled(false);
                j = 0;
                for (int i = 0; i < pass.length; i++) {
                    pass[i] = null;
                }

                String otherproblem = null;
                for (int i = 0; i < count.length - 1; i++) {

                    if (count[i] == 1) {

                        pass[j] = Cheeses.CHEESES[i];
                        j++;
                    } else if (count[i] == 0) {
                        pass[j] = null;
                        j++;
                    }


                    if (count[12] == 1) {

                        otherproblem = other.getText().toString();

                        if (otherproblem.isEmpty()) {
                            saveButton.setEnabled(true);
                            } else {
                            pass[12] = otherproblem;
                            //s = 1;
                        }
                    }

                }

               if (s == 0) {

                   s=0;
                   Toast.makeText(Warranty.this, "You haven't selected any item", Toast.LENGTH_SHORT).show();
                    saveButton.setEnabled(true);
                } else {

                    GetComplain(pass);
                }

                if ((other.getVisibility()==View.VISIBLE)&&(otherproblem.isEmpty())) {
                    saveButton.setEnabled(true);

                    Toast.makeText(Warranty.this, "Mention your other problem", Toast.LENGTH_SHORT).show();

                } else if (!answer) {

                    saveButton.setEnabled(true);

                } else if(s!=0){

                    Show();
                }
            }
        });
    }

/*public class SendSMSTask extends AsyncTask<Void, Void, Void> {

    private ProgressDialog ringProgressDialog;
    boolean success;
    @Override
    protected Void doInBackground(Void... voids) {
        try {

            HttpClient Client = new DefaultHttpClient();

            try {
                String SetServerString = "";

                String msg = URLEncoder.encode("Your complaint was registered with ATM...We will get back to you soon","UTF-8");
                //String URL = "http://fast.admarksolution.com/vendorsms/pushsms.aspx?user=Guptapower&password=abc123&msisdn=91"+mobile+"&sid=ERHINO&msg="+msg+"&fl=0&gwid=2";
                String URL = "http://fast.admarksolution.com/vendorsms/pushsms.aspx?user=MUMATM&password=abc123&msisdn=91" + credentials[1] + "&sid=MUMATM&msg=" + msg + "&fl=0&gwid=2";
                // Create Request to server and get response

                HttpGet httpget = new HttpGet(URL);
                ResponseHandler<String> responseHandler = new BasicResponseHandler();
                SetServerString = Client.execute(httpget, responseHandler);

            } catch (Exception ex) {
                ex.printStackTrace();
            }

            success = true;
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), "Sorry! Your sms sending failed...",
                    Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {

        ringProgressDialog.dismiss();
        if(success){
            Show();
        }else{
            Toast.makeText(Warranty.this, "Problem in sending message..Please try again..", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onPreExecute() {
        success = false;
        ringProgressDialog = ProgressDialog.show(Warranty.this, "Please wait ...", "Sending Request ...", true);
        ringProgressDialog.setCancelable(true);

    }
}*/

    private void Show() {

                        Intent in = new Intent(Warranty.this, Support.class);
                        startActivity(in);
                        finish();
    }

    protected void GetMobileNumber() {
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

                companyName = objs.toString();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

private class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return Cheeses.CHEESES.length;
        }

        @Override
        public String getItem(int position) {
            return Cheeses.CHEESES[position];
        }

        @Override
        public long getItemId(int position) {
            return Cheeses.CHEESES[position].hashCode();

        }

        @Override
        public View getView(final int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.list_item, container, false);
            }

            ((TextView) convertView.findViewById(android.R.id.text1))
                    .setText(getItem(position));

            return  convertView;
        }
    }

    protected void GetComplain(String abc[]) {
        try {
            SoapObject request = new SoapObject(NAMESPACE, "GetComplainWarranty");

            request.addProperty("methodName", "GetComplainWarranty");

            request.addProperty("co_name", companyName);
            for(int i=0; i<13;i++)
                request.addProperty("data"+(i+1),abc[i]);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

            envelope.dotNet = true;

            envelope.setOutputSoapObject(request);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

            androidHttpTransport.call(Register_SOAP_ACTION, envelope);

            SoapPrimitive objs = (SoapPrimitive) envelope.getResponse();

            if (objs.toString().contentEquals("true")) {
                answer = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
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
        startActivity(new Intent(Warranty.this,HomeActivity.class));
        finish();
    }

    @Override
    public void onBackPressed() {

    }
}
