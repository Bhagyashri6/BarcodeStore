package com.atminfotech.barcodestorenew;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Rma extends AppCompatActivity {
    private TableFixHeaders tableFixHeaders;
    private String[] tableHeaders = {"Bill Date", "Bill No", "Model", "Serial Number.", "ExpiresIn(Days)"};
    private static final String URL = "http://atm-india.in/Service.asmx";
    private static final String NAMESPACE = "http://tempuri.org/";
    private ProgressDialog ringProgressDialog;
    private AutoCompleteTextView ClientName;
    private FABToolbarLayout layout;
    private View Cancle, Enter;
    private String partyName;
    FloatingActionButton fab;
    RelativeLayout rl;
    boolean success;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rma);

        tableFixHeaders = (TableFixHeaders) findViewById(R.id.table);
        layout = (FABToolbarLayout) findViewById(R.id.fabtoolbar);
        rl = (RelativeLayout) findViewById(R.id.fabtoolbar_container);
        fab = (FloatingActionButton) findViewById(R.id.fabtoolbar_fab);
        rl.setVisibility(View.GONE);
        Cancle = findViewById(R.id.cancel);
        Enter = findViewById(R.id.enter);
        ClientName = (AutoCompleteTextView) findViewById(R.id.party_name);

        new GetMobileNumber().execute();
        new GetPartyName().execute();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout.show();
            }
        });
        Cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClientName.setText("");
                layout.hide();
            }
        });
        Enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(ClientName.getText())) {
                    partyName = ClientName.getText().toString();
                    new SalesReportTask().execute();
                    layout.hide();
                } else
                    Toast.makeText(Rma.this, "Enter Client Name First", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public class MyAdapter extends SampleTableAdapter {

        private String[] tableHeaderList;
        private final int widthMedium;
        private final int height;
        private ArrayList<ModelStockReport> list = new ArrayList<>();

        public MyAdapter(Context context, String[] tableHeaderList, ArrayList<ModelStockReport> list) {
            super(context);

            this.tableHeaderList = tableHeaderList;
            this.list = list;

            Resources resources = context.getResources();

            widthMedium = resources.getDimensionPixelSize(R.dimen.table_width_large);
            height = resources.getDimensionPixelSize(R.dimen.table_height);
        }

        @Override
        public int getRowCount() {
            return list.size();
        }

        @Override
        public int getColumnCount() {
            return tableHeaderList.length - 1;
        }

        @Override
        public int getWidth(int column) {
            return widthMedium;
        }

        @Override
        public int getHeight(int row) {
            return height;
        }

        @Override
        public String getCellString(int row, int column) {
            if (getItemViewType(row, column) == 0) {
                return tableHeaderList[column + 1];
            }

            return getColumnString(list.get(row), column + 1);
        }

        private String getColumnString(ModelStockReport modelSalesReport, int column) {
            switch (column) {
                case 0:
                    return modelSalesReport.getPurDate();
                case 1:
                    return modelSalesReport.getBrand();
                case 2:
                    return modelSalesReport.getName();
                case 3:
                    return modelSalesReport.getQty();
                case 4:
                    return modelSalesReport.getValues();
            }
            return null;
        }

        @Override
        public int getLayoutResource(int row, int column) {
            final int layoutResource;
            switch (getItemViewType(row, column)) {
                case 0:
                    layoutResource = R.layout.item_table1_header;
                    break;
                case 1:
                    layoutResource = R.layout.item_table_center_even;
                    break;
                case 2:
                    layoutResource = R.layout.item_table_center_odd;
                    break;
                default:
                    throw new RuntimeException("wtf?");
            }
            return layoutResource;
        }

        @Override
        public int getItemViewType(int row, int column) {

            if (row < 0) {
                return 0;
            } else if (row % 2 == 0) {
                return 1;
            }
            return 2;
        }

        @Override
        public int getViewTypeCount() {
            return 3;
        }
    }

    private class SalesReportTask extends AsyncTask<Void, Void, Void> {

        private ArrayList<ModelStockReport> list = new ArrayList<>();

        @Override
        protected Void doInBackground(Void... params) {

            Map<String, String> props = new HashMap<String, String>();
            props.put("PartyName", partyName);
            SoapObject response = WebService.invokeWSWithProperties(props, null, "GetRmaReport");

            if (response != null && !response.toString().equals("anyType{}")) {
                success = true;
                list = getList(response);
            }

            return null;
        }

        private ArrayList<ModelStockReport> getList(SoapObject response) {

            ArrayList<ModelStockReport> list = new ArrayList<>();

            for (int i = 0; i < response.getPropertyCount(); i++) {

                SoapObject innerResponse = (SoapObject) response.getProperty(i);

                ModelStockReport model = new ModelStockReport(
                        innerResponse.getProperty("Date").toString().equals("anyType{}") ? "-" : innerResponse.getProperty("Date").toString(),
                        innerResponse.getProperty("point").toString().equals("anyType{}") ? "-" : innerResponse.getProperty("point").toString(),
                        innerResponse.getProperty("Point_Type").toString().equals("anyType{}") ? "-" : innerResponse.getProperty("Point_Type").toString(),
                        innerResponse.getProperty("RefMob").toString().equals("anyType{}") ? "-" : innerResponse.getProperty("RefMob").toString(),
                        innerResponse.getProperty("ExpDate").toString().equals("anyType{}") ? "-" : innerResponse.getProperty("ExpDate").toString()
                );

                list.add(model);

            }
            return list;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ringProgressDialog = ProgressDialog.show(Rma.this, "Please wait ...", "Report is being generated...", true);
            ringProgressDialog.setCancelable(true);
            ringProgressDialog.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            ringProgressDialog.dismiss();
            if (success) {
                tableFixHeaders.setAdapter(new MyAdapter(Rma.this, tableHeaders, list));
            } else Toast.makeText(Rma.this, "No data present", Toast.LENGTH_LONG).show();
        }
    }

    private class GetPartyName extends AsyncTask<Void, Void, Void> {
        ArrayList<String> list = new ArrayList<>();
        boolean success;

        @Override
        protected Void doInBackground(Void... params) {
            try {

                SoapObject request = new SoapObject(NAMESPACE, "GetAllPartyName");

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

                envelope.dotNet = true;

                envelope.setOutputSoapObject(request);
                HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

                androidHttpTransport.call("http://tempuri.org/GetAllPartyName", envelope);

                SoapObject objs = (SoapObject) envelope.getResponse();
                int count = objs.getPropertyCount();
                if (!objs.toString().isEmpty()) {
                    success = true;
                    for (int i = 0; i < count; i++) {
                        SoapPrimitive obj = (SoapPrimitive) objs.getProperty(i);
                        list.add(obj.toString());
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (success) {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(Rma.this,
                        R.layout.support_simple_spinner_dropdown_item, list);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                ClientName.setAdapter(adapter);
            }
        }
    }

    private class GetMobileNumber extends AsyncTask<Void, Void, Void> {
        boolean success;

        @Override
        protected Void doInBackground(Void... params) {
            try {
                TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

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
                    success = true;
                    partyName = objs.toString();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (success) {
                if (partyName.equals("Admin")) {
                    rl.setVisibility(View.VISIBLE);
                } else {
                    new SalesReportTask().execute();
                }
            }
        }
    }

    String getDeviceID(TelephonyManager phonyManager) {

        String id = phonyManager.getDeviceId();
        if (id == null) {
            id = "not available";
        }

        int phoneType = phonyManager.getPhoneType();
        switch (phoneType) {
            case TelephonyManager.PHONE_TYPE_NONE:
                return id;

            case TelephonyManager.PHONE_TYPE_GSM:
                return id;

            case TelephonyManager.PHONE_TYPE_CDMA:
                return id;

            default:
                return id;
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_home,menu);
        return true;
    }

    public void onHomeClicked(MenuItem menu){
        startActivity(new Intent(Rma.this,HomeActivity.class));
        finish();
    }

    @Override
    public void onBackPressed() {

    }
}
