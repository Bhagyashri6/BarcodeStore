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

public class SubProductRFIDBarcodeReaders extends AppCompatActivity {
    String imei;
    String name,mobile,str,email;
    //private int[] IMAGEgrid = {R.mipmap.zebra_card_studio, R.mipmap.zebra_designer_pro};
    private String[] IMAGEgrid = {"http://atm-india.in/img/CoverImages/cardstudio.png","http://atm-india.in/img/CoverImages/designerpro.png"};

    private String[] TITLeGgrid = {"Zebra Card Studio", "Zebra Designer Pro"};
    private String[] Dategrid = {"Explore Now!","Grab Now!"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub_product_rfidbarcode_readers);

        SessionManagement session =new SessionManagement(SubProductRFIDBarcodeReaders.this);
        HashMap<String, String> user = session.getUserDetails();

        name = user.get(SessionManagement.KEY_NAME);
        mobile = user.get(SessionManagement.KEY_Mobile);
        email = user.get(SessionManagement.KEY_EmailId);
        imei=user.get(SessionManagement.KEY_Imei);



        ExpandableHeightGridView gridview = (ExpandableHeightGridView) findViewById(R.id.gridview);
        ArrayList<Beanclass> beanclassArrayList = new ArrayList<>();

        for (int i= 0; i< IMAGEgrid.length; i++) {

            Beanclass beanclass = new Beanclass(IMAGEgrid[i], TITLeGgrid[i],Dategrid[i]);
            beanclassArrayList.add(beanclass);

        }
        GridviewAdapter gridviewAdapter = new GridviewAdapter(SubProductRFIDBarcodeReaders.this, beanclassArrayList);
        gridview.setExpanded(true);

        gridview.setAdapter(gridviewAdapter);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                    Intent i = new Intent(SubProductRFIDBarcodeReaders.this,ProductDetails.class);
                    i.putExtra("position",position);
                    i.putExtra("Class","rfid");
                    i.putExtra("CompanyName","Zebra RFID Barcode Readers");
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
        startActivity(new Intent(SubProductRFIDBarcodeReaders.this,HomeActivity.class));
        finish();
    }

    @Override
    public void onBackPressed() {

    }
}
