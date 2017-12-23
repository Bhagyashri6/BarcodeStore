package com.atminfotech.barcodestorenew;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class AboutUs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View aboutPage = new AboutPage(AboutUs.this)
                .isRTL(false)
                .setImage(R.mipmap.atm_icon)
                .addItem(new Element().setTitle("Version 1.0"))
                .addGroup("Connect with us")
                .addEmail("atminfotech@ymail.com")
                .addTwitter("9619900785")
                .addPlayStore("9664008090")
                .addRma("02265924421")
                .addTwitterA("9619900785")
                .addPlayStoreA("9930699901")
                .addAndroid("02265324421")
                .addwins1("02265124421")
                .addwins2("02265424421")
                .addwins3("02265524421")
                .addweb("02265624421")
                .adddWebsite("www.barcodestore.in")
                .addWebsite("www.atminfotech.com")
                .addItem(getCopyRightsElement())
                .create();

        setContentView(aboutPage);
    }


    Element getCopyRightsElement() {
        Element copyRightsElement = new Element();
        String copyrights = "Copyrights © 2017";
        copyRightsElement.setTitle(copyrights);
        copyRightsElement.setIcon(R.drawable.about_icon_copy_right);
        copyRightsElement.setColor(ContextCompat.getColor(this, R.color.about_item_icon_color));
        copyRightsElement.setGravity(Gravity.CENTER);

        return copyRightsElement;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_home,menu);
        return true;
    }

    public void onHomeClicked(MenuItem menu){
        startActivity(new Intent(AboutUs.this,HomeActivity.class));
        finish();
    }

    @Override
    public void onBackPressed() {

    }
}
