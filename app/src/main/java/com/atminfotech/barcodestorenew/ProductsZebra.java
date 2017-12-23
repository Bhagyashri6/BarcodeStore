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

public class ProductsZebra extends AppCompatActivity {
    String imei;
    String name,mobile,str,email;
    //private int[] IMAGEgrid = {R.mipmap.gc420t, R.mipmap.ls1203, R.mipmap.zt230, R.mipmap.mc36, R.mipmap.ez320, R.mipmap.zxp_three, R.mipmap.zebra_card_studio, R.mipmap.zebra_ribbon};

    private String[] IMAGEgrid = {"http://atm-india.in/img/CoverImages/gt800.png"
            ,"http://atm-india.in/img/CoverImages/ls1203.png"
            ,"http://atm-india.in/img/CoverImages/zt230.png"
            ,"http://atm-india.in/img/CoverImages/mc36.png"
            ,"http://atm-india.in/img/CoverImages/ez320.png"
            ,"http://atm-india.in/img/CoverImages/zxp3.png"
            ,"http://atm-india.in/img/CoverImages/cardstudio.png"
            ,"http://atm-india.in/img/CoverImages/ribbons.png"};

    private String[] TITLeGgrid = {"Entry Level Barcode Printers", "Barcode Scanners", "Industrial Barcode Printers",  "Mobile Computers", "Mobile Printers", "Card Printers", "Softwares", "Accessories"};
    private String[] Dategrid = {"Explore Now!","Grab Now!","Discover now!", "Great Savings!","Discover now!", "Great Savings!","Grab Now!","Discover now!"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product);

        SessionManagement session =new SessionManagement(ProductsZebra.this);
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
        GridviewAdapter gridviewAdapter = new GridviewAdapter(ProductsZebra.this, beanclassArrayList);
        gridview.setExpanded(true);

        gridview.setAdapter(gridviewAdapter);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                switch (position)
                {
                    case 0:
                        startActivity(new Intent(ProductsZebra.this,SubProductDekstopBarcodePrinter.class));
                        break;
                    case 1:
                        startActivity(new Intent(ProductsZebra.this,SubProductBasicBarcodeScanner.class));
                        break;
                    case 2:
                        startActivity(new Intent(ProductsZebra.this,SubProductIndustrialBarcode.class));
                        break;
                    case 3:
                        startActivity(new Intent(ProductsZebra.this,SubProductMobileComputer.class));
                        break;
                    case 4:
                        startActivity(new Intent(ProductsZebra.this,SubProductMobilePrinters.class));
                        break;
                    case 5:
                        startActivity(new Intent(ProductsZebra.this,SubProductCardPrinters.class));
                        break;
                    case 6:
                        startActivity(new Intent(ProductsZebra.this,SubProductRFIDBarcodeReaders.class));
                        break;
                    case 7:
                        startActivity(new Intent(ProductsZebra.this,SubProductKIOSKBarcodePrinters.class));
                        break;
                }
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
        startActivity(new Intent(ProductsZebra.this,HomeActivity.class));
        finish();
    }

    @Override
    public void onBackPressed() {

    }
}
