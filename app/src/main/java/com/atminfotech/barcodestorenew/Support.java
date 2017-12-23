package com.atminfotech.barcodestorenew;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class Support extends AppCompatActivity {

    //private int[] IMAGE = {R.mipmap.purchase_history_demo, R.mipmap.warrenty_demo, R.mipmap.non_warrenty_demo/*, R.mipmap.troubleshoot_demo*/};
    CardView Purchase,Warranty,Non_Warranty;
    ImageView purchase,warrenty,non_warrenty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.service_support);

        Purchase = (CardView) findViewById(R.id.purchase);
        Warranty = (CardView) findViewById(R.id.warranty);
        Non_Warranty = (CardView) findViewById(R.id.non_warranty);
        purchase = (ImageView) findViewById(R.id.purchase_history);
        warrenty = (ImageView) findViewById(R.id.iv_warrenty);
        non_warrenty = (ImageView) findViewById(R.id.iv_non_warrenty);

        Picasso.with(Support.this)
                .load("http://atm-india.in/img/CoverImages/puchase_history_demo.png")
                .into(purchase);

        Picasso.with(Support.this)
                .load("http://atm-india.in/img/CoverImages/warranty_demo.png")
                .into(warrenty);

        Picasso.with(Support.this)
                .load("http://atm-india.in/img/CoverImages/termsandconditions_demo.jpg")
                .into(non_warrenty);

        Purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Support.this,Rma.class));
            }
        });

        Warranty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Support.this,WarrantyIntermediate.class));
            }
        });

        Non_Warranty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Support.this,NonWarrantyIntermediate.class));
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
        startActivity(new Intent(Support.this,HomeActivity.class));
        finish();
    }

    @Override
    public void onBackPressed() {

    }
}

