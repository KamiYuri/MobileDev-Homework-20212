package com.example.mobiledevhomework20212;

import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Pair<Integer, String>> fileInfo;

    public CustomAdapter(ArrayList<Pair<Integer, String>> filesName){
        this.fileInfo = filesName;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View listViewItem = inflater.inflate(R.layout.listview_items, parent, false);

        return new ViewHolder(listViewItem);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder)holder).fileIcon.setImageResource(fileInfo.get(position).first);
        ((ViewHolder)holder).fileName.setText(fileInfo.get(position).second);
    }

    @Override
    public int getItemCount() {
        return fileInfo.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView fileName;
        public ImageView fileIcon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.fileIcon = itemView.findViewById(R.id.fileIcon);
            this.fileName = itemView.findViewById(R.id.fileName);
        }
    }
}
