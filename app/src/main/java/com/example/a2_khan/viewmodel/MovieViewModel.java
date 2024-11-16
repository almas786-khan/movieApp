package com.example.a2_khan.viewmodel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.a2_khan.model.MovieModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;


public class MovieViewModel extends ViewModel {

    private OkHttpClient client = new OkHttpClient();
    private final MutableLiveData<List<MovieModel>> movieData = new MutableLiveData<>();
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private final MutableLiveData<MovieModel> movieDetailsData = new MutableLiveData<>();


    public MovieViewModel() {
        Log.i("tag", "Constructor");
    }

    public LiveData<List<MovieModel>> getMovieData() {
        return movieData;
    }
    public LiveData<String> getErrorMessage(){
        return  errorMessage;
    }
    public LiveData<MovieModel> getMovieDetailsData(){
        return movieDetailsData;
    }

    public void Search(String Query) {

        String urlString =  "https://www.omdbapi.com/?apikey=9e23f5a5&s=" + Query;

        ApiClient.get(urlString, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e("API Error", e.getMessage());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                assert  response.body() != null;
                String responseData = response.body().string();

                try {
                    JSONObject jsonObject = new JSONObject(responseData);
                    String responseStatus = jsonObject.optString("Response"); // if Response comes false from the api call
                    if ("False".equals(responseStatus)){
                            String errormessage = jsonObject.optString("Error"); // to check if the error is too many results then display error message
                        if ("Too many results.".equals(errormessage)){
                            Log.e("API Error", "Too many results found.");
                            movieData.postValue(null);
                            errorMessage.postValue("Too many results found.");
                        }else {
                            errorMessage.postValue("No movies found");
                        }
                    }
                    //if response is not false
                    else{
                        JSONArray searchArray = jsonObject.optJSONArray("Search");
                        if (searchArray != null) {
                            List<MovieModel> moviesList = new ArrayList<>();

                            for (int i = 0; i < searchArray.length(); i++) {
                                JSONObject movieObject = searchArray.getJSONObject(i);
                                String title = movieObject.getString("Title");
                                String year = movieObject.getString("Year");
                                String poster = movieObject.getString("Poster");
                                String movieID = movieObject.getString("imdbID");
                                MovieModel movieModel = new MovieModel(title, year, poster,movieID);
                                moviesList.add(movieModel);
                                fetchImdbRating(movieID,movieModel);
                            }
                            //post the list of movies to livedata
                            movieData.postValue(moviesList);
                        }
                        else {
                            errorMessage.postValue("No movies found");
                            System.out.println("no movies found");
                        }
                    }
                }
                catch (JSONException e){
                    e.printStackTrace();
                }
            }
        });
    }

    //to get movie details using imdbID
    public void fetchMovieDetails(String imdbID) {
        String urlString = "https://www.omdbapi.com/?apikey=9e23f5a5&i=" + imdbID +"&plot=short";
        ApiClient.get(urlString, new Callback() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String responseData = response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(responseData);
                    String responseStatus = jsonObject.optString("Response");
                    if ("True".equals(responseStatus)){
                        String title = jsonObject.getString("Title");
                        String year = jsonObject.getString("Year");
                        String poster = jsonObject.getString("Poster");
                        String movieID = jsonObject.getString("imdbID");
                        String plot = jsonObject.getString("Plot");
                        MovieModel movieModel = new MovieModel(title, year, poster,plot, movieID);
                        movieDetailsData.postValue(movieModel);

                    }else{
                        errorMessage.postValue("Error fetching movie details");
                    }
                }
                catch (JSONException e){
                    e.printStackTrace();
                    errorMessage.postValue("Error in parsing movie details.");
                }
            }
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e("API Error", e.getMessage());
            }
        });
    }
    private void fetchImdbRating(String imdbID, MovieModel movieModel){
        String urlString = "https://www.omdbapi.com/?apikey=9e23f5a5&i=" + imdbID +"&plot=short";
        ApiClient.get(urlString, new Callback() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String responseData = response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(responseData);
                    String responseStatus = jsonObject.optString("Response");
                    if ("True".equals(responseStatus)){
                        String imdbRating = jsonObject.getString("imdbRating");
                        movieModel.setImdbRating(imdbRating);
                        List<MovieModel> updateMovies = movieData.getValue();
                        if (updateMovies != null){
                            movieData.postValue(updateMovies);
                        }
                    }else{
                        errorMessage.postValue("Error fetching movie details");
                    }
                }
                catch (JSONException e){
                    e.printStackTrace();
                    errorMessage.postValue("Error in parsing rating details.");
                }
            }

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e("API Error", e.getMessage());

            }
        });
    }

 }
