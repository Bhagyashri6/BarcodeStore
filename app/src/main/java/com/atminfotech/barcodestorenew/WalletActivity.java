package com.atminfotech.barcodestorenew;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;

import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;

import java.util.HashMap;

public class WalletActivity extends AppCompatActivity implements BaseSliderView.OnSliderClickListener{


    SliderLayout mDemoSlider;
    PagerIndicator pagerIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);

        pagerIndicator = (PagerIndicator) findViewById(R.id.banner_slider_indicator);
        mDemoSlider = (SliderLayout)findViewById(R.id.slider);
        HashMap<String,Parcelable> file_maps = new HashMap<>();

        file_maps.put("1", Uri.parse("http://atm-india.in/img/CoverImages/ic_launcher.png"));
        file_maps.put("2",Uri.parse("http://atm-india.in/img/CoverImages/atm_icon_new.png"));
        file_maps.put("3",Uri.parse("http://atm-india.in/img/CoverImages/atm_demo.png"));
        file_maps.put("4",Uri.parse("http://atm-india.in/img/CoverImages/atm_icon.jpg"));
        file_maps.put("5",Uri.parse("http://atm-india.in/img/CoverImages/sri_balaji_logo.jpg"));
        file_maps.put("6",Uri.parse("http://atm-india.in/img/CoverImages/zebra_business_partner.png"));
        file_maps.put("7",Uri.parse("http://atm-india.in/img/CoverImages/zebra_psp.png"));

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
        startActivity(new Intent(WalletActivity.this, AboutUs.class));
    }

    @Override
    public void onBackPressed() {

    }
}
