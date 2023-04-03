package com.example.presidentelectionsapp;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class MyAdapter extends BaseAdapter {
    Context context;
    private ArrayList<Candidate> candidateList;
    String PATH_TO_FILES = "/data/user/0/com.example.presidentelectionsapp/cache/imagesElections/";
    int beforeSelectedIndex = -1;
    int preSelectedIndex = -1;

    public MyAdapter(Context context, ArrayList<Candidate> candidateList) {
        this.context = context;
        this.candidateList = candidateList;
    }
    @Override
    public int getCount() {
        return candidateList.size();
    }
    @Override
    public Object getItem(int position) {
        return candidateList.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        StringBuilder unknownDataString = new StringBuilder();

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.row,parent,false);
            holder = new ViewHolder();
            holder.textName = (TextView) convertView.findViewById(R.id.textName);
            holder.textSurname = (TextView) convertView.findViewById(R.id.textSurname);
            holder.textVotes = (TextView) convertView.findViewById(R.id.textVotes);
            holder.textPercent = (TextView) convertView.findViewById(R.id.textPercent);
            holder.candidateImageButton = convertView.findViewById(R.id.candidateImageButton);
            holder.checkBox = convertView.findViewById(R.id.checkBox);
            holder.textUnknown = convertView.findViewById(R.id.unknownData);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Candidate candidate = candidateList.get(position);
                    candidate.setChecked(true);
                    candidate.setVotes(String.valueOf((Integer.parseInt(candidateList.get(position).getVotes()) + 1)));
                    candidateList.set(position,candidate);
                    if(preSelectedIndex > -1){
                        Candidate nowRecord = candidateList.get(preSelectedIndex);
                        nowRecord.setChecked(false);
                        nowRecord.setVotes(String.valueOf((Integer.parseInt(candidateList.get(preSelectedIndex).getVotes()) - 1)));
                        candidateList.set(preSelectedIndex,nowRecord);
                    }
                    beforeSelectedIndex = preSelectedIndex;
                    //Candidate beforeRecord = candidatesList.get(beforeSelectedIndex);
                    preSelectedIndex = position;
                    Log.d("MyLog","NOW" + (preSelectedIndex));
                    Log.d("MyLog","BEFORE" + (beforeSelectedIndex));
                    updateRecords(candidateList);
                    AddAsyncTask addAsyncTask = new AddAsyncTask(
                            candidateList,
                            position,
                            beforeSelectedIndex);
                    addAsyncTask.execute();
            }
        });
        Candidate candidate = candidateList.get(position);
        if(candidate.isChecked()){
            holder.checkBox.setChecked(true);
        }
        else {
            holder.checkBox.setChecked(false);
        }
        holder.textName.setText(candidateList.get(position).getFirstname());
        holder.textSurname.setText(candidateList.get(position).getSecondname());
        holder.textVotes.setText("Голосов: " + candidateList.get(position).getVotes());
        holder.textPercent.setText(
                "Процент: " + (int)(Double.parseDouble(candidateList.get(position).getVotes())
                / Double.parseDouble(candidateList.get(position).getTotalVotes()) * 100) + " %");
        File file = new File(PATH_TO_FILES + candidateList.get(position).getImage());
        for(Map.Entry<String, String> pair: candidateList.get(position).getUnknownData().entrySet()){
            unknownDataString.append(pair.getKey()).append(" ").append(pair.getValue()).append("\n");
        }
        holder.textUnknown.setText(unknownDataString);
        if(file.exists()){
            Bitmap savedBitmap = BitmapFactory.decodeFile(PATH_TO_FILES + candidateList.get(position).getImage());
            holder.candidateImageButton.setImageBitmap(savedBitmap);
            Log.d("MyLog","YET DOWNLOADED ONLY FROM DEVICE MEMORY" + PATH_TO_FILES + candidateList.get(position).getImage());
        }
        else {
            ImageDownloadAsyncTask imageDownloadAsyncTask = new ImageDownloadAsyncTask(candidateList, position);
            imageDownloadAsyncTask.execute("https://adlibtech.ru/elections/upload_images/" + candidateList.get(position).getImage());
            Log.d("MyLog","NEW IMAGE DOWNLOADED");
        }
        return convertView;
    }
    public void updateRecords(ArrayList<Candidate> candidateList){
        this.candidateList = candidateList;
        notifyDataSetChanged();
    }
    public static final class ViewHolder {
        TextView textName;
        TextView textSurname;
        TextView textVotes;
        TextView textPercent;
        TextView textUnknown;
        ImageButton candidateImageButton;
        CheckBox checkBox;
    }
}