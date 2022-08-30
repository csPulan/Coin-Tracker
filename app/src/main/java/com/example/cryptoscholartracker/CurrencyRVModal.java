package com.example.cryptoscholartracker;

import java.io.Serializable;

public class CurrencyRVModal implements Serializable {
    // variable for currency name,
    // currency symbol and price.
    private String name;
    private String symbol;
    private String slug;
    private double price;
    private double volume_change_24h;
    private double volume24h;
    private double market_cap;
    private double percent_1h;
    private double percent_24h;
    private double percent_7d;


    public CurrencyRVModal(String name, String symbol, String slug, double price, double volume24h, double market_cap, double volume_change_24h, double percent_1h, double percent_24h, double percent_7d)
    {
        this.name = name;
        this.symbol = symbol;
        this.slug = slug;
        this.price = price;
        this.volume24h = volume24h;
        this.market_cap = market_cap;
        this.volume_change_24h = volume_change_24h;
        this.percent_1h = percent_1h;
        this.percent_24h = percent_24h;
        this.percent_7d = percent_7d;
    }




    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getSymbol()
    {
        return symbol;
    }

    public void setSymbol(String symbol)
    {
        this.symbol = symbol;
    }

    public String getSlug()
    {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public double getPrice()
    {
        return price;
    }

    public void setPrice(double price)
    {
        this.price = price;
    }

    public double getPercent()
    {
        return percent_1h;
    }

    public void setPercent(double percent)
    {
        this.percent_1h = percent;
    }

    public double getPercent_24h()
    {
        return percent_24h;
    }

    public void setPercent_24h(double percent_24h)
    {
        this.percent_24h = percent_24h;
    }

    public double getPercent_7d()
    {
        return percent_7d;
    }

    public void setPercent_7d(double percent_7d)
    {
        this.percent_7d = percent_7d;
    }


    public double getVolume24h() {
        return volume24h;
    }

    public void setVolume24h(double volume24h) {
        this.volume24h = volume24h;
    }

    public double getMarket_cap() {
        return market_cap;
    }

    public void setMarket_cap(double market_cap) {
        this.market_cap = market_cap;
    }

    public double getVolume_change_24h() {
        return volume_change_24h;
    }

    public void setVolume_change_24h(double circulating_supply) {
        this.volume_change_24h = circulating_supply;
    }
}