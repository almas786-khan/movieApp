package com.example.a2_khan.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.LinearLayout;
import com.example.a2_khan.MyAdapter;
import com.example.a2_khan.databinding.ActivityMainBinding;
import com.example.a2_khan.model.MovieModel;
import com.example.a2_khan.viewmodel.MovieViewModel;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttp;
import okhttp3.OkHttpClient;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    MyAdapter myAdapter;
    List<MovieModel> moviesModels = new ArrayList<>();
    MovieViewModel viewModel;
    private OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //recycler view
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.recyclerView.setLayoutManager(layoutManager);

        //initialize adapter
        myAdapter = new MyAdapter(this, new ArrayList<>());
        binding.recyclerView.setAdapter(myAdapter);

        viewModel = new ViewModelProvider(this).get(MovieViewModel.class);


        viewModel.getMovieData().observe(this, movies ->{
            if (movies != null && !movies.isEmpty()){
                binding.recyclerView.setVisibility(View.VISIBLE);
                binding.noDataTextView.setVisibility(View.GONE);
                myAdapter.updateMovies(movies);
            }
            else{
                binding.recyclerView.setVisibility(View.GONE);
                binding.noDataTextView.setVisibility(View.VISIBLE);
                binding.noDataTextView.setText("No movies found");
            }
        });
        viewModel.getErrorMessage().observe(this,errorMessage->{
            if (errorMessage != null){
                binding.recyclerView.setVisibility(View.GONE);
                binding.noDataTextView.setVisibility(View.VISIBLE);
                binding.noDataTextView.setText(errorMessage);
            }
        });


    binding.button.setOnClickListener(new View.OnClickListener(){
       public void onClick(View w){
          String searchField = binding.editTextText.getText().toString();
           if(searchField.isEmpty()){
              binding.editTextText.setError("Please type something to search");
           }
           else {
           viewModel.Search(searchField);}
        }
    });
    }

}