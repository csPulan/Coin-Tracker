package com.example.cryptoscholartracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class GameAdapter extends ArrayAdapter<GameItem> {

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position,convertView,parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position,convertView,parent);
    }

    public GameAdapter(Context context, ArrayList<GameItem> gameList){
        super(context, 0,gameList);

    }

    private View initView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.country_spinner_row, parent, false);
        }
        ImageView imageViewGame = convertView.findViewById(R.id.image_view_token);
        TextView textViewName = convertView.findViewById(R.id.text_view_name);

        GameItem currentItem = getItem(position);

        if (currentItem!=null) {
            imageViewGame.setImageResource(currentItem.getGameImage());
            textViewName.setText(currentItem.getGameName());
        }
        return convertView;
    }
}