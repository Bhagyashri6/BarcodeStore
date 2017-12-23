package com.atminfotech.barcodestorenew;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

public class SearchProducts extends AppCompatActivity {
    String imei;
    String name,mobile,str,email;
    private String[] IMAGEgrid = {"http://atm-india.in/img/CoverImages/101black.png"
            , "http://atm-india.in/img/CoverImages/301black.png", "http://atm-india.in/img/CoverImages/zebra336in_is.png"
            , "http://atm-india.in/img/CoverImages/zebra336in_is.png", "http://atm-india.in/img/CoverImages/zebra340in_is.png"
            , "http://atm-india.in/img/CoverImages/zebra340in_is.png", "http://atm-india.in/img/CoverImages/440in.png"
            , "http://atm-india.in/img/CoverImages/cardstudio.png", "http://atm-india.in/img/CoverImages/cleaningkit.png"
            , "http://atm-india.in/img/CoverImages/designerpro.png", "http://atm-india.in/img/CoverImages/ds3600.png"
            , "http://atm-india.in/img/CoverImages/ds4208.png", "http://atm-india.in/img/CoverImages/ds4308.png"
            , "http://atm-india.in/img/CoverImages/ds6878.png", "http://atm-india.in/img/CoverImages/ds7708.png"
            , "http://atm-india.in/img/CoverImages/ds9208.png", "http://atm-india.in/img/CoverImages/ez320.png"
            , "http://atm-india.in/img/CoverImages/gc420t.png", "http://atm-india.in/img/CoverImages/gt800.png"
            , "http://atm-india.in/img/CoverImages/gx430.png", "http://atm-india.in/img/CoverImages/li4278.png"
            , "http://atm-india.in/img/CoverImages/ls1203.png", "http://atm-india.in/img/CoverImages/ls2208withoutstand.png"
            , "http://atm-india.in/img/CoverImages/ls2208withstand.png", "http://atm-india.in/img/CoverImages/lp130.png"
            , "http://atm-india.in/img/CoverImages/lx400.png", "http://atm-india.in/img/CoverImages/lx900.png"
            , "http://atm-india.in/img/CoverImages/mc2180.png", "http://atm-india.in/img/CoverImages/mc2180.png"
            , "http://atm-india.in/img/CoverImages/mc319z.png", "http://atm-india.in/img/CoverImages/mc3200.png"
            , "http://atm-india.in/img/CoverImages/mc36.png", "http://atm-india.in/img/CoverImages/mc36.png"
            , "http://atm-india.in/img/CoverImages/mc45.png", "http://atm-india.in/img/CoverImages/mc67.png"
            , "http://atm-india.in/img/CoverImages/mc919z.png", "http://atm-india.in/img/CoverImages/mc9200.png"
            , "http://atm-india.in/img/CoverImages/pos_dt_series.png", "http://atm-india.in/img/CoverImages/pos_ft_series.png"
            , "http://atm-india.in/img/CoverImages/pos_ht_series.png", "http://atm-india.in/img/CoverImages/pos_jiva_tp_series.png"
            , "http://atm-india.in/img/CoverImages/pos_monitor.png", "http://atm-india.in/img/CoverImages/px450.png"
            , "http://atm-india.in/img/CoverImages/qln220.png", "http://atm-india.in/img/CoverImages/rx900.png"
            , "http://atm-india.in/img/CoverImages/rx900f.png", "http://atm-india.in/img/CoverImages/pvcCards.png"
            , "http://atm-india.in/img/CoverImages/zxp3.png", "http://atm-india.in/img/CoverImages/zxp7.png"
            , "http://atm-india.in/img/CoverImages/zxp8.png", "http://atm-india.in/img/CoverImages/zd500.png"
            , "http://atm-india.in/img/CoverImages/zt110xi.png", "http://atm-india.in/img/CoverImages/zt230.png"
            , "http://atm-india.in/img/CoverImages/zt410.png"
    };

    private String[] TITLeGgrid = {"Zebra 101 Black", "Zebra 301 Black", "Zebra 336 IN", "Zebra 336 IS", "Zebra 340 IN", "Zebra 340 IS", "Zebra 440 IN", "Zebra Card Studio"
            , "Zebra Cleaning Kit", "Zebra Designer Pro", "Zebra Scanner DS 3600", "Zebra Scanner DS 4208", "Zebra Scanner DS 4308", "Zebra Scanner DS 6878"
            , "Zebra Scanner DS 7708", "Zebra Scanner DS 9208", "EZ 320", "GC 420t", "GT 800", "GX 430", "Zebra Scanner LI 4278", "Zebra Scanner LS 1203", "Zebra Scanner LS 2208 Without Stand"
            , "Zebra Scanner LS 2208 With Stand", "LP 130", "LX 400", "LX 900", "MC 2180 1D", "MC 2180 2D", "MC 319 Z", "MC 3200", "MC 36 1D", "MC 36 2D", "MC 45"
            , "MC 67", "MC 919 Z", "MC 9200", "POS DT Series", "POS FT Series", "POS HT Series", "POS Jiva TP Series", "POS Monitor", "PX 450", "QLN 220", "RX 900", "RX 900F", "Zebra PVC Cards", "Zebra ZXP 3", "Zebra ZXP 7", "Zebra ZXP 8", "ZD 500R", "ZT 110xi", "ZT 230", "ZT 410"};

    private String[] Dategrid = {"Explore Now!", "Grab Now!", "Discover now!", "Great Savings!", "Discover now!", "Great Savings!", "Grab Now!", "Discover now!", "Explore Now!", "Grab Now!"
            , "Explore Now!", "Grab Now!", "Discover now!", "Great Savings!", "Discover now!", "Great Savings!", "Grab Now!", "Discover now!", "Explore Now!", "Grab Now!"
            , "Explore Now!", "Grab Now!", "Discover now!", "Great Savings!", "Discover now!", "Great Savings!", "Grab Now!", "Discover now!", "Explore Now!", "Grab Now!"
            , "Explore Now!", "Grab Now!", "Discover now!", "Great Savings!", "Discover now!", "Great Savings!", "Grab Now!", "Discover now!", "Explore Now!", "Grab Now!"
            , "Explore Now!", "Grab Now!", "Discover now!", "Great Savings!", "Discover now!", "Great Savings!", "Grab Now!", "Discover now!", "Explore Now!", "Grab Now!"
            , "Great Savings!", "Discover now!", "Great Savings!", "Grab Now!"};

    private String[] ClassName = {"kiosk", "kiosk", "kiosk", "kiosk"
            , "kiosk", "kiosk", "kiosk", "rfid", "kiosk", "rfid", "basicbarcodescanner"
            , "basicbarcodescanner", "basicbarcodescanner", "basicbarcodescanner", "basicbarcodescanner", "basicbarcodescanner", "mobileprinters", "desktop", "desktop"
            , "Zebra Industrial Barcode", "basicbarcodescanner", "basicbarcodescanner", "basicbarcodescanner", "basicbarcodescanner", "primera", "primera"
            , "primera", "mobilecomputer"
            , "mobilecomputer", "mobilecomputer", "mobilecomputer", "mobilecomputer", "mobilecomputer", "mobilecomputer"
            , "mobilecomputer", "mobilecomputer", "mobilecomputer", "pos_jiva_tp_series", "pos_jiva_tp_series", "pos_jiva_tp_series"
            , "pos_jiva_tp_series", "pos_jiva_tp_series", "primera", "mobileprinters", "primera", "primera", "kiosk", "cardprinters"
            , "cardprinters", "cardprinters", "industrialbarcode"
            , "industrialbarcode", "industrialbarcode", "industrialbarcode"};

    private String[] CompanyName = {"Zebra Accessories", "Zebra Accessories", "Zebra Accessories", "Zebra Accessories"
            , "Zebra Accessories", "Zebra Accessories", "Zebra Accessories", "Zebra Software", "Zebra Accessories", "Zebra Software", "Zebra Barcode Scanner"
            , "Zebra Barcode Scanner", "Zebra Barcode Scanner", "Zebra Barcode Scanner", "Zebra Barcode Scanner", "Zebra Barcode Scanner", "Zebra Mobile Printers"
            , "Zebra Desktop Barcode Printers", "Zebra Desktop Barcode Printers"
            , "Zebra Industrial Barcode", "Zebra Barcode Scanner", "Zebra Barcode Scanner", "Zebra Barcode Scanner", "Zebra Barcode Scanner", "Primera", "Primera"
            , "Primera", "Zebra Mobile Computers"
            , "Zebra Mobile Computers", "Zebra Mobile Computers", "Zebra Mobile Computers", "Zebra Mobile Computers", "Zebra Mobile Computers", "Zebra Mobile Computers"
            , "Zebra Mobile Computers", "Zebra Mobile Computers", "Zebra Mobile Computers", "Posiflex", "Posiflex", "Posiflex"
            , "Posiflex", "Posiflex", "Primera", "Zebra Mobile Printers", "Primera", "Primera", "Zebra Accessories", "Zebra Card Printers"
            , "Zebra Card Printers", "Zebra Card Printers", "Zebra Industrial Barcode"
            , "Zebra Industrial Barcode", "Zebra Industrial Barcode", "Zebra Industrial Barcode"};

    private int[] positions = {5, 6, 1, 0, 3, 2, 4, 0, 8, 1, 6, 4, 5, 7, 8, 9, 0, 1, 0, 3, 3, 0, 1, 2, 3, 4, 5, 4, 5, 8, 0, 1, 2, 6, 7, 9, 3, 2, 3, 4, 0, 1, 0, 1, 1, 2, 7, 0, 1, 2, 4, 2, 0, 1};

    ImageView ImageView0, ImageView1, ImageView2, ImageView3, ImageView4, ImageView5, ImageView6, ImageView7, ImageView8, ImageView9;
    ArrayList<Beanclass> beanclassArrayList = new ArrayList<>();
    LinearLayout SelectAll, Categories, ContainerCategory;
    ExpandableHeightGridView gridview;
    GridviewAdapter gridviewAdapter;
    boolean sa = false, ac = false;
    TextView txt_products, txt_category;
    EditText et_search;
    String[] imgLinks = {"http://atm-india.in/img/CoverImages/gt800.png"
            , "http://atm-india.in/img/CoverImages/ls1203.png"
            , "http://atm-india.in/img/CoverImages/zt230.png"
            , "http://atm-india.in/img/CoverImages/mc36.png"
            , "http://atm-india.in/img/CoverImages/ez320.png"
            , "http://atm-india.in/img/CoverImages/zxp3.png"
            , "http://atm-india.in/img/CoverImages/cardstudio.png"
            , "http://atm-india.in/img/CoverImages/ribbons.png"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_products);


         SessionManagement session =new SessionManagement(SearchProducts.this);
        HashMap<String, String> user = session.getUserDetails();

        name = user.get(SessionManagement.KEY_NAME);
        mobile = user.get(SessionManagement.KEY_Mobile);
        email = user.get(SessionManagement.KEY_EmailId);
        imei=user.get(SessionManagement.KEY_Imei);



        et_search = (EditText) findViewById(R.id.search_inside);
        gridview = (ExpandableHeightGridView) findViewById(R.id.gridview);
        SelectAll = (LinearLayout) findViewById(R.id.all_products);
        Categories = (LinearLayout) findViewById(R.id.categories);
        ContainerCategory = (LinearLayout) findViewById(R.id.ll_category);
        txt_category = (TextView) findViewById(R.id.cat_txt);
        txt_products = (TextView) findViewById(R.id.prod_text);
        ImageView0 = (ImageView) findViewById(R.id.imageview);
        ImageView1 = (ImageView) findViewById(R.id.imageview1);
        ImageView2 = (ImageView) findViewById(R.id.imageview2);
        ImageView3 = (ImageView) findViewById(R.id.imageview3);
        ImageView4 = (ImageView) findViewById(R.id.imageview4);
        ImageView5 = (ImageView) findViewById(R.id.imageview5);
        ImageView6 = (ImageView) findViewById(R.id.imageview6);
        ImageView7 = (ImageView) findViewById(R.id.imageview7);
        ImageView8 = (ImageView) findViewById(R.id.cat_primera);
        ImageView9 = (ImageView) findViewById(R.id.cat_posiflex);

        initTypes();

        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                et_search.requestFocus();
            }

            @Override
            public void onTextChanged(final CharSequence s, int start, int before, int count) {

                beanclassArrayList.clear();

                if (!et_search.getText().toString().isEmpty()) {

                    if (ContainerCategory.isShown()) {
                        ContainerCategory.setVisibility(View.GONE);
                    }
                    if (!gridview.isShown()) {
                        gridview.setVisibility(View.VISIBLE);
                    }

                    for (int i = 0; i < IMAGEgrid.length; i++) {
                        if (TITLeGgrid[i].toLowerCase().contains(s.toString().toLowerCase())) {
                            beanclassArrayList.add(new Beanclass(IMAGEgrid[i], TITLeGgrid[i], Dategrid[i]));
                            gridviewAdapter = new GridviewAdapter(SearchProducts.this, beanclassArrayList);
                            gridview.setExpanded(true);
                            gridview.setAdapter(gridviewAdapter);
                        }
                    }

                    if (et_search.getText().toString().isEmpty()) {
                        beanclassArrayList.clear();
                        gridviewAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                if (et_search.getText().toString().isEmpty()) {
                    Intent i = new Intent(SearchProducts.this, ProductDetails.class);
                    i.putExtra("position", positions[position]);
                    i.putExtra("Class", ClassName[position]);
                    i.putExtra("CompanyName", CompanyName[position]);
                    i.putExtra("ModelNo", TITLeGgrid[position]);
                    startActivity(i);
                } else {
                    Beanclass bean = (Beanclass) gridviewAdapter.getItem(position);
                    int trickedPosition = 0;
                    for (int i = 0; i < TITLeGgrid.length; i++) {
                        if (TITLeGgrid[i].equals(bean.getTitle1())) {
                            trickedPosition = i;
                            break;
                        }
                    }
                    Intent i = new Intent(SearchProducts.this, ProductDetails.class);
                    i.putExtra("position", positions[trickedPosition]);
                    i.putExtra("Class", ClassName[trickedPosition]);
                    i.putExtra("CompanyName", CompanyName[trickedPosition]);
                    i.putExtra("ModelNo", TITLeGgrid[trickedPosition]);
                    startActivity(i);
                }
            }
        });

        FloatingActionButton Fab = (FloatingActionButton) findViewById(R.id.fab);
        Fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SearchProducts.this,CartActivity.class));
            }
        });
    }

    private void initTypes() {

        SelectAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                gridview.setVisibility(View.VISIBLE);
                ContainerCategory.setVisibility(View.GONE);
                if (!sa) {
                    txt_products.setTextColor(getResources().getColor(R.color.colorPrimary));
                    sa = true;

                    txt_category.setTextColor(getResources().getColor(R.color.about_description_color));
                    ac = false;

                    new GetProductsAll().execute();

                } else {
                    gridview.setVisibility(View.GONE);
                    txt_products.setTextColor(getResources().getColor(R.color.about_description_color));
                    sa = false;
                    if (!beanclassArrayList.isEmpty()) {
                        beanclassArrayList.clear();
                        gridviewAdapter.notifyDataSetChanged();
                    }
                }

            }
        });

        Categories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                gridview.setVisibility(View.GONE);

                if (!beanclassArrayList.isEmpty()) {
                    beanclassArrayList.clear();
                    gridviewAdapter.notifyDataSetChanged();
                }

                if (!ac) {
                    ContainerCategory.setVisibility(View.VISIBLE);
                    if (ContainerCategory.isShown()) {
                        Picasso.with(SearchProducts.this)
                                .load(imgLinks[0])
                                .resize(250, 250)
                                .into(ImageView0);

                        Picasso.with(SearchProducts.this)
                                .load(imgLinks[1])
                                .resize(250, 250)
                                .into(ImageView1);

                        Picasso.with(SearchProducts.this)
                                .load(imgLinks[2])
                                .resize(250, 250)
                                .into(ImageView2);

                        Picasso.with(SearchProducts.this)
                                .load(imgLinks[3])
                                .resize(250, 250)
                                .into(ImageView3);

                        Picasso.with(SearchProducts.this)
                                .load(imgLinks[4])
                                .resize(250, 250)
                                .into(ImageView4);

                        Picasso.with(SearchProducts.this)
                                .load(imgLinks[5])
                                .resize(250, 250)
                                .into(ImageView5);

                        Picasso.with(SearchProducts.this)
                                .load(imgLinks[6])
                                .resize(250, 250)
                                .into(ImageView6);

                        Picasso.with(SearchProducts.this)
                                .load(imgLinks[7])
                                .resize(250, 250)
                                .into(ImageView7);

                        Picasso.with(SearchProducts.this)
                                .load("http://atm-india.in/img/CoverImages/primera_demo.jpg")
                                .into(ImageView8);

                        Picasso.with(SearchProducts.this)
                                .load("http://atm-india.in/img/CoverImages/posiflex_demo.png")
                                .into(ImageView9);
                    }

                    txt_category.setTextColor(getResources().getColor(R.color.colorPrimary));
                    ac = true;

                    txt_products.setTextColor(getResources().getColor(R.color.about_description_color));
                    sa = false;
                } else {
                    ContainerCategory.setVisibility(View.GONE);
                    txt_category.setTextColor(getResources().getColor(R.color.about_description_color));
                    ac = false;
                }
            }
        });
    }

    public class GetProductsAll extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            beanclassArrayList.clear();
            for (int i = 0; i < IMAGEgrid.length; i++) {
                beanclassArrayList.add(new Beanclass(IMAGEgrid[i], TITLeGgrid[i], Dategrid[i]));
                gridviewAdapter = new GridviewAdapter(SearchProducts.this, beanclassArrayList);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            gridview.setExpanded(true);
            gridview.setAdapter(gridviewAdapter);
        }
    }

    public void BarcodePrinters(View view) {
        startActivity(new Intent(SearchProducts.this, SubProductDekstopBarcodePrinter.class));
    }

    public void BarcodeScanners(View view) {
        startActivity(new Intent(SearchProducts.this, SubProductBasicBarcodeScanner.class));
    }

    public void IndustrialBarcodePrinters(View view) {
        startActivity(new Intent(SearchProducts.this, SubProductIndustrialBarcode.class));
    }

    public void MobilePrinters(View view) {
        startActivity(new Intent(SearchProducts.this, SubProductMobilePrinters.class));
    }

    public void MobileComputers(View view) {
        startActivity(new Intent(SearchProducts.this, SubProductMobileComputer.class));
    }

    public void CardPrinters(View view) {
        startActivity(new Intent(SearchProducts.this, SubProductCardPrinters.class));
    }

    public void ZebraSoftwares(View view) {
        startActivity(new Intent(SearchProducts.this, SubProductRFIDBarcodeReaders.class));
    }

    public void Accessories(View view) {
        startActivity(new Intent(SearchProducts.this, SubProductKIOSKBarcodePrinters.class));
    }

    public void Primera(View view) {
        startActivity(new Intent(SearchProducts.this, ProductPrimera.class));
    }

    public void Posiflex(View view) {
        startActivity(new Intent(SearchProducts.this, ProductsPosiflex.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_home,menu);
        return true;
    }

    public void onHomeClicked(MenuItem menu){
        startActivity(new Intent(SearchProducts.this,HomeActivity.class));
        finish();
    }
    @Override
    public void onBackPressed() {

    }
}
