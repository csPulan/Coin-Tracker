package com.example.cryptoscholartracker;

public class GameItem {
    private String mGameName;
    private int mGameImage;

    public GameItem(String gameName, int gameImage ){
        mGameName = gameName;
        mGameImage = gameImage;
    }

    public String getGameName(){
        return mGameName;
    }

    public int getGameImage(){
        return mGameImage;
    }
}
