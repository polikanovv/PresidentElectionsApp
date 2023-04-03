package com.example.presidentelectionsapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.Nullable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class ImageDownloadAsyncTask extends AsyncTask<String, Void, ArrayList<Candidate>> {

    private String imageUrl;
    private ArrayList<Candidate> candidateList;
    int position;

    public ImageDownloadAsyncTask(ArrayList<Candidate> candidateList, int position) {
        this.candidateList = candidateList;
        this.position = position;
    }

    @Override
    protected ArrayList<Candidate> doInBackground(String... params) {
        imageUrl = params[0];
        Bitmap bitmap = LoadImage(imageUrl);
        saveImgToCacheWithPath(bitmap,candidateList.get(position).getImage());
        return candidateList;
    }

    @Override
    protected void onPostExecute(ArrayList<Candidate> candidateList) {
    }

    private Bitmap LoadImage(String URL) {
        Bitmap bitmap = null;
        InputStream in = null;
        try {
            in = OpenHttpConnection(URL);
            bitmap = BitmapFactory.decodeStream(in);
            in.close();
        } catch (IOException e1) {
        }
        return bitmap;
    }

    private InputStream OpenHttpConnection(String strURL) throws IOException {
        InputStream inputStream = null;
        URL url = new URL(strURL);
        URLConnection conn = url.openConnection();

        try {
            HttpURLConnection httpConn = (HttpURLConnection) conn;
            httpConn.setRequestMethod("GET");
            httpConn.connect();

            if (httpConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = httpConn.getInputStream();
            }
        } catch (Exception ex) {

        }
        return inputStream;
    }
    public String saveImgToCacheWithPath(Bitmap bitmap, @Nullable String name) {
        File cachePath = null;
        try {
            cachePath = new File("/data/user/0/com.example.presidentelectionsapp/cache/", "imagesElections");
            cachePath.mkdirs();
            FileOutputStream stream = new FileOutputStream(cachePath + "/" + name);
            bitmap.compress(
                    Bitmap.CompressFormat.PNG,
                    100,
                    stream);
            stream.close();
            Log.d("MyLog",cachePath.getAbsolutePath() + "/" + name);
            return cachePath.getAbsolutePath() + "/" + name;
        } catch (IOException e) {
            Log.e("MyLog", "saveImgToCache error: " + bitmap, e);
        }
        return null;
    }
}
