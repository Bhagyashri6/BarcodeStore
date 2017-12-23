package com.atminfotech.barcodestorenew;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CartActivity extends AppCompatActivity implements
        AdapterView.OnItemClickListener,ConnectivityReceiver.ConnectivityReceiverListener{

    private static String url = "http://atm-india.in/Service.asmx";
    private static final String URL = "http://atm-india.in/Service.asmx";
    private static final String NAMESPACE = "http://tempuri.org/";
    private static final String Register_SOAP_ACTION1 = "http://tempuri.org/GetDataInfo";


    SessionManagement session;
    public ListView listView;
    Button book_order;
    CustomListViewAdapter adapter;
    TextView Total_Price, Tax, Final_Amount;
    double total_price = 0.0, tax = 0.0, final_amount = 0.0;
    String mobile,name,imei;
    Date date;
    boolean execute=false;
    View child=null;
    RelativeLayout item = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        checkConnection();

        new GetMobileNumber().execute();
        new GetName().execute();

        Total_Price = (TextView) findViewById(R.id.txt_total_price);
        Tax = (TextView) findViewById(R.id.txt_taxes);
        Final_Amount = (TextView) findViewById(R.id.txt_final_amount);
        if (!(ListModel.getList().size() == 0)) {
            for (int i = 0; i < ListModel.getList().size(); i++) {
                if (!(ListModel.getList().get(i).getAmount().contentEquals("-"))){
                    total_price = total_price + Double.parseDouble(ListModel.getList().get(i).getAmount());
                }/*else  if (!(ListModel.getList().get(i).getAmount().contentEquals("-"))){
                    total_price = total_price + Double.parseDouble(ListModel.getList().get(i).getAmount());
                }*/

            }
        }

        //tax = total_price * 0.06;
        //shri cxhange cst rate .18
        tax = total_price * 0.18;
        final_amount = total_price + tax;

        Total_Price.setText("" + (total_price==0.0?"On Request":total_price));
        Tax.setText("" + tax);
        Final_Amount.setText("" + (final_amount==0.0?"On Request":final_amount));

        book_order = (Button) findViewById(R.id.bCart);
        book_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                imei =getDeviceID(tm);
                new IsLoggedIn().execute();
            }
        });
        listView = (ListView) findViewById(R.id.lst_cart);
        adapter = new CustomListViewAdapter(this,R.layout.row_cart, ListModel.getList());
        listView.setAdapter(adapter);
        setListViewHeightBasedOnChildren(listView);
        listView.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position,
                            long id) {

        ImageView iv_cancel = (ImageView) view.findViewById(R.id.img_cancel);
        ImageView IncreaseQuantity = (ImageView) view.findViewById(R.id.quantity_increase);
        ImageView DecreaseQuantity = (ImageView) view.findViewById(R.id.quantity_decrease);
        final TextView Quantity = (TextView) view.findViewById(R.id.txt_qty);

        iv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                adapter.remove(adapter.getItem(position));
                adapter.notifyDataSetChanged();
                listView.setAdapter(adapter);
                setListViewHeightBasedOnChildren(listView);

                if (!(ListModel.getList().size() == 0)) {
                    total_price = 0;
                    for (int i = 0; i < ListModel.getList().size(); i++) {
                        if (!(ListModel.getList().get(i).getAmount().contentEquals("-")))
                            total_price = total_price + Double.parseDouble(ListModel.getList().get(i).getAmount());
                    }

                    //tax = total_price * 0.06;
                    //shri change gst rate .18
                    tax = total_price * 0.18;
                    final_amount = total_price + tax;

                    Total_Price.setText("" + (total_price==0.0?"On Request":total_price));
                    Tax.setText("" + tax);
                    Final_Amount.setText("" + (final_amount==0.0?"On Request":final_amount));

                } else {

                    Total_Price.setText("0");
                    Tax.setText("0");
                    Final_Amount.setText("0");

                    Toast.makeText(CartActivity.this, "Your Cart is Empty...", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(CartActivity.this, HomeActivity.class));
                }
            }
        });

        IncreaseQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListModel.getList().get(position).setQty(((ListModel.getList().get(position).getQty())+1));
                Quantity.setText(String.valueOf(ListModel.getList().get(position).getQty()));
                if(!ListModel.getList().get(position).getRate().contentEquals("On Demand")) {
                    ListModel.getList().get(position).setAmount(String.valueOf((ListModel.getList().get(position).getQty()) * Double.parseDouble(ListModel.getList().get(position).getRate())));
                }

                if (!(ListModel.getList().size() == 0)) {
                    total_price = 0;
                    for (int i = 0; i < ListModel.getList().size(); i++) {
                        if (!(ListModel.getList().get(i).getAmount().contentEquals("-")))
                            total_price = total_price + Double.parseDouble(ListModel.getList().get(i).getAmount());
                    }

                    //tax = total_price * 0.06;
                    //shri change gst rate .18
                    tax = total_price * 0.18;
                    final_amount = total_price + tax;

                    Total_Price.setText("" + (total_price==0.0?"On Request":total_price));
                    Tax.setText("" + tax);
                    Final_Amount.setText("" + (final_amount==0.0?"On Request":final_amount));

                }
            }
        });

        DecreaseQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListModel.getList().get(position).setQty(((ListModel.getList().get(position).getQty())-1));
                Quantity.setText(String.valueOf(ListModel.getList().get(position).getQty()));
                if(!ListModel.getList().get(position).getRate().contentEquals("On Demand")) {
                    ListModel.getList().get(position).setAmount(String.valueOf((ListModel.getList().get(position).getQty()) * Double.parseDouble(ListModel.getList().get(position).getRate())));
                }

                if (!(ListModel.getList().size() == 0)) {
                    total_price = 0;
                    for (int i = 0; i < ListModel.getList().size(); i++) {
                        if (!(ListModel.getList().get(i).getAmount().contentEquals("-")))
                            total_price = total_price + Double.parseDouble(ListModel.getList().get(i).getAmount());
                    }

                    //tax = total_price * 0.06;
                    //shri change gst rate .18
                    tax = total_price * 0.18;
                    final_amount = total_price + tax;

                    Total_Price.setText("" + (total_price==0.0?"On Request":total_price));
                    Tax.setText("" + tax);
                    Final_Amount.setText("" + (final_amount==0.0?"On Request":final_amount));

                }

                if(ListModel.getList().get(position).getQty() == 0)
                {
                    adapter.remove(adapter.getItem(position));
                    adapter.notifyDataSetChanged();
                    listView.setAdapter(adapter);
                    setListViewHeightBasedOnChildren(listView);
                }

                if ((ListModel.getList().size() == 0)) {
                    Toast.makeText(CartActivity.this, "Your Cart is Empty...", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(CartActivity.this, HomeActivity.class));
                }
            }
        });

        //if(view.getId() == getResId("img_cancel",CartActivity.class)) {
    }


    private class BookOrder extends AsyncTask<Void, Void, Void> {
        ProgressDialog progressDialog;
        boolean success = false;
        String date;

        public BookOrder(String date) {
            this.date = date;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(CartActivity.this);
            progressDialog.setMessage("Please wait while we book your order...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            SoapObject request = new SoapObject("http://tempuri.org/", "BookOrder");

            request.addProperty("OrderNumber", date);
            request.addProperty("ClientName", name);
            request.addProperty("MobileNumber", mobile);
            request.addProperty("TotalAmount", String.valueOf(total_price));
            request.addProperty("TotalVat", String.valueOf(tax));
            request.addProperty("NetAmount", String.valueOf(final_amount));

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);

            try {

                HttpTransportSE httpsTransportSE = new HttpTransportSE(url);
                httpsTransportSE.call("http://tempuri.org/BookOrder", envelope);

                SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
                if (response.toString().contentEquals("true")) {
                    success = true;
                }

                String[] sol =request.toString().split(",",-1);
                SessionManagement session =new SessionManagement(CartActivity.this);
                session.createRegisterationSession(sol[0],sol[1],sol[2],sol[3]);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();
            if (success) {
                new SendSMSTask().execute();
                startActivity(new Intent(CartActivity.this, HomeActivity.class));
                ListModel.getList().clear();
                Toast.makeText(CartActivity.this, "Your Order was Booked Successfully...Our Team will contact you for the confirmation within 24hrs...", Toast.LENGTH_LONG).show();
                }
        }
    }

    private class BookSubOrder extends AsyncTask<Void, Void, Void> {
        ProgressDialog progressDialog;
        boolean success = false;
        int position;
        double Amount;
        String date;

        public BookSubOrder(int i,String date) {
            position = i;
            this.date = date;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(CartActivity.this);
            progressDialog.setMessage("Please wait while we book your order...");
            progressDialog.setCancelable(false);
            progressDialog.show();
            //Amount = Integer.valueOf(ListModel.getList().get(position).getRate())*0.06*ListModel.getList().get(position).getQty()+Integer.valueOf(ListModel.getList().get(position).getRate())*ListModel.getList().get(position).getQty();

            //shri change gst rate
            Amount = Integer.valueOf(ListModel.getList().get(position).getRate())*0.18*ListModel.getList().get(position).getQty()+Integer.valueOf(ListModel.getList().get(position).getRate())*ListModel.getList().get(position).getQty();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            SoapObject request = new SoapObject("http://tempuri.org/", "BookSubOrder");

            request.addProperty("OrderNumber", date);
            request.addProperty("ProductName", ListModel.getList().get(position).getTitle()+" "+ListModel.getList().get(position).getDesc());
            request.addProperty("Qty", ListModel.getList().get(position).getQty());
            request.addProperty("Rate", ListModel.getList().get(position).getRate());
            request.addProperty("Amount", String.valueOf(Integer.valueOf(ListModel.getList().get(position).getRate())*ListModel.getList().get(position).getQty()));
            request.addProperty("TaxName", "VAT");
            request.addProperty("TaxPercent", "18");
            /*request.addProperty("TaxAmount", String.valueOf(Integer.valueOf(ListModel.getList().get(position).getRate())*0.06));
            request.addProperty("TotalTax", String.valueOf(Integer.valueOf(ListModel.getList().get(position).getRate())*0.06*ListModel.getList().get(position).getQty()));*/

            //shri change gst rate to .18
            request.addProperty("TaxAmount", String.valueOf(Integer.valueOf(ListModel.getList().get(position).getRate())*0.18));
            request.addProperty("TotalTax", String.valueOf(Integer.valueOf(ListModel.getList().get(position).getRate())*0.18*ListModel.getList().get(position).getQty()));
            request.addProperty("TotalAmount", String.valueOf(Amount));

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);

            try {

                HttpTransportSE httpsTransportSE = new HttpTransportSE(url);
                httpsTransportSE.call("http://tempuri.org/BookSubOrder", envelope);

                SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
                if (response.toString().contentEquals("true")) {
                    success = true;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
                progressDialog.dismiss();
            }
    }

    public static void setListViewHeightBasedOnChildren(ListView listView)
    {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight=0;
        View view = null;

        for (int i = 0; i < listAdapter.getCount(); i++)
        {
            view = listAdapter.getView(i, view, listView);

            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth,
                        ViewGroup.LayoutParams.MATCH_PARENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();

        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + ((listView.getDividerHeight()) * (listAdapter.getCount()));

        listView.setLayoutParams(params);
        listView.requestLayout();

    }

    protected class IsLoggedIn extends AsyncTask<Void, Void, Void> {

        boolean success=false;

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

                SoapObject request = new SoapObject(NAMESPACE, "IsLoggedIn");

                request.addProperty("methodName", "IsLoggedIn");

                request.addProperty("imei",imei);

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

                envelope.dotNet = true;

                envelope.setOutputSoapObject(request);
                HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

                androidHttpTransport.call("http://tempuri.org/IsLoggedIn", envelope);

                SoapPrimitive objs = (SoapPrimitive) envelope.getResponse();

                if (objs.toString().contentEquals("UserAlreadyExist")) {
                    GetData();
                    success = true;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(!success)
            {  new MaterialStyledDialog.Builder(CartActivity.this)
                    .setTitle("Register")
                    .setDescription("Your are still not a member with us plz Register for become member")
                    .setIcon(R.mipmap.ic_launcher)
                    .setPositiveText("Yes")
                    .withIconAnimation(true)
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                             /*   moveTaskToBack(true);

                                android.os.Process.killProcess(android.os.Process.myPid());
                                System.exit(1);*/
                            Intent i = new Intent (CartActivity.this,RegisterActivity.class);
                            i.putExtra("ClassName","CartActivity");
                            startActivity(i);
                        }
                    })
                    .setNegativeText("NO")
                    .onNegative(new MaterialDialog.SingleButtonCallback(){
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            dialog.dismiss();
                        }
                    })
                    .setCancelable(true)
                    .show();

            }else{
                execute = true;
                new GetMobileNumber().execute();
                new GetName().execute();
            }
        }
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

                    mobile = objs.toString();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }
    }

    protected class GetName extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... params) {
            try {
                TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

               /* SoapObject request = new SoapObject(NAMESPACE, "" +
                        "");*/

                SoapObject request = new SoapObject(NAMESPACE, "GetName");


                request.addProperty("methodName", "GetName");

                request.addProperty("imei", getDeviceID(tm));



                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

                envelope.dotNet = true;

                envelope.setOutputSoapObject(request);
                HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

                androidHttpTransport.call("http://tempuri.org/GetName", envelope);

                SoapPrimitive objs = (SoapPrimitive) envelope.getResponse();

                if (!objs.toString().contentEquals("UserDoesNotExist")) {

                    name = objs.toString();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(execute) {
                Date d = new Date();
                final String date = new SimpleDateFormat("yyyyMMddHHmmssS").format(d);
                for (int i = 0; i < ListModel.getList().size(); i++) {
                    new BookSubOrder(i, date).execute();
                }
                new BookOrder(date).execute();
                execute = false;
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
    private void GetData() {
        try{
            SoapObject request = new SoapObject(NAMESPACE, "GetDataInfo");

            request.addProperty("imei", imei);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);
            envelope.dotNet = true;

            envelope.setOutputSoapObject(request);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

            androidHttpTransport.call(Register_SOAP_ACTION1, envelope);

            SoapPrimitive objs = (SoapPrimitive) envelope.getResponse();
           // progressDialog.dismiss();
            String[] sol = objs.toString().split(",",-1);
            SessionManagement session = new SessionManagement(CartActivity.this);
          //  Intent i = new Intent(CartActivity.this, HomeActivity.class);
            session.createRegisterationSession(sol[0], sol[1], sol[2], sol[3]);
          //
            //  startActivity(i);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private class SendSMSTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {

                try {
                    // Create http cliient object to send request to server

                    HttpClient Client = new DefaultHttpClient();

                    // Create URL string

                    try {
                        String SetServerString = "";

                        String msg = URLEncoder.encode("Your Order was Booked Successfully...Our Team will contact you for the confirmation shortly for processing the order ahead...", "UTF-8");
                        //String URL = "http://fast.admarksolution.com/vendorsms/pushsms.aspx?user=MUMATM&password=abc123&msisdn=91"+mobile+"&sid=ERHINO&msg="+msg+"&fl=0&gwid=2";
                        String URL = "http://fast.admarksolution.com/vendorsms/pushsms.aspx?user=MUMATM&password=abc123&msisdn=91"+mobile+"&sid=MUMATM&msg="+msg+"&fl=0&gwid=2";
                        // Create Request to server and get response

                        HttpGet httpget = new HttpGet(URL);
                        ResponseHandler<String> responseHandler = new BasicResponseHandler();
                        SetServerString = Client.execute(httpget, responseHandler);

                        SetServerString += "";
                    } catch (Exception ex) {
                        //content.setText("Fail!");
                        ex.printStackTrace();
                    }

                } catch (Exception ex) {
                    //Toast.makeText(getApplicationContext(), "Sorry! Your sms sending failed...",Toast.LENGTH_LONG).show();
                    ex.printStackTrace();
                }

            return null;
        }
    }

    private void checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        showSnack(isConnected);
    }

    private void showSnack(boolean isConnected) {

        if (!isConnected) {
            item = (RelativeLayout)findViewById(R.id.no_internet_container);
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
        CustomFonts.getInstance().setConnectivityListener(this);
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showSnack(isConnected);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_home,menu);
        return true;
    }

    public void onHomeClicked(MenuItem menu){
        startActivity(new Intent(CartActivity.this,HomeActivity.class));
        finish();
    }
    @Override
    public void onBackPressed() {

    }
}
