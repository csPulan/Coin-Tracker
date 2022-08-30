package com.example.cryptoscholartracker;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDialogFragment;

public class axieTutorial extends AppCompatDialogFragment {

    Context context;
    public static boolean checkAxie = false;
    CheckBox skip;
    Boolean skipButton = false;
    Boolean closeButton = false;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.axie_tutorial, null);
        context = getActivity();
        skip = view.findViewById(R.id.skip);

        builder.setView(view)
                .setTitle("Axie Tutorial")
                .setMessage("In this Activity, You just need to paste a ronin address on the field above and put the scholar and manager's cut. After this process, it will display all what you want to see in an account's progress. You can just paste another ronin address and it will replace the current ronin address' detail.");

        skip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b)
            {
                checkAxie = true;
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

                        closeButton = true;

                        if(checkAxie == true || skipButton == true){
                            SharedPreferences.Editor mEditor = axiewithHistory.sharedPrefAxieTutorial.edit();
                            mEditor.putBoolean("tagAxie",checkAxie).commit();
                        }else{
                            skipButton = false;
                        }

                    }
                });


        Dialog d = builder.create();


        return d;

    }
}
