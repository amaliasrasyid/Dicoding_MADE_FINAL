package amalia.dev.dicodingmade.repository;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiRepository {
    public  static Retrofit retrofit;
    private static final String BASE_URL = "https://api.themoviedb.org/3/";

   public static Retrofit getInstance(){
       if(retrofit == null){
           retrofit = new Retrofit.Builder()
                   .baseUrl(BASE_URL)
                   .addConverterFactory(GsonConverterFactory.create())
                   .build();
       }
       return retrofit;
   }
}
