package com.example.cryptoscholartracker;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDialogFragment;

public class homeTutorial extends AppCompatDialogFragment {

    Context context;
    public static boolean checkHome = false;
    CheckBox skip;
    Boolean skipButton = false;
    Boolean closeButton = false;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        context = getActivity();

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.home_tutorial, null);
        skip = view.findViewById(R.id.skip);

        builder.setView(view)
                .setTitle("Home Tutorial")
                .setMessage("In this Activity, You can check and see the prices and information about Cryptocurrencies. You can click the cryptocurrencies to check more information about them. If you want, you can also search crytocurrency by clicking the magnifying glass button on your upper right of the screen.");

            skip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b)
            {
                checkHome = true;
                Toast.makeText(getContext(), "Do not Show this again", Toast.LENGTH_SHORT).show();


            }
            });
                builder.setNegativeButton("Skip", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        skipButton = true;

                    }
                })

                .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        closeButton= true;
                        if(checkHome == true || closeButton == true){
                            SharedPreferences.Editor mEditor = MainActivity.sharedPrefHomeTutorial.edit();
                            mEditor.putBoolean("tagHome",checkHome).commit();
                        }else{
                            closeButton = false;
                        }

                    }
                });


        Dialog d = builder.create();


        return d;

    }
}