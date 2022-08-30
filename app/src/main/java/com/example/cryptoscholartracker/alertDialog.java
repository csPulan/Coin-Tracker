package com.example.cryptoscholartracker;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import net.yslibrary.android.keyboardvisibilityevent.util.UIUtil;

public class alertDialog extends AppCompatDialogFragment {
    EditText oldPassword;
    EditText newPassword;
    EditText confirmPassword;
    Context context;

    String stringOld=null;
    String stringNew=null;
    String stringConfirm=null;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.reset_password, null);
        View view1 = inflater.inflate(R.layout.activity_account, null);

        oldPassword = view.findViewById(R.id.old_password);
        newPassword = view.findViewById(R.id.new_password);
        confirmPassword = view.findViewById(R.id.confirm_password);
        context = getActivity().getApplicationContext();

        oldPassword.requestFocus();
        InputMethodManager imgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imgr.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);


        builder.setView(view)
                .setTitle("Reset Password")
                .setMessage("New password must be at least 6 Characters.")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        imgr.hideSoftInputFromWindow(oldPassword.getWindowToken(), 0);
                    }
                })

                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        stringOld = oldPassword.getText().toString();
                        stringNew = newPassword.getText().toString();
                        stringConfirm = confirmPassword.getText().toString();

                        if (stringConfirm.equals(stringNew)) {

                            imgr.hideSoftInputFromWindow(oldPassword.getWindowToken(), 0);
                            Toast.makeText(getContext(), "Password Changed", Toast.LENGTH_SHORT).show();
                            accountActivity.resetPassword(stringConfirm);
                            getActivity().finishAffinity();
                            startActivity(new Intent(getActivity(), Login.class));



                        } else {
                            imgr.hideSoftInputFromWindow(oldPassword.getWindowToken(), 0);
                            Toast.makeText(getContext(), "New password is incorrect. Please try again", Toast.LENGTH_LONG).show();

                        }


                    }
                });


        Dialog d = builder.create();


        return d;

    }
}




