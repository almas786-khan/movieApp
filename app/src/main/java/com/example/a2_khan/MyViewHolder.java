package com.example.a2_khan;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder {
    ImageView poster;
    TextView title;
    TextView year;
    RatingBar ratingBar;
    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        poster = itemView.findViewById(R.id.imageview);
        title = itemView.findViewById(R.id.title_txt);
        year = itemView.findViewById(R.id.year_text);
        ratingBar = itemView.findViewById(R.id.ratingBar);

    }
}
