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

public class SubProductKIOSKBarcodePrinters extends AppCompatActivity {
    String imei;
    String name,mobile,str,email;
    //private int[] IMAGEgrid = {R.mipmap.zebra336in_is, R.mipmap.zebra336in_is, R.mipmap.zebra340in_is, R.mipmap.zebra340in_is};
    private String[] IMAGEgrid = {"http://atm-india.in/img/CoverImages/zebra336in_is.png",
            "http://atm-india.in/img/CoverImages/zebra336in_is.png"
            ,"http://atm-india.in/img/CoverImages/zebra340in_is.png"
            ,"http://atm-india.in/img/CoverImages/zebra340in_is.png"
            ,"http://atm-india.in/img/CoverImages/440in.png"
            ,"http://atm-india.in/img/CoverImages/101black.png"
            ,"http://atm-india.in/img/CoverImages/301black.png"
            ,"http://atm-india.in/img/CoverImages/pvcCards.png"
            ,"http://atm-india.in/img/CoverImages/cleaningkit.png"};

    private String[] TITLeGgrid = {"Zebra 336 IS", "Zebra 336 IN", "Zebra 340 IS",  "Zebra 340 IN",  "Zebra 440 IN",  "Zebra 101 Black",  "Zebra 301 Black", "Zebra PVC Cards", "Zebra Cleaning Kit"};
    private String[] Dategrid = {"Explore Now!","Grab Now!","Discover now!", "Great Savings!","Explore Now!","Grab Now!","Discover now!", "Great Savings!","Explore Now!"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub_product_kioskbarcode_printers);

        SessionManagement session =new SessionManagement(SubProductKIOSKBarcodePrinters.this);
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
        GridviewAdapter gridviewAdapter = new GridviewAdapter(SubProductKIOSKBarcodePrinters.this, beanclassArrayList);
        gridview.setExpanded(true);

        gridview.setAdapter(gridviewAdapter);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                        Intent i = new Intent(SubProductKIOSKBarcodePrinters.this,ProductDetails.class);
                        i.putExtra("position",position);
                        i.putExtra("Class","kiosk");
                        i.putExtra("CompanyName","Zebra Accessories");
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
        startActivity(new Intent(SubProductKIOSKBarcodePrinters.this,HomeActivity.class));
        finish();
    }

    @Override
    public void onBackPressed() {

    }
}
