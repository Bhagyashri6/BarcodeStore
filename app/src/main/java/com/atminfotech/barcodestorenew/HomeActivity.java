package com.atminfotech.barcodestorenew;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.squareup.picasso.Picasso;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends AppCompatActivity implements BaseSliderView.OnSliderClickListener {

    LinearLayout Zebra,Posiflex,Primera,Softwares;
    ImageView /*one,two,three,four,five,six,seven,eight,nine,ten,*/iv_offers;
    CircleImageView primera,posiflex,softwares,zebra;
    SliderLayout mDemoSlider;
    EditText Search_Main;
    CardView rma,offers;
    PagerIndicator pagerIndicator;
    ProgressDialog progressDialog;
    SessionManagement session;
    String imei,Amount;
    String name,mobile,str,email;
    ArrayList<String> namelist = new ArrayList<>();

    /*String Rate ;
    TextView  []rate = new TextView[10];
    ArrayList<String> mylist =new ArrayList<String>();

    // Integer[] input_rate ={R.id.txt_rate1,R.id.txt_rate2,R.id.txt_rate3,R.id.txt_rate4,R.id.txt_rate5,R.id.txt_rate6,R.id.txt_rate7,R.id.txt_rate8,R.id.txt_rate9,R.id.txt_rate10};
   String[] ModelNo ={"Zebra Scanner LS 1203","Zebra Scanner LS 2208","Zebra Scanner LI 4278","GT 800","GC 420t","Zebra 336 IS","Zebra 340 IS","ZT 230","GX 430","ZT 410"};
*/

    RecyclerView  cardRecycleView;
    HorizontalCardRecycleAdaapter cardRecycleAdaapter;
    RecyclerView.LayoutManager  layoutManager1;
   // private static String url = "http://atm-india.in/Service.asmx";
    private static final String URL = "http://atm-india.in/Service.asmx";
    private static final String NAMESPACE = "http://tempuri.org/";
    private static final String Register_SOAP_ACTION1 = "http://tempuri.org/GetDataInfo";
    private static final String SOAP_ACTIONFront = "http://tempuri.org/GetAllfrontData";
    private static final String METHOD_NAMEFront = "GetAllfrontData";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        Zebra = (LinearLayout) findViewById (R.id.zebra);
        Posiflex = (LinearLayout) findViewById (R.id.posiflex);
        Primera = (LinearLayout) findViewById (R.id.primera);
        Softwares = (LinearLayout) findViewById (R.id.software);
        rma = (CardView) findViewById(R.id.request_now);
        offers = (CardView) findViewById(R.id.card_view_offers);
      /*  one  = (ImageView) findViewById(R.id.horizontal_image_one);
        two  = (ImageView) findViewById(R.id.horizontal_image_two);
        three  = (ImageView) findViewById(R.id.horizontal_image_three);
        four  = (ImageView) findViewById(R.id.horizontal_image_four);
        five  = (ImageView) findViewById(R.id.horizontal_image_five);
        six  = (ImageView) findViewById(R.id.horizontal_image_six);
        seven  = (ImageView) findViewById(R.id.horizontal_image_seven);
        eight  = (ImageView) findViewById(R.id.horizontal_image_eight);
        nine =(ImageView) findViewById(R.id.horizontal_image_nine);
        ten =(ImageView) findViewById(R.id.horizontal_image_ten);*/
        iv_offers  = (ImageView) findViewById(R.id.offers_image);
        Search_Main = (EditText) findViewById (R.id.search_main);
        primera = (CircleImageView) findViewById(R.id.iv_primera);
        posiflex = (CircleImageView) findViewById(R.id.iv_posiflex);
        softwares = (CircleImageView) findViewById(R.id.iv_software);
        zebra = (CircleImageView) findViewById(R.id.iv_zebra);
        pagerIndicator = (PagerIndicator) findViewById(R.id.banner_slider_indicator);

      /*  rate[0] =(TextView)findViewById(R.id.txt_rate1);
        rate[1] =(TextView)findViewById(R.id.txt_rate2);
        rate[2] =(TextView)findViewById(R.id.txt_rate3);
        rate[3] =(TextView)findViewById(R.id.txt_rate4);
        rate[4] =(TextView)findViewById(R.id.txt_rate5);
        rate[5] =(TextView)findViewById(R.id.txt_rate6);
        rate[6] =(TextView)findViewById(R.id.txt_rate7);
        rate[7] =(TextView)findViewById(R.id.txt_rate8);
        rate[8] =(TextView)findViewById(R.id.txt_rate9);
        rate[9] =(TextView)findViewById(R.id.txt_rate10);

        for (int i=0;i<mylist.size();i++){
            rate[i].setText(mylist.get(i));
        }*/
       // rate2 =(TextView)findViewById(R.id.txt_rate2);

        cardRecycleView = (RecyclerView)findViewById(R.id.cardRecycleView);
        cardRecycleView.setHasFixedSize(true);
        layoutManager1 = new LinearLayoutManager(HomeActivity.this, LinearLayoutManager.HORIZONTAL, false);
        cardRecycleView.setLayoutManager(layoutManager1);
        new AsynkHorizotalCardData().execute();

        Picasso.with(HomeActivity.this)
                .load("http://atm-india.in/img/CoverImages/Zebralogo.png")
                .into(zebra);

        Picasso.with(HomeActivity.this)
                .load("http://atm-india.in/img/CoverImages/primeralogo.png")
                .into(primera);

        Picasso.with(HomeActivity.this)
                .load("http://atm-india.in/img/CoverImages/posiflexlogo.png")
                .into(posiflex);

        Picasso.with(HomeActivity.this)
                .load("http://atm-india.in/img/CoverImages/software_demo.jpg")
                .into(softwares);

        Picasso.with(HomeActivity.this)
                .load("http://atm-india.in/img/CoverImages/warrenty_new_demo.png")
                .into(iv_offers);

        Search_Main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(HomeActivity.this, SearchProducts.class);
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(HomeActivity.this, v, "transit");
                ActivityCompat.startActivity(HomeActivity.this, intent, options.toBundle());

            }
        });

        Zebra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this,ProductsZebra.class));
            }
        });

        Posiflex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this,ProductsPosiflex.class));
            }
        });

        Primera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this,ProductPrimera.class));
            }
        });

        Softwares.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this,Softwares.class));
                //Toast.makeText(HomeActivity.this, "Coming Soon", Toast.LENGTH_SHORT).show();
            }
        });

        rma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                imei =getDeviceID(tm);
                new IsLoggedIn().execute();
            }
        });

        offers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomeActivity.this, "Coming Soon", Toast.LENGTH_SHORT).show();
                //startActivity(new Intent(HomeActivity.this,OffersActivity.class));
            }
        });

       /* one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this,ProductDetails.class);
                i.putExtra("position",0);
                i.putExtra("Class","basicbarcodescanner");
                i.putExtra("CompanyName","Zebra Barcode Scanner");
                i.putExtra("ModelNo","Zebra Scanner LS 1203");
                startActivity(i);
            }
        });

        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this,ProductDetails.class);
                i.putExtra("position",1);
                i.putExtra("Class","basicbarcodescanner");
                i.putExtra("CompanyName","Zebra Barcode Scanner");
                i.putExtra("ModelNo","Zebra Scanner LS 2208 Without Stand");
                startActivity(i);
            }
        });

        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this,ProductDetails.class);
                i.putExtra("position",3);
                i.putExtra("Class","basicbarcodescanner");
                i.putExtra("CompanyName","Zebra Barcode Scanner");
                i.putExtra("ModelNo","Zebra Scanner LI 4278");
                startActivity(i);
            }
        });

        four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this,ProductDetails.class);
                i.putExtra("position",0);
                i.putExtra("Class","desktop");
                i.putExtra("CompanyName","Zebra Barcode Scanner");
                i.putExtra("ModelNo","GT 800");
                startActivity(i);
            }
        });

        five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this,ProductDetails.class);
                i.putExtra("position",1);
                i.putExtra("Class","desktop");
                i.putExtra("CompanyName","Zebra Barcode Scanner");
                i.putExtra("ModelNo","GC 420t");
                startActivity(i);
            }
        });

        six.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this,ProductDetails.class);
                i.putExtra("position",0);
                i.putExtra("Class","kiosk");
                i.putExtra("CompanyName","Zebra Accessories");
                i.putExtra("ModelNo","Zebra 336 IS");
                startActivity(i);
            }
        });

        seven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this,ProductDetails.class);
                i.putExtra("position",2);
                i.putExtra("Class","kiosk");
                i.putExtra("CompanyName","Zebra Accessories");
                i.putExtra("ModelNo","Zebra 340 IS");
                startActivity(i);
            }
        });

        eight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this,ProductDetails.class);
                i.putExtra("position",0);
                i.putExtra("Class","desktop");
                i.putExtra("CompanyName","Zebra Industrial Barcode");
                i.putExtra("ModelNo","ZT 230");
                startActivity(i);
            }
        });

        nine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this,ProductDetails.class);
                i.putExtra("position",3);
                i.putExtra("Class","desktop");
                i.putExtra("CompanyName","Zebra Industrial Barcode");
                i.putExtra("ModelNo","GX 430");
                startActivity(i);
            }
        });

        ten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this,ProductDetails.class);
                i.putExtra("position",1);
                i.putExtra("Class","desktop");
                i.putExtra("CompanyName","Zebra Industrial Barcode");
                i.putExtra("ModelNo","ZT 410");
                startActivity(i);
            }
        });

        Picasso.with(HomeActivity.this)
                .load("http://atm-india.in/img/CoverImages/ls1203.png")
                .into(one);

        Picasso.with(HomeActivity.this)
                .load("http://atm-india.in/img/CoverImages/ls2208withoutstand.png")
                .into(two);

        Picasso.with(HomeActivity.this)
                .load("http://atm-india.in/img/CoverImages/li4278.png")
                .into(three);

        Picasso.with(HomeActivity.this)
                .load("http://atm-india.in/img/CoverImages/gt800.png")
                .resize(250,250)
                .into(four);

        Picasso.with(HomeActivity.this)
                .load("http://atm-india.in/img/CoverImages/gc420t.png")
                .into(five);

        Picasso.with(HomeActivity.this)
                .load("http://atm-india.in/img/CoverImages/zebra336in_is.png")
                .into(six);

        Picasso.with(HomeActivity.this)
                .load("http://atm-india.in/img/CoverImages/zebra340in_is.png")
                .into(seven);

        Picasso.with(HomeActivity.this)
                .load("http://atm-india.in/img/CoverImages/zt230.png")
                .into(eight);

        Picasso.with(HomeActivity.this)
                .load("http://atm-india.in/img/CoverImages/gx430.png")
                .into(nine);

        Picasso.with(HomeActivity.this)
                .load("http://atm-india.in/img/CoverImages/zt410.png")
                .into(ten);*/

        mDemoSlider = (SliderLayout)findViewById(R.id.slider);
        HashMap<String,Parcelable> file_maps = new HashMap<>();

        file_maps.put("1",Uri.parse("http://atm-india.in/img/CoverImages/ic_launcher.png"));
        file_maps.put("2",Uri.parse("http://atm-india.in/img/CoverImages/atm_icon_new.png"));
        file_maps.put("3",Uri.parse("http://atm-india.in/img/CoverImages/atm_demo.png"));
        file_maps.put("4",Uri.parse("http://atm-india.in/img/CoverImages/atm_icon.jpg"));
        file_maps.put("5",Uri.parse("http://atm-india.in/img/CoverImages/shribalaji.png"));
        file_maps.put("6",Uri.parse("http://atm-india.in/img/CoverImages/zebra_business_partner.png"));
        file_maps.put("7",Uri.parse("http://atm-india.in/img/CoverImages/zebra_psp11.png"));
        file_maps.put("8",Uri.parse("http://atm-india.in/img/CoverImages/sri_balaji_logo.jpg"));

        for(String name : file_maps.keySet()){
            TextSliderView textSliderView = new TextSliderView(this);
            textSliderView
                    .image(String.valueOf(file_maps.get(name)))
                    .setScaleType(BaseSliderView.ScaleType.CenterInside)
                    .setOnSliderClickListener(this);

            textSliderView.bundle(new Bundle());
            textSliderView.getBundle().putString("extra", name);

            mDemoSlider.addSlider(textSliderView);
        }
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Default);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomIndicator(pagerIndicator);
        mDemoSlider.setCustomAnimation(new ChildAnimationExample());
        mDemoSlider.setDuration(3000);
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        startActivity(new Intent(HomeActivity.this, AboutUs.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_cart_exit,menu);
        return true;
    }

    public void onExitClicked(MenuItem item){
        new MaterialStyledDialog.Builder(HomeActivity.this)
                .setTitle("Exit")
                .setDescription("Are you sure you want to exit App?")
                .setIcon(R.mipmap.ic_launcher)
                .setPositiveText("Yes")
                .withIconAnimation(true)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                moveTaskToBack(true);

                                android.os.Process.killProcess(android.os.Process.myPid());
                                System.exit(1);

                    }
                })
                .setNegativeText("NO")
                .onNegative(new MaterialDialog.SingleButtonCallback(){
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .setCancelable(true)
                .show();

    }

    public void onCartClicked(MenuItem item){
        if (ListModel.getList().size() != 0) {
            startActivity(new Intent(HomeActivity.this, CartActivity.class));
        } else
            Toast.makeText(HomeActivity.this, "Your Cart is Empty", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        /*final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(HomeActivity.this);
        alertBuilder.setCancelable(true);
        alertBuilder.setTitle("Exit");
        alertBuilder.setMessage("Are you sure you want to exit App?");
        alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(HomeActivity.this, "Exiting the app", Toast.LENGTH_SHORT).show();
                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
            }
        });
        alertBuilder.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alert = alertBuilder.create();
        alert.show();*/
        new MaterialStyledDialog.Builder(HomeActivity.this)
                .setTitle("Exit")
                .setDescription("Are you sure you want to exit App?")
                .setIcon(R.mipmap.ic_launcher)
                .setPositiveText("Yes")
                .withIconAnimation(true)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                moveTaskToBack(true);

                                android.os.Process.killProcess(android.os.Process.myPid());
                                System.exit(1);
                       /* Intent i = new Intent(HomeActivity.this, RegisterActivity.class);
                        i.putExtra("ClassName", "HomeActivity");
                        startActivity(i);*/
                    }
                })
                .setNegativeText("NO")
                .onNegative(new MaterialDialog.SingleButtonCallback(){
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .setCancelable(true)
                .show();
    }

    protected class IsLoggedIn extends AsyncTask<Void, Void, Void> {

        boolean success = false;

        @Override
        protected  void onPreExecute(){
            progressDialog = new ProgressDialog(HomeActivity.this);
            progressDialog.setMessage("Please wait while we check few details...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {

                SoapObject request = new SoapObject(NAMESPACE, "IsLoggedIn");

                request.addProperty("methodName", "IsLoggedIn");

                request.addProperty("imei", imei);

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

                envelope.dotNet = true;

                envelope.setOutputSoapObject(request);
                HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

                androidHttpTransport.call("http://tempuri.org/IsLoggedIn", envelope);

                SoapPrimitive objs = (SoapPrimitive) envelope.getResponse();

                if (objs.toString().contentEquals("UserAlreadyExist")) {
                    GetData();
                    success = true;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();
            if (!success) {
                new MaterialStyledDialog.Builder(HomeActivity.this)
                        .setTitle("Register")
                        .setDescription("Your are still not a member with us plz Register for become member")
                        .setIcon(R.mipmap.ic_launcher)
                        .setPositiveText("Yes")
                        .withIconAnimation(true)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                                Intent i = new Intent(HomeActivity.this, RegisterActivity.class);
                                i.putExtra("ClassName", "HomeActivity");
                                startActivity(i);
                            }
                        })
                        .setNegativeText("NO")
                        .onNegative(new MaterialDialog.SingleButtonCallback(){
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();
                            }
                        })
                        .setCancelable(true)
                        .show();

            } else {
                startActivity(new Intent(HomeActivity.this,Support.class));
            }
        }
    }
    private void GetData() {
        try{
            SoapObject request = new SoapObject(NAMESPACE, "GetDataInfo");

            request.addProperty("imei", imei);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);
            envelope.dotNet = true;

            envelope.setOutputSoapObject(request);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

            androidHttpTransport.call(Register_SOAP_ACTION1, envelope);

            SoapPrimitive objs = (SoapPrimitive) envelope.getResponse();
            progressDialog.dismiss();
            String[] sol = objs.toString().split(",",-1);
            SessionManagement session = new SessionManagement(HomeActivity.this);
            Intent i = new Intent(HomeActivity.this, HomeActivity.class);
            session.createRegisterationSession(sol[0], sol[1], sol[2], sol[3]);
            startActivity(i);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    class AsynkHorizotalCardData extends AsyncTask<Void, Void, Void> {

        ArrayList<String> pklist = new ArrayList<>();
        ArrayList<String> imagelist = new ArrayList<>();
        ArrayList<String> namelist = new ArrayList<>(10);
        ArrayList<String> ratelist = new ArrayList<>();
        ArrayList<String> companyNamelist = new ArrayList<>();
        ArrayList<String> classlist = new ArrayList<>();
        ArrayList<String> positionlist = new ArrayList<>();
        String[] image={"http://atm-india.in/img/CoverImages/ls1203.png",
                "http://atm-india.in/img/CoverImages/ls2208withoutstand.png",
                "http://atm-india.in/img/CoverImages/li4278.png",
                "http://atm-india.in/img/CoverImages/gt800.png",
                "http://atm-india.in/img/CoverImages/gc420t.png",
                "http://atm-india.in/img/CoverImages/zebra336in_is.png",
                "http://atm-india.in/img/CoverImages/zebra340in_is.png",
                "http://atm-india.in/img/CoverImages/zt230.png",
                "http://atm-india.in/img/CoverImages/gx430.png",
                "http://atm-india.in/img/CoverImages/zt410.png"};
        String[] name1 ={"Zebra Scanner LS 1203","Zebra Scanner LS 2208 Without Stand","Zebra Scanner LI 4278","GT 800","GC 420t","Zebra 336 IS","Zebra 340 IS","ZT 230","GX 430","ZT 410"};

        String pklist1;
        String name11 ="";



        ArrayList<String> main = new ArrayList<>();
        ProgressDialog pd = new ProgressDialog(HomeActivity.this);

       /* public AsynkHorizotalCardData(ArrayList<String> name1){
            namelist=name1;
        }*/

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // pd.setMessage("Loading");
            //pd.show();
        }

        @Override
        protected Void doInBackground(Void... avoid) {
            try {
                ListInfoData.listDHorizontalList.clear();


                    SoapObject request = new SoapObject(NAMESPACE, "GetAllfrontData");


                   // request.addProperty("Modelno", name11);
                    SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                    envelope.dotNet = true;
                    envelope.setOutputSoapObject(request);

                    HttpTransportSE transportSE = new HttpTransportSE(URL);
                    transportSE.call(SOAP_ACTIONFront, envelope);

                    SoapObject objs = (SoapObject) envelope.getResponse();
                    //int som=objs.getPropertyCount();

                    for (int i = 0; i < objs.getPropertyCount(); i++) {
                        SoapObject object = (SoapObject) objs.getProperty(i);



                    /*GridViewData sample = new GridViewData();
                    sample.setPk(object.getProperty("S1").toString());
                    sample.setName(object.getProperty("S2").toString());
                    sample.setImage(object.getProperty("S4").toString());
                    sample.setRate(object.getProperty("S3").toString());


*/


                        pklist.add(object.getProperty("S1").toString());
                       namelist.add(object.getProperty("S2").toString());
                        ratelist.add(object.getProperty("S3").toString() + " +GST*");
                      //  imagelist.add(object.getProperty("S4").toString());
                        positionlist.add(object.getProperty("S5").toString());
                        classlist.add(object.getProperty("S6").toString());
                        companyNamelist.add(object.getProperty("S7").toString());

                    /*colourlist.add(object.getProperty("S6").toString());
                    desclist.add(object.getProperty("S7").toString());*/
                        int ff = Integer.parseInt(pklist.get(i));
                        if (ff <= 10 && ff >= 1) {
                            ListInfoData.setListDHorizontalList(pklist.get(i),image[i], namelist.get(i), ratelist.get(i), companyNamelist.get(i), classlist.get(i), positionlist.get(i));
                        }
                    }


            } catch (Exception e) {
                e.printStackTrace();
                //pd.dismiss();
            }


            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);


            cardRecycleAdaapter = new HorizontalCardRecycleAdaapter(HomeActivity.this, ListInfoData.listDHorizontalList);
            cardRecycleView.setAdapter(cardRecycleAdaapter);
            //pd.dismiss();

        }
    }




    /*    private class GetRate extends AsyncTask<Void, Void, Void> {
            boolean toSet = false;
            ProgressDialog progressDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = new ProgressDialog(HomeActivity.this);
                progressDialog.setMessage("Please wait while we fetch some information...");
                progressDialog.setCancelable(false);
                progressDialog.show();
            }

            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    String NAMESPACE = "http://tempuri.org/";
                    String METHOD_NAME = "GetRate";
                    for (int i=0;i<ModelNo.length;i++) {
                        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

                        request.addProperty("methodName", METHOD_NAME);

                        request.addProperty("ModelNumber", ModelNo[i]);

                        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

                        envelope.dotNet = true;

                        envelope.setOutputSoapObject(request);
                        String URL = "http://atm-india.in/Service.asmx";
                        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

                        String SOAP_ACTION = "http://tempuri.org/GetRate";
                        androidHttpTransport.call(SOAP_ACTION, envelope);

                        SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
                        if (response.toString().contentEquals("-")) {
                            Rate = "On Demand";
                            // Amount = "-";
                            //shri change
                            Amount = "0";

                        } else {
                            Rate = response.toString();
                            Amount = response.toString();
                            toSet = true;
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                if (toSet) {
                    for (int i=0;i<mylist.size();i++){
                    rate[i].setText(Rate + " Rs./Pcs. + GST (18%) ");}
                } else
                    for (int i=0;i<mylist.size();i++){
                    rate[i].setText("Price On Demand! + GST (18%) ");}

                progressDialog.dismiss();
            }
        }*/
    String getDeviceID(TelephonyManager phonyManager){

        String id = phonyManager.getDeviceId();
        if (id == null){
            id = "not available";
        }

        int phoneType = phonyManager.getPhoneType();
        switch(phoneType){
            case TelephonyManager.PHONE_TYPE_NONE:
                return id;

            case TelephonyManager.PHONE_TYPE_GSM:
                return id;

            case TelephonyManager.PHONE_TYPE_CDMA:
                return  id;

            default:
                return  id;
        }
    }

}
