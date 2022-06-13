package com.example.mobiledevhomework20212;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class EmailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final List emails;

    public EmailAdapter(List emails){
        this.emails = emails;
    }

    @NonNull
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
        ((MyViewHolder)holder).emailTitle.setText(email.getEmailTitle());
        ((MyViewHolder)holder).emailContent.setText(email.getEmailContent());
        ((MyViewHolder)holder).emailAvatar.setText(email.getEmailFrom().substring(0, 1).toUpperCase(Locale.ROOT));

        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm aa");
        ((MyViewHolder)holder).emailTime.setText(dateFormat.format(email.getEmailTime()));

        TextView emailAvatar = ((MyViewHolder)holder).emailAvatar;

        Random rnd = new Random();
        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        GradientDrawable drawable = (GradientDrawable)emailAvatar.getBackground();
        drawable.setColor(color);
    }

    @Override
    public int getItemCount() {
        return emails.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView emailFrom, emailTitle, emailContent, emailAvatar, emailTime;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.emailFrom = itemView.findViewById(R.id.emailFrom);
            this.emailTitle = itemView.findViewById(R.id.emailTitle);
            this.emailContent = itemView.findViewById(R.id.emailContent);
            this.emailAvatar = itemView.findViewById(R.id.emailAvatar);
            this.emailTime = itemView.findViewById(R.id.emailTime);
        }
    }
}
