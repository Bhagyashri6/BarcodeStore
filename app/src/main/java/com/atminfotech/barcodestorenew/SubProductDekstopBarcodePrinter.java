package com.atminfotech.barcodestorenew;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.HashMap;

public class SubProductDekstopBarcodePrinter extends AppCompatActivity {

    //private int[] IMAGEgrid = {R.mipmap.gt800, R.mipmap.gc420t};
    private String[] IMAGEgrid = {"http://atm-india.in/img/CoverImages/gt800.png", "http://atm-india.in/img/CoverImages/gc420t.png"};
    private String[] TITLeGgrid = {"GT 800", "GC 420t"};
    private String[] Dategrid = {"Explore Now!","Grab Now!"};
    String imei;
    String name,mobile,str,email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub_product_dekstop_barcode_printer);

        //********GRIDVIEW***********

        SessionManagement session =new SessionManagement(SubProductDekstopBarcodePrinter.this);
        HashMap<String, String> user = session.getUserDetails();

        name = user.get(SessionManagement.KEY_NAME);
        mobile = user.get(SessionManagement.KEY_Mobile);
        email = user.get(SessionManagement.KEY_EmailId);
        imei=user.get(SessionManagement.KEY_Imei);

        ExpandableHeightGridView gridview = (ExpandableHeightGridView) findViewById(R.id.gridview);
        ArrayList<Beanclass> beanclassArrayList = new ArrayList<>();

        for (int i= 0; i< IMAGEgrid.length; i++) {

            Beanclass beanclass = new Beanclass(IMAGEgrid[i], TITLeGgrid[i], Dategrid[i]);
            beanclassArrayList.add(beanclass);

        }
        GridviewAdapter gridviewAdapter = new GridviewAdapter(SubProductDekstopBarcodePrinter.this, beanclassArrayList);
        gridview.setExpanded(true);

        gridview.setAdapter(gridviewAdapter);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                    Intent i = new Intent(SubProductDekstopBarcodePrinter.this,ProductDetails.class);
                    i.putExtra("position",position);
                    i.putExtra("Class","desktop");
                    i.putExtra("CompanyName","Zebra Desktop Barcode Printers");
                    i.putExtra("ModelNo",TITLeGgrid[position]);
                    startActivity(i);

            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_home,menu);
        return true;
    }

    public void onHomeClicked(MenuItem menu){
        startActivity(new Intent(SubProductDekstopBarcodePrinter.this,HomeActivity.class));
        finish();
    }

    @Override
    public void onBackPressed() {

    }
}
