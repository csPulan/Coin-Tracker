package com.example.cryptoscholartracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements CurrencyRVAdapter.SelectedItem{


    public static final String PREFS_NAME = "Test";
    public CheckBox check;
    BottomNavigationView bottomNavigationView;
    private RecyclerView courseRV;

    SwitchCompat switchCompat;
    SharedPreferences sharedPreferences = null;
    public static SharedPreferences sharedPrefHomeTutorial = null;


    // variable for our adapter
    // class and array list
    private CurrencyRVAdapter adapter;
    private ArrayList<CurrencyRVModal> currencyModalArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.home);

        sharedPrefHomeTutorial = getSharedPreferences("label",0);
        Boolean mBool = sharedPrefHomeTutorial.getBoolean("tagHome",false);

        if(mBool ==true){

            homeTutorial.checkHome = true;

        }else{
            homeTutorial();
        }



        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.tracker:
                        finishAffinity();
                        startActivity(new Intent(getApplicationContext(), trackerActivity.class));
                        overridePendingTransition(0, 0);
                        break;

                    case R.id.home:
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
        switchCompat = findViewById(R.id.switchCompat);
        sharedPreferences = getSharedPreferences("night",0);
        Boolean booleanValue = sharedPreferences.getBoolean("night_mode",false);
        if (booleanValue){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            switchCompat.setChecked(true);
        }



        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    switchCompat.setChecked(true);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("night_mode",true);
                    editor.commit();
                }else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    switchCompat.setChecked(false);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("night_mode",false);
                    editor.commit();

                }
            }
        });

        courseRV = findViewById(R.id.idRVCourses);
        // calling method to
        // build recycler view.
        buildRecyclerView();

    }

    // calling on create option menu
    // layout to inflate our menu file.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // below line is to get our inflater
        MenuInflater inflater = getMenuInflater();

        // inside inflater we are inflating our menu file.
        inflater.inflate(R.menu.homemenu, menu);

        // below line is to get our menu item.
        MenuItem searchItem = menu.findItem(R.id.actionSearch);

        // getting search view of our item.
        SearchView searchView = (SearchView) searchItem.getActionView();


        // below line is to call set on query text listener method.
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // inside on query text change method we are
                // calling a method to filter our recycler view.
                filter(newText);
                return false;
            }
        });
        return true;
    }

    private void filter(String currency) {
        // creating a new array list to filter our data.
        ArrayList<CurrencyRVModal> filteredlist = new ArrayList<>();

        // running a for loop to compare elements.
        for (CurrencyRVModal item : currencyModalArrayList) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.getName().toLowerCase().contains(currency.toLowerCase())||item.getSymbol().toLowerCase().contains(currency.toLowerCase())) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredlist.add(item);
            }
        }
        if (filteredlist.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
            Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show();
        } else {
            // at last we are passing that filtered
            // list to our adapter class.
            adapter.filterList(filteredlist);
        }
    }

    private void buildRecyclerView() {

        // below line we are creating a new array list
        currencyModalArrayList = new ArrayList<>();

        // below line is to add data to our array list.
        String url = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    // extracting data from json.
                    JSONArray dataArray = response.getJSONArray("data");
                    for (int i = 0; i < dataArray.length(); i++) {
                        JSONObject dataObj = dataArray.getJSONObject(i);
                        String symbol = dataObj.getString( "symbol");
                        String name = dataObj.getString("name");
                        String slug = dataObj.getString("slug");
                        JSONObject quote = dataObj.getJSONObject("quote");
                        JSONObject USD = quote.getJSONObject("USD");
                        double volume24h = USD.getDouble("volume_24h");
                        double volume_change_24h = USD.getDouble("volume_change_24h");
                        double market_cap = USD.getDouble("market_cap");
                        double percent_1h = USD.getDouble("percent_change_1h");
                        double percent_24h = USD.getDouble("percent_change_24h");
                        double percent_7d = USD.getDouble("percent_change_7d");
                        double price = USD.getDouble("price");
                        currencyModalArrayList.add(new CurrencyRVModal(name, symbol, slug, price, volume_change_24h, volume24h, market_cap, percent_1h, percent_24h, percent_7d));
                    }

                    adapter.notifyDataSetChanged();

                }catch (JSONException e){
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this,"Fail to get the data",Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(MainActivity.this,"Fail to get the data",Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            public Map<String, String> getHeaders(){
                HashMap<String,String> headers = new HashMap<>();
                headers.put("X-CMC_PRO_API_KEY","4e230ec4-4e2b-43ef-85d0-f7cfaa003f96");
                return  headers;

            }
        };
        requestQueue.add(jsonObjectRequest);


        // initializing our adapter class.
        adapter = new CurrencyRVAdapter(currencyModalArrayList, MainActivity.this,this);

        // adding layout manager to our recycler view.
        LinearLayoutManager manager = new LinearLayoutManager(this);
        courseRV.setHasFixedSize(true);

        // setting layout manager
        // to our recycler view.
        courseRV.setLayoutManager(manager);

        // setting adapter to
        // our recycler view.
        courseRV.setAdapter(adapter);
    }

    private void openTrackerTut()
    {
        trackerTutDialog trackerTutDialog = new trackerTutDialog();
        trackerTutDialog.show(getSupportFragmentManager(),"alert dialog");
    }

    @Override
    public void selectedItem(CurrencyRVModal currencyRVModal) {

        startActivity(new Intent(MainActivity.this, InfoActivity.class).putExtra("data",currencyRVModal));
    }


    private void homeTutorial()
    {
        homeTutorial homeTutorial = new homeTutorial();
        homeTutorial.show(getSupportFragmentManager(), "home tutorial");
    }

}