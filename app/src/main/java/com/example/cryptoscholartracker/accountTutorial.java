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

public class accountTutorial extends AppCompatDialogFragment {

    Context context;
    public static boolean checkAccount = false;
    CheckBox skip;
    Boolean skipButton = false;
    Boolean closeButton = false;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        context = getActivity();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.account_tutorial, null);
        skip = view.findViewById(R.id.skip);

        builder.setView(view)
                .setTitle("Account Tutorial")
                .setMessage("In this Activity, You can check all the details about the account. You can remove details or change a detail in your account by clicking the CHANGE PROFILE button. If you want, you can reset your password by just clicking the RESET PASSWORD button. In this activity also is the Logout Button");

        skip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b)
            {
                checkAccount = true;
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
                        if(checkAccount == true || closeButton == true){
                            SharedPreferences.Editor mEditor = accountActivity.sharedPrefAccountTutorial.edit();
                            mEditor.putBoolean("tagAccount",checkAccount).commit();
                        }else{
                            closeButton = false;
                        }

                    }
                });


        Dialog d = builder.create();


        return d;

    }
}
