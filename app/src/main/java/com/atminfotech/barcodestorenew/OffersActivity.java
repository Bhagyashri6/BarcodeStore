package com.atminfotech.barcodestorenew;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class OffersActivity extends AppCompatActivity {

    //CircularMusicProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offers);

        /*progressBar = (CircularMusicProgressBar) findViewById(R.id.album_art);

        progressBar.setValue(33);*/

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_home,menu);
        return true;
    }

    public void onHomeClicked(MenuItem menu){
        startActivity(new Intent(OffersActivity.this,HomeActivity.class));
        finish();
    }

    @Override
    public void onBackPressed() {

    }
}
