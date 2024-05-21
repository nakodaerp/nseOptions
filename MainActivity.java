package com.mterp.virtual.trading;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    String fbaUserId = "";
    String webSocket="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        if (fbaUserId.equals("")) {
            if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                fbaUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
            }
        }



        startActivity(new Intent(this, FirebasePhoneActivityBef2.class));




    }

    void loadFbdbUrlSett(){
        FirebaseDatabase.getInstance()
                .getReference("appSettings")
                .child("webSocket")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getValue() != null) {
                            webSocket=dataSnapshot.child("webSocket").getValue().toString();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }


                });



    }


}