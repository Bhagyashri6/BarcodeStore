package com.atminfotech.barcodestorenew;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AndroidFragment extends ListFragment implements AdapterView.OnItemClickListener {

    ArrayList<AppData> dataModels = new ArrayList<>();
    private static AppsAdapter adapter;

    public AndroidFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_android, container, false);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        new AppDataFetch().execute();
        getListView().setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent i = new Intent(getActivity(),SoftwareDetails.class);
        i.putExtra("AppName",dataModels.get(position).getSalesBillNo());
            i.putExtra("AppType","Android");
        startActivity(i);
    }

    private class AppDataFetch extends AsyncTask<Void, Void, Void>

    {
        ProgressDialog progressDialog;
        Map<String, String> mapProps;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mapProps = new HashMap<>();
            progressDialog = new ProgressDialog(getContext());
            progressDialog.setMessage("Please wait while we load the list");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            SoapObject response = WebService.invokeWSWithProperties(mapProps, null, "GetAppData");

            if (response != null && response.getPropertyCount() > 0) {

                for (int i = 0; i < response.getPropertyCount(); i++) {

                    SoapObject obj = (SoapObject) response.getProperty(i);

                    if(obj.getProperty("TransportName").toString().equals("Android")) {
                        dataModels.add(new AppData(obj.getProperty("SalesBillNo").toString(),
                                obj.getProperty("LRNumber").toString(),
                                obj.getProperty("Date").toString(),
                                obj.getProperty("TransportName").toString(),
                                obj.getProperty("TotalMeters").toString(),
                                obj.getProperty("TotalAmount").toString(),
                                obj.getProperty("TotalMeters").toString()));
                    }
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            adapter= new AppsAdapter(dataModels,getContext());
            setListAdapter(adapter);
            progressDialog.dismiss();
        }
    }
}

