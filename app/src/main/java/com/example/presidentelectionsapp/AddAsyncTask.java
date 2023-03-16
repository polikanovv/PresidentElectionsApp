package com.example.presidentelectionsapp;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AddAsyncTask extends AsyncTask<Void,Void,Void> {
    ArrayList<Candidate> candidatesList;
    int preSelectedIndex;
    int beforeSelectedIndex;
    String URL = "https://adlibtech.ru/elections/api/addvote.php";

    AddAsyncTask(ArrayList<Candidate> candidatesList,int preSelectedIndex,int beforeSelectedIndex) {
        this.candidatesList = candidatesList;
        this.preSelectedIndex = preSelectedIndex;
        this.beforeSelectedIndex = beforeSelectedIndex;
    }
    @Override
    protected Void doInBackground(Void... voids) {
        markCandidate(preSelectedIndex,beforeSelectedIndex); return null;
    }
    public void markCandidate(int preSelectedIndex,int beforeSelectedIndex) {
        String idBefore;
        if (beforeSelectedIndex == -1) {
            idBefore = "999";
        } else {
            idBefore = candidatesList.get(beforeSelectedIndex).getId();
        }
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
            .add("device_id", "1")
            .add("device_name", "huawei-eml")
            .add("candidate_id", candidatesList.get(preSelectedIndex).getId())
            .add("last_id", idBefore)
            .build();
        Request request = new Request.Builder()
            .url(URL)
            .post(formBody)
            .build();
        Response responses = null;
        try {
            responses = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String jsonData = null;
        try {
            jsonData = responses.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
