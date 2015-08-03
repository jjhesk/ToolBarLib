package com.hkm.toolbarlib;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by hesk on 3/8/15.
 */
public class mainMenu extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_menu);
    }

    private void startApp(Class<?> k) {
        Intent g = new Intent(this, k);
        startActivity(g);
    }

    public void ios(View view) {
        startApp(CustomActionBar.class);
    }

    public void main3(View view) {
        startApp(Main3.class);
    }

    public void main2(View view) {
        startApp(Main2.class);
    }

    public void main4(View view) {
        startApp(Main4.class);
    }

    public void candybarimple(View view) {
        startApp(TestOfCandyBar.class);
    }
}
