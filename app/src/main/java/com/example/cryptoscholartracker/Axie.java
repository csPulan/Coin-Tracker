package com.example.cryptoscholartracker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

public class Axie extends AppCompatActivity {

    //getting info of user
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private EditText  seditTextScholar, seditTextManager;
    private Button sokButton;
    private Button scancelButton;

    public static SharedPreferences sharedPrefAxieTutorial;


    //getting info through API
    private TextView sslp_showValue;
    private EditText sinput_Address;
    private TextView saddress_showText;
    private TextView sname_showText;
    private TextView stotalSlp_showText;
    private TextView smanagerSlp_showText;
    private TextView sscholarSlp_showText;
    private TextView sslpValue_Total;
    private TextView sslpValue_Manager;
    private TextView sslpValue_Scholar;
    private TextView syesterdaySlp_showText;
    private TextView stodaySlp_showText;
    private TextView smmr_showText;
    private Button srefresh_Button;
    private ProgressBar loadingBar;
    private Context context;
    private String savedAddress;
    private String loadAddress;

    private RequestQueue mQueue;

    String url;
    String urlName;
    String urlSLP;
    String key;
    String name;
    String sManager = "";
    String sScholar = "";
    String accName = "";
    double slpvalue;
    float manager;
    float scholar;
    float manager_cut;
    float scholar_cut;

    //replacing variables to data from api
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_axie);getSupportActionBar().hide();

        sslp_showValue = findViewById(R.id.slp_showValue);
        sinput_Address = findViewById(R.id.input_address);
        saddress_showText = findViewById(R.id.address_showText);
        sname_showText = findViewById(R.id.name_showText);
        stotalSlp_showText = findViewById(R.id.totalSlp_showText);
        smanagerSlp_showText = findViewById(R.id.managerSlp_showText);
        sscholarSlp_showText = findViewById(R.id.scholarSlp_showText);
        sslpValue_Total = findViewById(R.id.slpValue_Total);
        sslpValue_Manager = findViewById(R.id.slpValue_Manager);
        sslpValue_Scholar = findViewById(R.id.slpValue_Scholar);
        syesterdaySlp_showText = findViewById(R.id.yesterdaySlp_showText);
        stodaySlp_showText = findViewById(R.id.todaySlp_showText);
        smmr_showText = findViewById(R.id.mmr_showText);
        srefresh_Button = findViewById(R.id.refresh_Button);
        loadingBar = findViewById(R.id.loadingData);

        Button sfind_addressButton = findViewById(R.id.find_addressButton);


        mQueue = Volley.newRequestQueue(this);

        sharedPrefAxieTutorial = getSharedPreferences("label",0);
        Boolean mBool = sharedPrefAxieTutorial.getBoolean("tagAxie",false);

        if(mBool ==true){

            axieTutorial.checkAxie = true;

        }else{
            axieTutorial();
        }

        sinput_Address.setText(null);
        sfind_addressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeKeyboard();
                createNewPopupDialog();
                saveRoninAddress();
            }

        });

        srefresh_Button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                if(sfind_addressButton !=null){
                    closeKeyboard();
                    jsonParse();
                    getSLPValue();
                }

            }
        });

        applySavedAddress();

    }

    private void createNewPopupDialog(){
        dialogBuilder = new AlertDialog.Builder(this);
        final View popUp = getLayoutInflater().inflate(R.layout.popup, null);


        seditTextScholar = (EditText) popUp.findViewById(R.id.editTextScholar);
        seditTextManager = (EditText) popUp.findViewById(R.id.editTextManager);

        sokButton = (Button) popUp.findViewById(R.id.saveButton);
        scancelButton = (Button) popUp.findViewById(R.id.cancelButton);

        dialogBuilder.setView(popUp);
        dialog = dialogBuilder.create();
        dialog.show();


        seditTextScholar.addTextChangedListener(enableButton);
        seditTextManager.addTextChangedListener(enableButton);

        context = getApplicationContext();
        seditTextScholar.requestFocus();
        InputMethodManager imgr = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imgr.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

        sokButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                imgr.hideSoftInputFromWindow(seditTextScholar.getWindowToken(), 0);
                key = sinput_Address.getText().toString().trim();
                url = "https://axie-scho-tracker-server.herokuapp.com/api/account/" + key;
                urlSLP = "https://api.coingecko.com/api/v3/simple/token_price/ethereum?contract_addresses=0xcc8fa225d80b9c7d42f96e9570156c65d6caaa25&vs_currencies=php";

                sManager = seditTextManager.getText().toString();
                sScholar = seditTextScholar.getText().toString();
                manager = Integer.parseInt(sManager);
                scholar = Integer.parseInt(sScholar);

                int totalPercent = (int) (manager + scholar);

                if(totalPercent == 100)
                {
                    jsonParse();
                    getSLPValue();
                    closeKeyboard();
                    dialog.dismiss();
                }else if (totalPercent > 100 || totalPercent<100){

                    Toast.makeText(Axie.this, "Invalid Percentage", Toast.LENGTH_SHORT).show();

                }
                return;
            }
        });

        scancelButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                imgr.hideSoftInputFromWindow(seditTextScholar.getWindowToken(), 0);
                dialog.dismiss();
                sinput_Address.setText(null);
            }
        });


    }

    private TextWatcher enableButton = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            if (charSequence.toString().contains(",") || charSequence.toString().contains(".") || charSequence.toString().contains("-"))
            {
                Toast.makeText(Axie.this, "Use numbers only", Toast.LENGTH_SHORT).show();
            }else{
                String cManager = seditTextManager.getText().toString();
                String cScholar = seditTextScholar.getText().toString();
                sokButton.setEnabled(!cManager.isEmpty() && !cScholar.isEmpty());
            }






        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };


    private void closeKeyboard(){
        View view = this.getCurrentFocus();

        if(view!=null){
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
    }

    private void getSLPValue(){

        JsonObjectRequest requestSlp =  new JsonObjectRequest(Request.Method.GET, urlSLP, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONObject object1 = new JSONObject(response.toString());
                    JSONObject slpDatas = object1.getJSONObject("0xcc8fa225d80b9c7d42f96e9570156c65d6caaa25");
                    //getting price of SLP
                    double slpPrice = slpDatas.getDouble("php");
                    //converting SLP price to 2 Decimal Format

                    String Stringslpvalue = String.format("%.2f",slpPrice);

                    slpvalue = slpPrice;

                    sslp_showValue.setText(Stringslpvalue);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mQueue.add(requestSlp);
    }

    private void jsonParse(){

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject databaseData = response.getJSONObject("databaseData");

                            String roninAddress = databaseData.getString("roninAddress");

                            //initializing variables for API
                            int noData = 0;
                            double slpDailyYesterday = 0;
                            double slpTotalYesterday = 0;
                            if (databaseData.getBoolean("included")==true){
                                noData = 1;
                                slpDailyYesterday = databaseData.getInt("slpDailyYesterday");
                                if(databaseData.has("slpTotalYesterday") && databaseData.isNull("slpTotalYesterday")){
                                    slpTotalYesterday=0;
                                }else{

                                    slpTotalYesterday = databaseData.getInt("slpTotalYesterday");
                                }

                            }

                            //getting data from API
                            JSONObject slpData = response.getJSONObject("slpData");

                            int gameSlp = slpData.getInt("gameSlp");
                            int totalSLP = slpData.getInt("totalSlp");

                            float getPercentM = manager / 100;
                            float getPercentS = scholar / 100;
                            manager_cut = getPercentM * gameSlp;
                            scholar_cut = getPercentS * gameSlp;
                            double totalslpPesos = Math.round(gameSlp * slpvalue);
                            double managerslpPesos = Math.round(manager_cut * slpvalue);
                            double scholarslpPesos = Math.round(scholar_cut * slpvalue);

                            JSONObject leaderboardData = response.getJSONObject("leaderboardData");

                            int elo = leaderboardData.getInt("elo");
                            accName = leaderboardData.getString("name");

                            if(noData!=0) {
                                double totaltoday = totalSLP - slpTotalYesterday;

                                stodaySlp_showText.setText(String.valueOf(totaltoday));
                                syesterdaySlp_showText.setText(String.valueOf(slpDailyYesterday));
                            }
                            else{
                                stodaySlp_showText.setText("0");
                                syesterdaySlp_showText.setText(String.valueOf(slpDailyYesterday));
                            }

                            sname_showText.setText(accName);
                            saddress_showText.setText(roninAddress);
                            stotalSlp_showText.setText(String.valueOf(gameSlp ));
                            sscholarSlp_showText.setText(String.valueOf( scholar_cut));
                            smanagerSlp_showText.setText(String.valueOf(manager_cut));
                            sslpValue_Total.setText("₱");
                            sslpValue_Total.append(String.valueOf(totalslpPesos));
                            sslpValue_Manager.setText("₱");
                            sslpValue_Manager.append(String.valueOf(managerslpPesos));
                            sslpValue_Scholar.setText("₱");
                            sslpValue_Scholar.append(String.valueOf(scholarslpPesos));
                            smmr_showText.setText(String.valueOf(elo ));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mQueue.add(request);
    }

    private void axieTutorial()
    {
        axieTutorial axieTutorial = new axieTutorial();
        axieTutorial.show(getSupportFragmentManager(), "home tutorial");
    }

    private void saveRoninAddress()
    {
        SharedPreferences.Editor editor = sharedPrefAxieTutorial.edit();
        editor.putString("savedAddress", sinput_Address.getText().toString());
        editor.commit();
    }

    private void applySavedAddress()
    {
        loadAddress = sharedPrefAxieTutorial.getString("savedAddress", "");
        sinput_Address.setText(loadAddress);
    }


}
