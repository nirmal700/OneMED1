package com.example.onemed1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.onemed1.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private EditText username ;
    private EditText password;
    private Button login;
    private String luser ="Admin";
    private String lpassword="12345678";
    public String snip;
    public int sn=0;
    int isValid = -1;
    FloatingActionButton fab;
    Spinner spinner;
    public static final String USER_NAME = "com.example.onemed1.username";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        spinner = findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        fab = findViewById(R.id.fab);
                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String urlText = "Its a sos";
                        Toast.makeText(MainActivity.this, "Hey sending a Mail", Toast.LENGTH_SHORT).show();
                        String[] addresses = {"k.nirmalkumar2002@gmail.com", "cst.20bcta16@silicon.ac.in"};
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("*/*");
                        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
                        intent.putExtra(Intent.EXTRA_SUBJECT, "OneMED subject");
                        intent.putExtra(Intent.EXTRA_TEXT, urlText);
                        if (intent.resolveActivity(getPackageManager()) != null) {
                            startActivity(intent);
                        }
                    }
                });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputname =username.getText().toString();
                String inputpassword=password.getText().toString();
                if(inputname.isEmpty()||inputpassword.isEmpty())
                {
                    Toast.makeText(MainActivity.this, "Invalid User-ID or Password", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    isValid = validate(inputname,inputpassword);
                    if(isValid==1)
                    {
                        Toast.makeText(MainActivity.this, "Login Successfull", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(MainActivity.this, Homepage_activity.class);
                                intent.putExtra(USER_NAME, inputname);
                                startActivity(intent);
                                isValid=0;
                    }
                    else if(isValid==2)
                    {
                        Toast.makeText(MainActivity.this, "Login Successfull", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, Homepage_Pharmacy.class);
                        intent.putExtra(USER_NAME, inputname);
                        startActivity(intent);
                        isValid=0;
                    }
                    else if(isValid==3)
                    {
                        Toast.makeText(MainActivity.this, "Login Successfull", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, Homepage_Patient.class);
                        intent.putExtra(USER_NAME, inputname);
                        startActivity(intent);
                        isValid=0;
                    }

                    else
                    {
                        Toast.makeText(MainActivity.this, "Login Unsuccessfull", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        snip = spinner.getSelectedItem().toString();
        if(snip.equals("Select Login-Style"))
        {
            Toast.makeText(this, "Select the style you want to login", Toast.LENGTH_SHORT).show();
        }
        else if (snip.equals("Organisation"))
        {
            sn=1;
            Toast.makeText(this, "You will be logged as organisation", Toast.LENGTH_SHORT).show();
        }
        else if(snip.equals("Pharmacy"))
        {
            sn=2;
            Toast.makeText(this, "You will be logged as Pharmacy", Toast.LENGTH_SHORT).show();
        }
        else if (snip.equals("Patient"))
        {
            sn=3;
            Toast.makeText(this, "You will be logged as patient", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Toast.makeText(this, "Select the style you want to login on nothing selected", Toast.LENGTH_SHORT).show();

    }
    private int validate(String name,String pass)
    {
        if(name.equals(luser) && pass.equals(lpassword)&&sn==1)
            return 1 ;
        else if (name.equals(luser) && pass.equals(lpassword)&&sn==2)
            return 2;
        else if(name.equals(luser) && pass.equals(lpassword)&&sn==3)
            return 3;
        else
            return -1;

    }
}