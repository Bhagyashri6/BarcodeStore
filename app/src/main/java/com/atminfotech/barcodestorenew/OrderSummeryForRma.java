package com.atminfotech.barcodestorenew;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class OrderSummeryForRma extends AppCompatActivity {

    String date,billNo,item,serialNo;
    TextView Bill,Date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summery_rma);

        Intent i = getIntent();
        date = i.getStringExtra("date");
        billNo = i.getStringExtra("billNo");
        item = i.getStringExtra("model");
        serialNo = i.getStringExtra("serial");

        Bill = (TextView) findViewById(R.id.order_no_rma);
        Date = (TextView) findViewById(R.id.date_rma);

        Bill.setText(billNo);
        Date.setText(date);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_home,menu);
        return true;
    }

    public void onHomeClicked(MenuItem menu){
        startActivity(new Intent(OrderSummeryForRma.this,HomeActivity.class));
        finish();
    }

    @Override
    public void onBackPressed() {

    }
}
