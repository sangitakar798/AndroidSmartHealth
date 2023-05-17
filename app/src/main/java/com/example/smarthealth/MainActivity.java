package com.example.smarthealth;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    DrawerLayout drawer_layout;
    EditText name_text;
    EditText agetext;
    ImageView enter;
    RadioButton male;
    RadioButton female;
    RadioGroup rg1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        drawer_layout = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolbar);
        name_text = findViewById(R.id.name_text);
        agetext = findViewById(R.id.agetext);
        enter = findViewById(R.id.imageView7);
        male = findViewById(R.id.male);
        female = findViewById(R.id.female);
        rg1 = findViewById(R.id.rg1);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer_layout.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView nav = findViewById(R.id.nav_view);

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = name_text.getText().toString();
                String age = agetext.getText().toString();
                String gender = "";

                int id = rg1.getCheckedRadioButtonId();
                switch (id) {
                    case R.id.male:
                        gender = "Mr.";
                        break;
                    case R.id.female:
                        gender = "Ms.";
                        break;
                }

                Intent symp = new Intent(MainActivity.this, SymptomsActivity.class);
                symp.putExtra("name", name);
                symp.putExtra("age", age);
                symp.putExtra("gender", gender);
                startActivity(symp);
            }
        });

        nav.setNavigationItemSelectedListener(item -> {

            switch (item.getItemId()) {
                case R.id.nav_sos:
                    Intent in = new Intent(MainActivity.this, Sos_Call.class);
                    startActivity(in);
                    break;

                case R.id.nav_share:
                    Intent myIntent = new Intent(Intent.ACTION_SEND);
                    myIntent.setType("text/plain");
                    startActivity(Intent.createChooser(myIntent, "SHARE USING"));
                    break;

                case R.id.nav_hosp:
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                    browserIntent.setData(Uri.parse("https://www.google.com/maps/search/hospitals+near+me"));
                    startActivity(browserIntent);
                    break;
                case R.id.nav_cntus:
                    Intent c_us = new Intent(MainActivity.this, activity_contact.class);
                    startActivity(c_us);
                    break;
                case R.id.nav_check:
                    Intent dis = new Intent(MainActivity.this, SymptomsActivity.class);
                    startActivity(dis);
                    break;
            }
            return false;
        });
    }
}
