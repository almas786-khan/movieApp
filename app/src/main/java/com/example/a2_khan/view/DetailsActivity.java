package com.example.a2_khan.view;


import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.a2_khan.databinding.ActivityDetailsBinding;
import com.example.a2_khan.viewmodel.MovieViewModel;

public class DetailsActivity extends AppCompatActivity {
    ActivityDetailsBinding binding;
    MovieViewModel movieViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        String movieID = getIntent().getStringExtra("movieID");
        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        movieViewModel = new ViewModelProvider(this).get(MovieViewModel.class);
        movieViewModel.fetchMovieDetails(movieID);
        movieViewModel.getMovieDetailsData().observe(this,movie->{
            if (movie != null){
                binding.textViewMovieTitle.setText(movie.getTitle());
                binding.textViewYear.setText(movie.getYear());
                binding.textViewPlot.setText(movie.getPlot());
                Glide.with(this)
                        .load(movie.getPosterUrl())
                        .into(binding.imageviewMoviePoster);
            }
        });
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        binding = null;
    }
}