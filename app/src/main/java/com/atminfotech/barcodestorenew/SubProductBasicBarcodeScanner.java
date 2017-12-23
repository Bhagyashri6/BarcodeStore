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

public class SubProductBasicBarcodeScanner extends AppCompatActivity {
    String imei;
    String name,mobile,str,email;
    //private int[] IMAGEgrid = {R.mipmap.ls1203, R.mipmap.ls2208, R.mipmap.ls2208_with_stand, R.mipmap.li4278, R.mipmap.ds4208, R.mipmap.ds4308, R.mipmap.ds3600, R.mipmap.ds6878,R.mipmap.ds7708, R.mipmap.ds9208};
    private String[] IMAGEgrid = {"http://atm-india.in/img/CoverImages/ls1203.png"
            ,"http://atm-india.in/img/CoverImages/ls2208withoutstand.png"
            ,"http://atm-india.in/img/CoverImages/ls2208withstand.png"
            ,"http://atm-india.in/img/CoverImages/li4278.png"
            ,"http://atm-india.in/img/CoverImages/ds4208.png"
            ,"http://atm-india.in/img/CoverImages/ds4308.png"
            ,"http://atm-india.in/img/CoverImages/ds3600.png"
            ,"http://atm-india.in/img/CoverImages/ds6878.png"
            ,"http://atm-india.in/img/CoverImages/ds7708.png"
            ,"http://atm-india.in/img/CoverImages/ds9208.png"};

    private String[] TITLeGgrid = {"Zebra Scanner LS 1203","Zebra Scanner LS 2208 Without Stand","Zebra Scanner LS 2208 With Stand","Zebra Scanner LI 4278",  "Zebra Scanner DS 4208", "Zebra Scanner DS 4308", "Zebra Scanner DS 3600", "Zebra Scanner DS 6878", "Zebra Scanner DS 7708","Zebra Scanner DS 9208"};

    private String[] Dategrid = {"Explore Now!","Grab Now!","Discover now!", "Great Savings!","Discover now!", "Great Savings!","Grab Now!","Discover now!","Explore Now!","Grab Now!"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub_product_basic_barcode_scanner);

        SessionManagement session =new SessionManagement(SubProductBasicBarcodeScanner.this);
        HashMap<String, String> user = session.getUserDetails();

        name = user.get(SessionManagement.KEY_NAME);
        mobile = user.get(SessionManagement.KEY_Mobile);
        email = user.get(SessionManagement.KEY_EmailId);
        imei=user.get(SessionManagement.KEY_Imei);


        ExpandableHeightGridView gridview = (ExpandableHeightGridView) findViewById(R.id.gridview);
        ArrayList<Beanclass> beanclassArrayList = new ArrayList<>();

        for (int i= 0; i< IMAGEgrid.length; i++) {

            beanclassArrayList.add( new Beanclass(IMAGEgrid[i], TITLeGgrid[i],  Dategrid[i]));

        }
        GridviewAdapter gridviewAdapter = new GridviewAdapter(SubProductBasicBarcodeScanner.this, beanclassArrayList);
        gridview.setExpanded(true);

        gridview.setAdapter(gridviewAdapter);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                    Intent i = new Intent(SubProductBasicBarcodeScanner.this,ProductDetails.class);
                    i.putExtra("position",position);
                    i.putExtra("Class","basicbarcodescanner");
                    i.putExtra("CompanyName","Zebra Barcode Scanner");
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
        startActivity(new Intent(SubProductBasicBarcodeScanner.this,HomeActivity.class));
        finish();
    }

    @Override
    public void onBackPressed() {

    }
}
