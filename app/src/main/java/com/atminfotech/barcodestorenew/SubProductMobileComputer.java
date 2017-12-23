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

public class SubProductMobileComputer extends AppCompatActivity {

    //private int[] IMAGEgrid = {R.mipmap.mc3200, R.mipmap.mc36, R.mipmap.mc9200, R.mipmap.mc2180, R.mipmap.mc45, R.mipmap.mc67};
    private String[] IMAGEgrid = {"http://atm-india.in/img/CoverImages/mc3200.png"
            ,"http://atm-india.in/img/CoverImages/mc36.png"
            ,"http://atm-india.in/img/CoverImages/mc36.png"
            ,"http://atm-india.in/img/CoverImages/mc9200.png"
            ,"http://atm-india.in/img/CoverImages/mc2180.png"
            ,"http://atm-india.in/img/CoverImages/mc2180.png"
            ,"http://atm-india.in/img/CoverImages/mc45.png"
            ,"http://atm-india.in/img/CoverImages/mc67.png"
            ,"http://atm-india.in/img/CoverImages/mc319z.png"
            ,"http://atm-india.in/img/CoverImages/mc919z.png"};

    private String[] TITLeGgrid = {"MC 3200", "MC 36 1D", "MC 36 2D", "MC 9200",  "MC 2180 1D", "MC 2180 2D", "MC 45", "MC 67", "MC 319 Z", "MC 919 Z"};
    private String[] Dategrid = {"Explore Now!","Grab Now!","Discover now!", "Great Savings!","Discover now!", "Great Savings!","Explore Now!","Discover now!","Discover now!", "Great Savings!"};
    String imei;
    String name,mobile,str,email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub_product_mobile_computer);

        SessionManagement session =new SessionManagement(SubProductMobileComputer.this);
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
        GridviewAdapter gridviewAdapter = new GridviewAdapter(SubProductMobileComputer.this, beanclassArrayList);
        gridview.setExpanded(true);

        gridview.setAdapter(gridviewAdapter);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                    Intent i = new Intent(SubProductMobileComputer.this,ProductDetails.class);
                    i.putExtra("position",position);
                    i.putExtra("Class","mobilecomputer");
                    i.putExtra("CompanyName","Zebra Mobile Computers");
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
        startActivity(new Intent(SubProductMobileComputer.this,HomeActivity.class));
        finish();
    }

    @Override
    public void onBackPressed() {

    }
}
