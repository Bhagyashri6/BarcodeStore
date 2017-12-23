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

public class SubProductIndustrialBarcode extends AppCompatActivity {
    String imei;
    String name,mobile,str,email;
    //private int[] IMAGEgrid = {R.mipmap.zt230, R.mipmap.zt410, R.mipmap.zt110xi, R.mipmap.gx430, R.mipmap.zd500};
    private String[] IMAGEgrid = {"http://atm-india.in/img/CoverImages/zt230.png"
            ,"http://atm-india.in/img/CoverImages/zt410.png"
            ,"http://atm-india.in/img/CoverImages/zt110xi.png"
            ,"http://atm-india.in/img/CoverImages/gx430.png"
            ,"http://atm-india.in/img/CoverImages/zd500.png"};

    private String[] TITLeGgrid = {"ZT 230", "ZT 410", "ZT 110xi",  "GX 430", "ZD 500R"};
    private String[] Dategrid = {"Explore Now!","Grab Now!","Discover now!", "Great Savings!","Discover now!"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub_product_industrial_barcode);

        SessionManagement session =new SessionManagement(SubProductIndustrialBarcode.this);
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
        GridviewAdapter gridviewAdapter = new GridviewAdapter(SubProductIndustrialBarcode.this, beanclassArrayList);
        gridview.setExpanded(true);

        gridview.setAdapter(gridviewAdapter);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                    Intent i = new Intent(SubProductIndustrialBarcode.this,ProductDetails.class);
                    i.putExtra("position",position);
                    i.putExtra("Class","industrialbarcode");
                    i.putExtra("CompanyName","Zebra Industrial Barcode");
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
        startActivity(new Intent(SubProductIndustrialBarcode.this,HomeActivity.class));
        finish();
    }

    @Override
    public void onBackPressed() {

    }
}
