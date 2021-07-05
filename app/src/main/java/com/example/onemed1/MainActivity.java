package com.example.onemed1;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Menu;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.onemed1.databinding.ActivityMainBinding;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;


import java.util.Map;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    public static String email;
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    EditText mEmail,mPassword;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;
    Button mLoginBtn;
    TextView mCreateBtn;
    ProgressBar progressBar;
    FirebaseAuth fAuth;
    String oEm="",oPs="";
    public String snip;
    public int sn = 0;
    FloatingActionButton fab;
    Spinner spinner;
    public static final String USER_NAME = "com.example.onemed1.username";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference();

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
        mEmail = findViewById(R.id.Email);
        mPassword = findViewById(R.id.password);
        progressBar = findViewById(R.id.progressBar);
        fAuth = FirebaseAuth.getInstance();
        mLoginBtn = findViewById(R.id.loginBtn);
        mCreateBtn = findViewById(R.id.createText);
        fab = findViewById(R.id.fab);
        progressBar.setVisibility(View.GONE);
        //Intent of sending the email when the user clicks the sos button
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
        //On clicking the login button the use will be re-directed to the selected login homepage
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                if (TextUtils.isEmpty(email)) {
                    mEmail.setError("Email is Required.");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    mPassword.setError("Password is Required.");
                    return;
                }

                if (password.length() < 6) {
                    mPassword.setError("Password Must be >= 6 Characters");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                // authenticate the user
                if(sn==2) {
                    //chekcing the login id and password for the pharmacy and the login for organisation should always be numeric
                    if(TextUtils.isDigitsOnly(email))
                        validate_pharmacy(email, password);
                    else
                    {
                        mEmail.setError("For organisation and the pharmacy the email should only be numeric");
                        return;
                    }
                }
                if(sn==1)
                {
                    //chekcing the login id and password for the organisation and the login for organisation should always be numeric
                    if(TextUtils.isDigitsOnly(email))
                    validate_organisation(email,password);
                    else
                    {
                        mEmail.setError("For organisation and the pharmacy the email should only be numeric");
                        return;
                    }
                }
                if(sn==0||sn==3) {
                    //comparing the entered user id and password with the registered user in the firebase and autheticating the login process for the patient
                    fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(MainActivity.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), Homepage_activity.class));
                                progressBar.setVisibility(View.GONE);
                            } else {
                                Toast.makeText(MainActivity.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }

                        }

                    });
                }
            }
        });
        //Transferring the user to register if he doesnot have any account
        mCreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Register.class));
            }
        });
    }
    //Validating the login of the user if he is from the pharmacy by checking the user-id node with the password in the child of the parent node
    public void validate_pharmacy(String em, String ps) {
        mRef.child("Pharmacy Users").child(em).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Map<String,Object> data = (Map<String, Object>)snapshot.getValue();
                oEm = snapshot.child("id").getValue(String.class);
                oPs = snapshot.child("password").getValue(String.class);
                if (oEm != null && oPs != null) {
                    if (oEm.equals(em) && oPs.equals(ps)) {
                        Toast.makeText(MainActivity.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, Homepage_Pharmacy.class);
                        startActivity(intent);
                    }
                    else
                    {
                        mPassword.setError("Password maybe incorrect");
                        mEmail.setError("User-id Maybe incorrect");
                        progressBar.setVisibility(View.GONE);
                    }
                }
                else
                {
                    mPassword.setError("Password incorrect");
                    mEmail.setError("User-id Maybe incorrect");
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NotNull DatabaseError error) {
            }
        });
    }
    //Validating the login of the user if he is from the organisation by checking the user-id node with the password in the child of the parent node
    public void validate_organisation(String em, String ps) {
        mRef.child("Organisation Users").child(em).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot snapshot) {
                Map<String, Object> data = (Map<String, Object>) snapshot.getValue();
                oEm = snapshot.child("oid").getValue(String.class);
                oPs = snapshot.child("password").getValue(String.class);
                if (oEm != null && oPs != null) {
                    if (oEm.equals(em) && oPs.equals(ps)) {
                        Toast.makeText(MainActivity.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, Homepage_activity.class);
                        startActivity(intent);
                    }
                    else
                    {
                        mPassword.setError("Password maybe incorrect");
                        mEmail.setError("User-id Maybe incorrect");
                        progressBar.setVisibility(View.GONE);
                    }
                }
                else
                {
                    mPassword.setError("Password incorrect");
                    mEmail.setError("User-id Maybe incorrect");
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NotNull DatabaseError error) {
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

//For the snipper option choosed and updating the login-style
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        snip = spinner.getSelectedItem().toString();
        if(snip.equals("Select Login-Style")) {
            sn=0;
            Toast.makeText(this, "Select the style you want to login", Toast.LENGTH_SHORT).show();
        } else if (snip.equals("Organisation")) {
            Toast.makeText(this, "You will be logged as organisation", Toast.LENGTH_SHORT).show();
            sn = 1;
        } else if (snip.equals("Pharmacy")) {
            Toast.makeText(this, "You will be logged as Pharmacy", Toast.LENGTH_SHORT).show();
            sn = 2;
        } else if (snip.equals("Patient")) {
            Toast.makeText(this, "You will be logged as patient", Toast.LENGTH_SHORT).show();
            sn = 3;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Toast.makeText(this, "Select the style you want to login on nothing selected", Toast.LENGTH_SHORT).show();

    }

}



