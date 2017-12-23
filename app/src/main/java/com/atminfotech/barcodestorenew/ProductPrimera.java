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

public class ProductPrimera extends AppCompatActivity {

    String imei;
    String name,mobile,str,email;

    //private int[] IMAGEgrid = {R.mipmap.pos_jiva_tp_series1, R.mipmap.pos_jiva_tp_series3, R.mipmap.pos_dt_series, R.mipmap.pos_ft_series, R.mipmap.pos_ht_series};
    private String[] IMAGEgrid = {"http://atm-india.in/img/CoverImages/px450.png"
            ,"http://atm-india.in/img/CoverImages/rx900.png"
            ,"http://atm-india.in/img/CoverImages/rx900f.png"
            ,"http://atm-india.in/img/CoverImages/lp130.png"
            ,"http://atm-india.in/img/CoverImages/lx400.png"
            ,"http://atm-india.in/img/CoverImages/lx900.png"};

    private String[] TITLeGgrid = {"PX 450", "RX 900", "RX 900F",  "LP 130", "LX 400", "LX 900"};
    private String[] Dategrid = {"Discover now!","Explore Now!","Grab Now!","Discover now!", "Great Savings!","Discover now!"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_primera);

        SessionManagement session =new SessionManagement(ProductPrimera.this);
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
        GridviewAdapter gridviewAdapter = new GridviewAdapter(ProductPrimera.this, beanclassArrayList);
        gridview.setExpanded(true);

        gridview.setAdapter(gridviewAdapter);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                    Intent i = new Intent(ProductPrimera.this,ProductDetails.class);
                    i.putExtra("position",position);
                    i.putExtra("Class","primera");
                    i.putExtra("CompanyName","Primera");
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
        startActivity(new Intent(ProductPrimera.this,HomeActivity.class));
        finish();
    }

    @Override
    public void onBackPressed() {

    }
}
