package com.example.mobiledevhomework20212;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class EmailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    @NonNull

    private List emails;
    private Context mContext;

    public EmailAdapter(List emails, Context mContext){
        this.emails = emails;
        this.mContext = mContext;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View emailView = inflater.inflate(R.layout.email_item, parent, false);

        return new MyViewHolder(emailView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        EmailModel email = (EmailModel) emails.get(position);
        ((MyViewHolder)holder).emailFrom.setText(email.getEmailFrom());
    }

    @Override
    public int getItemCount() {
        return emails.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView emailFrom;

        public MyViewHolder(View itemView) {
            super(itemView);
            emailFrom = itemView.findViewById(R.id.emailFrom);
        }
    }
}
