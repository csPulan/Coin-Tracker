package com.example.cryptoscholartracker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class axiewithHistory extends AppCompatActivity {

    private RecyclerView recyclerView;
    private historyRVAdapter adapter;

    private ArrayList<historyRVModal> historyList;

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog.Builder builderAxieDetails;
    private AlertDialog dialog, AxieDetails;
    private EditText seditTextScholar, seditTextManager, roninAddress;
    private Button sokButton;
    private Button scancelButton;
    private Button closeButton;
    private Button srefresh_Button;
    private Button clearHistory;
    private Button sfind_addressButton;
    private Context context;


    private TextView sslp_showValue;
    private TextView saddress_showText;
    private TextView stotalSlp_showText;
    private TextView smanagerSlp_showText;
    private TextView sscholarSlp_showText;
    private TextView sslpValue_Total;
    private TextView sslpValue_Manager;
    private TextView sslpValue_Scholar;
    private TextView syesterdaySlp_showText;
    private TextView stodaySlp_showText;
    private TextView smmr_showText;
    private String savedAddress;
    private String loadAddress;
    int pos;
    Boolean historyClicked = false;

    public static SharedPreferences sharedPrefAxieTutorial = null;
    private RequestQueue mQueue;


    //getting the SLP Price variables
    String urlSLP;
    double slpvalue;
    TextView slpPriceShow;
    String Stringslpvalue;
    //main function
    int num = 0;
    String url;
    String urlName;
    String key;
    String name;
    String sManager = "";
    String sScholar = "";
    public float manager;
    public float scholar;
    float manager_cut;
    float scholar_cut;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_axie_history);getSupportActionBar().hide();

        slpPriceShow = findViewById(R.id.slpPriceShow);
        recyclerView = findViewById(R.id.historyView);
        sfind_addressButton = findViewById(R.id.findAddress);
        clearHistory = findViewById(R.id.clearHistory);
        srefresh_Button = findViewById(R.id.refresh);
        roninAddress = findViewById(R.id.roninAddress);
        historyList = new ArrayList<>();

        mQueue = Volley.newRequestQueue(this);
        sharedPrefAxieTutorial = getSharedPreferences("label",0);
        Boolean mBool = sharedPrefAxieTutorial.getBoolean("tagAxie",false);

        historyList = PrefConfig.readListFromPref(this);

        if(historyList == null){
            historyList = new ArrayList<>();
        }
        setAdapter();

        if(mBool ==true){

            axieTutorial.checkAxie = true;

        }else{
            axieTutorial();

        }
        getSLPValue();
        roninAddress.setText(null);
        sfind_addressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getSLPValue();
                closeKeyboard();
                createNewPopupDialog();
            }
        });

        srefresh_Button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                if(sfind_addressButton !=null){

                    refreshPage();
                    closeKeyboard();
                    getSLPValue();
                }

            }
        });

         clearHistory.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                resetPref(view);
                 Intent i = new Intent(axiewithHistory.this, axiewithHistory.class);
                 finish();
                 overridePendingTransition(0, 0);
                 startActivity(i);
                 overridePendingTransition(0, 0);
             }
         });

    }



    private void getSLPValue(){


        urlSLP = "https://api.coingecko.com/api/v3/simple/token_price/ethereum?contract_addresses=0xcc8fa225d80b9c7d42f96e9570156c65d6caaa25&vs_currencies=php";
        JsonObjectRequest requestSlp =  new JsonObjectRequest(Request.Method.GET, urlSLP, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONObject object1 = new JSONObject(response.toString());
                    JSONObject slpDatas = object1.getJSONObject("0xcc8fa225d80b9c7d42f96e9570156c65d6caaa25");
                    //getting price of SLP
                    double slpPrice = slpDatas.getDouble("php");
                    //converting SLP price to 2 Decimal Format

                    Stringslpvalue = String.format("%.2f",slpPrice);

                    slpvalue = slpPrice;

                    slpPriceShow.setText("₱ ");

                    slpPriceShow.append(Stringslpvalue);

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
                historyClicked = false;
                key = roninAddress.getText().toString().trim();
                url = "https://axie-scho-tracker-server.herokuapp.com/api/account/" + key;
                sManager = seditTextManager.getText().toString();
                sScholar = seditTextScholar.getText().toString();
                manager = Integer.parseInt(sManager);
                scholar = Integer.parseInt(sScholar);

                int totalPercent = (int) (manager + scholar);

                if(totalPercent == 100)
                {
                    Toast.makeText(axiewithHistory.this, "Here's the Information", Toast.LENGTH_SHORT).show();
                    closeKeyboard();
                    dialog.dismiss();
                    getSLPValue();
                    createAxiePopup();
                }else if (totalPercent > 100 || totalPercent<100){

                    Toast.makeText(axiewithHistory.this, "Invalid Percentage", Toast.LENGTH_SHORT).show();

                }
                return;
            }
        });

        scancelButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                imgr.hideSoftInputFromWindow(seditTextScholar.getWindowToken(), 0);
                dialog.dismiss();
                roninAddress.setText(null);
            }
        });


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
                                num++;
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

                            if(noData!=0) {
                                double totaltoday = totalSLP - slpTotalYesterday;

                                stodaySlp_showText.setText(String.valueOf(totaltoday));
                                syesterdaySlp_showText.setText(String.valueOf(slpDailyYesterday));
                            }
                            else{
                                stodaySlp_showText.setText("0");
                                syesterdaySlp_showText.setText(String.valueOf(slpDailyYesterday));
                            }
                            saddress_showText.setText(roninAddress);
                            stotalSlp_showText.setText(String.valueOf(gameSlp));
                            sscholarSlp_showText.setText(String.valueOf( scholar_cut));
                            smanagerSlp_showText.setText(String.valueOf(manager_cut));
                            sslpValue_Total.setText("₱");
                            sslpValue_Total.append(String.valueOf(totalslpPesos));
                            sslpValue_Manager.setText("₱");
                            sslpValue_Manager.append(String.valueOf(managerslpPesos));
                            sslpValue_Scholar.setText("₱");
                            sslpValue_Scholar.append(String.valueOf(scholarslpPesos));
                            smmr_showText.setText(String.valueOf(elo ));

                                if(historyClicked ==false){
                                    historyRVModal historyModal = new historyRVModal(String.valueOf(num),roninAddress, manager, scholar);
                                    historyList.add(historyModal);
                                    PrefConfig.writeListInPref(getApplicationContext(),historyList);
                                }






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



    private void createAxiePopup(){
        builderAxieDetails = new AlertDialog.Builder(this);
        final View AxiePopUp = getLayoutInflater().inflate(R.layout.axieaccount_popup, null);

        sslp_showValue = AxiePopUp.findViewById(R.id.slp_showValue);
        saddress_showText = AxiePopUp.findViewById(R.id.address_showText);
        stotalSlp_showText = AxiePopUp.findViewById(R.id.totalSlp_showText);
        smanagerSlp_showText = AxiePopUp.findViewById(R.id.managerSlp_showText);
        sscholarSlp_showText = AxiePopUp.findViewById(R.id.scholarSlp_showText);
        sslpValue_Total = AxiePopUp.findViewById(R.id.slpValue_Total);
        sslpValue_Manager = AxiePopUp.findViewById(R.id.slpValue_Manager);
        sslpValue_Scholar = AxiePopUp.findViewById(R.id.slpValue_Scholar);
        syesterdaySlp_showText = AxiePopUp.findViewById(R.id.yesterdaySlp_showText);
        stodaySlp_showText = AxiePopUp.findViewById(R.id.todaySlp_showText);
        smmr_showText = AxiePopUp.findViewById(R.id.mmr_showText);
        closeButton = AxiePopUp.findViewById(R.id.closeInfo);

        builderAxieDetails.setView(AxiePopUp);
        AxieDetails = builderAxieDetails.create();
        AxieDetails.show();

        getSLPValue();
        sslp_showValue.setText(Stringslpvalue);


        jsonParse();


        closeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                setAdapter();
                AxieDetails.dismiss();
                roninAddress.setText(null);
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
                Toast.makeText(axiewithHistory.this, "Use numbers only", Toast.LENGTH_SHORT).show();
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

    private void refreshPage(){
        setAdapter();
        getSLPValue();
    }

    private void axieTutorial()
    {
        axieTutorial axieTutorial = new axieTutorial();
        axieTutorial.show(getSupportFragmentManager(), "home tutorial");
    }

    private void setAdapter(){
        adapter = new historyRVAdapter(historyList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        adapter.setHistoryRVModalArrayList(historyList);

        adapter.setOnItemClickListener(new historyRVAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                key = historyList.get(position).getRoninAddress();
                manager = historyList.get(position).getManager();
                scholar = historyList.get(position).getScholar();
                historyClicked =true;
                url = "https://axie-scho-tracker-server.herokuapp.com/api/account/" + key;


                createAxiePopup();

                Toast.makeText(getApplicationContext(), key + " CLICKED", Toast.LENGTH_SHORT).show();

            }
        });

    }

    public void resetPref(View View){
        PrefConfig.removeDatafromPref(this);
    }
}