package com.example.cryptoscholartracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.text.DecimalFormat;

public class InfoActivity extends AppCompatActivity {

    private static DecimalFormat df2 = new DecimalFormat("#.####");
    TextView name, symbol, price, slug, volume24h,volume_change_24h, market_cap,  change1h, change24h, change7d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        //initializing variables to replace data from api
        getSupportActionBar().hide();

        name = findViewById(R.id.coinName);
        symbol = findViewById(R.id.symbol);
        price = findViewById(R.id.coinPrice);
        slug = findViewById(R.id.slug);
        volume24h = findViewById(R.id.volume24h);
        volume_change_24h = findViewById(R.id.volume_change_24h);
        market_cap = findViewById(R.id.market_cap);
        change1h = findViewById(R.id.change1h);
        change24h = findViewById(R.id.change24h);
        change7d = findViewById(R.id.change7d);



        //Getting data from Array List and replacing them
        Intent intent = getIntent();

        if (intent.getExtras()!=null){

            CurrencyRVModal courseModal = (CurrencyRVModal) intent.getSerializableExtra("data");

            name.setText(courseModal.getName());
            price.setText("$");
            price.append(df2.format(courseModal.getPrice()));
            symbol.setText(courseModal.getSymbol());
            slug.setText(courseModal.getSlug());
            volume24h.setText(df2.format(courseModal.getVolume24h()));
            volume24h.append("%");
            market_cap.setText("$");
            market_cap.append(df2.format(courseModal.getMarket_cap()));
            volume_change_24h.setText("$");
            volume_change_24h.append(df2.format(courseModal.getVolume_change_24h()));
            change1h.setText(df2.format(courseModal.getPercent()));
            change1h.append("%");
            change7d.setText(df2.format(courseModal.getPercent_7d()));
            change7d.append("%");
            change24h.setText(df2.format(courseModal.getPercent_24h()));
            change24h.append("%");

        }
    }
}