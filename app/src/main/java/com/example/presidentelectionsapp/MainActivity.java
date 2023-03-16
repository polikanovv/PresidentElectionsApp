package com.example.presidentelectionsapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    ArrayList<Candidate> candidatesList;
    TextView totalVotes;
    int preSelectedIndex = -1;
    int beforeSelectedIndex = -1;
    String PATH_TO_FILES = "/data/user/0/com.example.presidentelectionsapp/cache/imagesElections/";
    String URL = "https://adlibtech.ru/elections/api/getcandidates.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        totalVotes = findViewById(R.id.textAllVotes);
        ListView listView = findViewById(R.id.listView);
        candidatesList = new ArrayList<>();
        MyAsyncTask myAsyncTask = new MyAsyncTask(candidatesList,totalVotes);
        try {
            myAsyncTask.execute(URL).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        final MyAdapter adapter = new MyAdapter(MainActivity.this,candidatesList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent candidateIntent = new Intent(MainActivity.this, CandidateInfoActivity.class);
                candidateIntent.putExtra("descriptions",candidatesList.get(position).getDescriptions());
                candidateIntent.putExtra("party",candidatesList.get(position).getParty());
                candidateIntent.putExtra("firstname",candidatesList.get(position).getFirstname());
                candidateIntent.putExtra("secondname",candidatesList.get(position).getSecondname());
                candidateIntent.putExtra("imagepath",PATH_TO_FILES + candidatesList.get(position).getImage());
                startActivity(candidateIntent);
            }
        });
    }
}