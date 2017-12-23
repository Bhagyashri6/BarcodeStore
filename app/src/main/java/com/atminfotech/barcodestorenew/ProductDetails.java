package com.atminfotech.barcodestorenew;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.HashMap;

public class ProductDetails extends AppCompatActivity implements BaseSliderView.OnSliderClickListener {
    String imei;
    String name,mobile,str,email;

    SliderLayoutNew mDemoSlider;
    PagerIndicator pagerIndicator;

    int id, resId = 0, j = 0, Qty = 1;

    String Class, CompanyName, ModelNo, Source, Rate, Amount;

    TextView co_name, model, discription1, addtoCart, rate, goToCart;

    LinearLayout linear1, linear2;

    int x = 0, y = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_details);


        SessionManagement session =new SessionManagement(ProductDetails.this);
        HashMap<String, String> user = session.getUserDetails();

        name = user.get(SessionManagement.KEY_NAME);
        mobile = user.get(SessionManagement.KEY_Mobile);
        email = user.get(SessionManagement.KEY_EmailId);
        imei=user.get(SessionManagement.KEY_Imei);


        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                id = 0;
                Class = CompanyName = ModelNo = null;
            } else {
                id = extras.getInt("position");
                Class = extras.getString("Class");
                CompanyName = extras.getString("CompanyName");
                ModelNo = extras.getString("ModelNo");
            }
        }

        new GetRate().execute();

        x = id + (id + 1);
        y = (x + 1);

        co_name = (TextView) findViewById(R.id.est);
        model = (TextView) findViewById(R.id.itemname);

        linear1 = (LinearLayout) findViewById(R.id.linear1);
        linear2 = (LinearLayout) findViewById(R.id.linear2);
        discription1 = (TextView) findViewById(R.id.discription1);
        addtoCart = (TextView) findViewById(R.id.buy);
        goToCart = (TextView) findViewById(R.id.go_to_cart);
        rate = (TextView) findViewById(R.id.price);
        pagerIndicator = (PagerIndicator) findViewById(R.id.banner_slider_indicator);

        goToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ListModel.getList().size() != 0) {
                    startActivity(new Intent(ProductDetails.this, CartActivity.class));
                } else
                    Toast.makeText(ProductDetails.this, "Your Cart is Empty", Toast.LENGTH_SHORT).show();
            }
        });

        addtoCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for (int i = 0; i < ListModel.getList().size(); i++) {

                    if (ListModel.getList().get(i).getDesc().equals(ModelNo)) {
                        Toast.makeText(ProductDetails.this, "There is already one such item in cart", Toast.LENGTH_SHORT).show();
                        ListModel.getList().get(i).setQty(((ListModel.getList().get(i).getQty()) + 1));
                        if (!ListModel.getList().get(i).getRate().contentEquals("On Demand")) {
                                ListModel.getList().get(i).setAmount(String.valueOf(((Integer.parseInt(ListModel.getList().get(i).getRate())) * ListModel.getList().get(i).getQty())));
                        }
                        j = 1;
                    }
                }
                if (j == 0) {
                    ListModel.setList("http://atm-india.in/img/barcodeimg/" + (String.valueOf(Class) + x) + ".png", CompanyName, ModelNo, Rate, Amount, Qty);
                }

                Toast.makeText(ProductDetails.this, "Added to Cart", Toast.LENGTH_SHORT).show();
            }
        });

        co_name.setText(CompanyName);
        model.setText("Model Name : " + ModelNo);

        Source = ModelNo.replaceAll(" ", "_");


        resId = getResources().getIdentifier(Source, "string", getPackageName());
        discription1.setText(getString(resId));
        linear1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                linear2.setVisibility(View.VISIBLE);
                linear1.setVisibility(View.GONE);
                discription1.setVisibility(View.VISIBLE);

            }
        });

        linear2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                linear2.setVisibility(View.GONE);
                linear1.setVisibility(View.VISIBLE);
                discription1.setVisibility(View.GONE);

            }
        });

        mDemoSlider = (SliderLayoutNew) findViewById(R.id.slider);

        HashMap<String, Parcelable> file_maps = new HashMap<>();

        file_maps.put("1", Uri.parse("http://atm-india.in/img/barcodeimg/" + (String.valueOf(Class) + x) + ".png"));
        file_maps.put("2", Uri.parse("http://atm-india.in/img/barcodeimg/" + (String.valueOf(Class) + y) + ".png"));


        for (String name : file_maps.keySet()) {

            TextSliderView textSliderView = new TextSliderView(this);
            textSliderView
                    .image(String.valueOf(file_maps.get(name)))
                    .setScaleType(BaseSliderView.ScaleType.CenterCrop)
                    .setOnSliderClickListener(this);

            textSliderView.bundle(new Bundle());
            textSliderView.getBundle().putString("extra", name);

            mDemoSlider.addSlider(textSliderView);
        }
        mDemoSlider.setPresetTransformer(SliderLayoutNew.Transformer.Default);
        mDemoSlider.setPresetIndicator(SliderLayoutNew.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new ChildAnimationExample());
        mDemoSlider.setCustomIndicator(pagerIndicator);
        mDemoSlider.setDuration(2000);

    }

    private class GetRate extends AsyncTask<Void, Void, Void> {
        boolean toSet = false;
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(ProductDetails.this);
            progressDialog.setMessage("Please wait while we fetch some information...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                String NAMESPACE = "http://tempuri.org/";
                String METHOD_NAME = "GetRate";
                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

                request.addProperty("methodName", METHOD_NAME);

                request.addProperty("ModelNumber", ModelNo);

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



            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (toSet) {
                if (Rate.equals("0")){
                    rate.setText("Price On Demand! + GST (18%) ");
                }else {
                rate.setText(Rate + " Rs./Pcs. + GST (18%) ");}
            } else rate.setText("Price On Demand! + GST (18%) ");
            progressDialog.dismiss();
        }
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_home,menu);
        return true;
    }

    public void onHomeClicked(MenuItem menu){
        startActivity(new Intent(ProductDetails.this,HomeActivity.class));
        finish();
    }

    @Override
    public void onBackPressed() {

    }
}
