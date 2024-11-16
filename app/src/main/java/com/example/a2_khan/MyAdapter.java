package com.example.a2_khan;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.a2_khan.model.MovieModel;
import com.example.a2_khan.view.DetailsActivity;
import com.example.a2_khan.viewmodel.MovieViewModel;


public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
    Context context;
    List<MovieModel> movieModels;
    MovieViewModel viewModel;

    public MyAdapter(Context context, List<MovieModel> movieModels) {
        this.context = context;
        this.movieModels = movieModels;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_layout, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        MovieModel movieModel = movieModels.get(position);
        holder.title.setText(movieModel.getTitle());
        holder.year.setText(movieModel.getYear());
        String imdbRating = movieModel.getImdbRating();
        if (imdbRating != null && !imdbRating.equals("N/A")) {
            try {
                // IMDb ratings are out of 10 so to scale them to the 5-star divide by 2
                float movierating = Float.parseFloat(imdbRating) / 2;
                holder.ratingBar.setRating(movierating);
            } catch (NumberFormatException e) {
                holder.ratingBar.setRating(0); // Default if parsing fails
            }
        } else {
            holder.ratingBar.setRating(0); // Default for "N/A" or missing ratings
        }

        Glide.with(context)
                .load(movieModel.getPosterUrl())
                .into(holder.poster);

        // onclicklistener for each movie item
        holder.itemView.setOnClickListener(a->{
                        Intent intent = new Intent(context, DetailsActivity.class);
                        intent.putExtra("movieID", movieModel.getMovieId());
                        context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        if(movieModels==null) return 0;
        return movieModels.size();
    }

    // Update the movie list
    public void updateMovies(List<MovieModel> newMoviesList) {
        this.movieModels = newMoviesList;
        notifyDataSetChanged();
    }
}
