package com.example.cryptoscholartracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import javax.annotation.Nullable;

public class trackerActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    private ArrayList<GameItem> mGameList;
    private GameAdapter mAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracker);
        getSupportActionBar().hide();


        bottomNavigationView = findViewById(R.id.bottom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.tracker);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.tracker:

                        return true;

                    case R.id.home:
                        finishAffinity();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0, 0);
                        return true;


                    case R.id.account:
                        finishAffinity();
                        startActivity(new Intent(getApplicationContext(), accountActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                }

                return false;
            }
        });

        initList();

        Spinner spinnerGames =findViewById(R.id.spinner_games);
        mAdapter = new GameAdapter(this,mGameList);
        spinnerGames.setAdapter(mAdapter);

        spinnerGames.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                GameItem clickedItem = (GameItem) parent.getItemAtPosition(position);
                String clickedGameName= clickedItem.getGameName();

                if (clickedGameName == "CryptoBlades")
                {
                    Toast.makeText(trackerActivity.this, "Coming Soon", Toast.LENGTH_SHORT).show();
                }
                else if (clickedGameName == "Axie")
                {
                    startActivity(new Intent(getApplicationContext(),axiewithHistory.class));
                }
                else
                {
                    return;
                }

                Toast.makeText(trackerActivity.this, clickedGameName+" selected", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });




    }

    private void initList(){
        mGameList = new ArrayList<>();
        mGameList.add(new GameItem("Select Coin",R.drawable.coin));
        mGameList.add(new GameItem("Axie",R.drawable.slp));
        mGameList.add(new GameItem("CryptoBlades",R.drawable.skill));
    }


}