package com.example.presidentelectionsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class CandidateInfoActivity extends AppCompatActivity {
    TextView firstname;
    TextView secondname;
    TextView descriptions;
    TextView party;
    Button buttonBack;
    ImageView imageCandidate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate_info);
        firstname = findViewById(R.id.textFirstName);
        secondname = findViewById(R.id.textSecondName);
        descriptions = findViewById(R.id.textDescriptions);
        party = findViewById(R.id.textParty);
        imageCandidate = findViewById(R.id.imageCandidate);
        buttonBack = findViewById(R.id.buttonBack);

        Bundle extraData = getIntent().getExtras();
        Bitmap savedBitmap = BitmapFactory.decodeFile(extraData.getString("imagepath"));
        imageCandidate.setImageBitmap(savedBitmap);
        firstname.setText(extraData.getString("firstname"));
        secondname.setText(extraData.getString("secondname"));
        descriptions.setText(extraData.getString("descriptions"));
        party.setText("Партийность: " + extraData.getString("party"));
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(CandidateInfoActivity.this, MainActivity.class);
                CandidateInfoActivity.this.startActivity(mainIntent);
                CandidateInfoActivity.this.finish();
            }
        });
    }
}