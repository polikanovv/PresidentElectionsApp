package com.example.presidentelectionsapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

class MyAsyncTask extends AsyncTask<String, Void, ArrayList<Candidate>> {
    ArrayList<Candidate> candidatesList;
    TextView totalVotes;

    MyAsyncTask(ArrayList<Candidate> candidatesList, TextView totalVotes) {
        this.candidatesList = candidatesList;
        this.totalVotes = totalVotes;
    }
    @Override
    protected ArrayList<Candidate> doInBackground(String... params) {
        String imageUrl = params[0];
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
            .add("device_id", "1")
            .add("device_name", "huawei-eml")
            .build();
        Request request = new Request.Builder()
            .url(imageUrl)
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
            Log.d("MyLog",  jsonData);
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONArray allCandidatesJson = null;
        try {
            allCandidatesJson = new JSONArray(jsonData);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            for (int i = 0; i < allCandidatesJson.length() - 1; i++) {
                candidatesList.add(new Candidate(
                    null,
                null,
                null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                     false));
                JSONObject candidate = allCandidatesJson.getJSONObject(i);
                String candidateId = candidate.getString("id");
                String candidateName = candidate.getString("firstname");
                String candidateSurname = candidate.getString("secondname");
                String candidateParty = candidate.getString("party");
                String candidateDescriptions = candidate.getString("description");
                String candidateWeb = candidate.getString("web");
                String candidateImage = candidate.getString("image");
                String candidateVotes = candidate.getString("votes");
                candidatesList.get(i).setId(candidateId);
                candidatesList.get(i).setFirstname(candidateName);
                candidatesList.get(i).setSecondname(candidateSurname);
                candidatesList.get(i).setParty(candidateParty);
                candidatesList.get(i).setDescriptions(candidateDescriptions);
                candidatesList.get(i).setWeb(candidateWeb);
                candidatesList.get(i).setImage(candidateImage);
                candidatesList.get(i).setVotes(candidateVotes);
            }
            JSONObject totalVotesJSON = allCandidatesJson.getJSONObject(8);
            for (int i = 0; i < allCandidatesJson.length() - 1; i++){
                candidatesList.get(i).setTotalVotes(totalVotesJSON.getString("total"));
            }
            totalVotes.setText(" " + totalVotesJSON.getString("total"));
            Log.d("MyLog",totalVotesJSON.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return candidatesList;
    }
    @Override
    protected void onPostExecute(ArrayList<Candidate> allCandidatesJson) {
    }
}
