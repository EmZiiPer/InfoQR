package com.devwings.info_qr;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintHelper;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Manual_Update extends AppCompatActivity {
    public static final String EQUIP_ID = "id";
    public static final String EQUIP_NAME = "name";
    public static final String DATE = "date";
    TextView mQuoteTextView;

    private DocumentReference mDocRef = FirebaseFirestore.getInstance().document("Equipment/information");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_update);
        mQuoteTextView = (TextView) findViewById(R.id.textViewInspiring);
    }

    public void fetchQuote(final View view){

        mDocRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {

                    String equip_id = documentSnapshot.getString(EQUIP_ID);
                    String equip_name = documentSnapshot.getString(EQUIP_NAME);
                    String equip_date = documentSnapshot.getString(DATE);
                    mQuoteTextView.setText("\"ID:- " +System.getProperty("line.separator")+ equip_id + System.getProperty("line.separator")+"\" Equip Name:- "+System.getProperty("line.separator") + equip_name + System.getProperty("line.separator")+"\" Last Service Date:- "+System.getProperty("line.separator") + equip_date);
                    Toast.makeText(Manual_Update.this, "Data Fetch Successful!", Toast.LENGTH_SHORT).show();
                    Snackbar snackbar = Snackbar
                            .make(view, "Establishing Secure Connection", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }
            }
        });

    }

    public void saveQuote (final View view) {
        EditText equipID_View = (EditText) findViewById(R.id.editEquipID);
        EditText equipNAMEView = (EditText) findViewById(R.id.editEquipName);
        EditText equipDATEView = (EditText) findViewById(R.id.editEquipService);
        String quoteText = equipID_View.getText().toString();
        String authorText = equipNAMEView.getText().toString();
        String detailText = equipDATEView.getText().toString();

        if (quoteText.isEmpty() || authorText.isEmpty()){
            return;
        }
        Map<String, Object> dataToSave = new HashMap<String, Object>();
        dataToSave.put(EQUIP_ID, quoteText);
        dataToSave.put(EQUIP_NAME, authorText);
        dataToSave.put(DATE, detailText);
        mDocRef.set(dataToSave).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(Manual_Update.this, "Successfully Added to Database", Toast.LENGTH_SHORT).show();
                Snackbar snackbar = Snackbar
                        .make(view, "Establishing Secure Connection", Snackbar.LENGTH_SHORT);
                snackbar.show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Manual_Update.this, "Failed to Add Data To Database", Toast.LENGTH_SHORT).show();
            }
        });
    }
}



/*
 _____           __          ___
 |  __ \          \ \        / (_)
 | |  | | _____   _\ \  /\  / / _ _ __   __ _ ___
 | |  | |/ _ \ \ / /\ \/  \/ / | | '_ \ / _` / __|
 | |__| |  __/\ V /  \  /\  /  | | | | | (_| \__ \
 |_____/ \___| \_/    \/  \/   |_|_| |_|\__, |___/
                                         __/ |
                                        |___/
 */