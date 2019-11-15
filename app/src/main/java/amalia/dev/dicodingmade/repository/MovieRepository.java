//package amalia.dev.dicodingmade.repository;
//
//import android.util.Log;
//
//import java.util.ArrayList;
//
//import amalia.dev.dicodingmade.model.Movie;
//import amalia.dev.dicodingmade.model.MovieResult;
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//import retrofit2.Retrofit;
//import retrofit2.converter.gson.GsonConverterFactory;
//
//public class MovieRepository {
//
//    private ApiInterface apiInterface;
//    private static MovieRepository movieRepository;
//    private String BASE_URL;
//    private String LANGUAGE;
//
//    public MovieRepository(ApiInterface apiInterface,String BASE_URL,String LANGUAGE) {
//        this.apiInterface = apiInterface;
//        this.BASE_URL = BASE_URL;
//        this.LANGUAGE = LANGUAGE;
//    }
//
//    public  MovieRepository getInstance(){
//        if(movieRepository == null){
//            //retrofit instance
//            Retrofit retrofit = new Retrofit.Builder()
//                    .baseUrl(BASE_URL)
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .build();
//            movieRepository = new MovieRepository(retrofit.create(ApiInterface.class));
//        }
//        return movieRepository
//    }
//
//    public void getMovies(){
//        // Melakukan proses asynchronous untuk mendapatkan data pengguna.
//        //connect & get data
//
//
//        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
//        Call<MovieResult> call = apiInterface.getListMovies(API_KEY,LANGUAGE);
//        call.enqueue(new Callback<MovieResult>() {
//            @Override
//            public void onResponse(Call<MovieResult> call, Response<MovieResult> response) {
//                if(response.isSuccessful()){
//                    //save response into objek MovieResult
//                    MovieResult movieResult = response.body();
//
//                    // moving List data(data dr server berupa list) into Arraylist, before saving in MutableLiveData
//                    listData = new ArrayList<Movie>();
//                    for (int i=0;i<movieResult.getMoviesResults().size();i++){
//                        listData.add(movieResult.getMoviesResults().get(i));
//                    }
//                    //inserting to MutableLiveData
//                    listMovies.postValue(listData);
//                }else{
//                    Log.e("FAILURE","Response is NULL");
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<MovieResult> call, Throwable t) {
//                Log.e("FAILED connection ",t.toString());
//            }
//        });
//    }
//
//}
