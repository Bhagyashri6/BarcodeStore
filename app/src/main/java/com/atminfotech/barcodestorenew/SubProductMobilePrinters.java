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

public class SubProductMobilePrinters extends AppCompatActivity {

    //private int[] IMAGEgrid = {R.mipmap.ez320, R.mipmap.qln220};
    private String[] IMAGEgrid = {"http://atm-india.in/img/CoverImages/ez320.png","http://atm-india.in/img/CoverImages/qln220.png"};

    private String[] TITLeGgrid = {"EZ 320","QLN 220"};
    private String[] Dategrid = {"Explore Now!","Grab Now!"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub_product_mobile_printers);

        ExpandableHeightGridView gridview = (ExpandableHeightGridView) findViewById(R.id.gridview);
        ArrayList<Beanclass> beanclassArrayList = new ArrayList<>();

        for (int i= 0; i< IMAGEgrid.length; i++) {

            Beanclass beanclass = new Beanclass(IMAGEgrid[i], TITLeGgrid[i], Dategrid[i]);
            beanclassArrayList.add(beanclass);

        }
        GridviewAdapter gridviewAdapter = new GridviewAdapter(SubProductMobilePrinters.this, beanclassArrayList);
        gridview.setExpanded(true);

        gridview.setAdapter(gridviewAdapter);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                    Intent i = new Intent(SubProductMobilePrinters.this,ProductDetails.class);
                    i.putExtra("position",position);
                    i.putExtra("Class","mobileprinters");
                    i.putExtra("CompanyName","Zebra Mobile Printers");
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
        startActivity(new Intent(SubProductMobilePrinters.this,HomeActivity.class));
        finish();
    }

    @Override
    public void onBackPressed() {

    }
}
