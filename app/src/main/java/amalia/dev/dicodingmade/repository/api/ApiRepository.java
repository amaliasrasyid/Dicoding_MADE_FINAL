package amalia.dev.dicodingmade.repository.api;

import amalia.dev.dicodingmade.BuildConfig;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiRepository {
    private static Retrofit retrofit;

   public static Retrofit getInstance(){
       if(retrofit == null){
           retrofit = new Retrofit.Builder()
                   .baseUrl(BuildConfig.BASE_URL)
                   .addConverterFactory(GsonConverterFactory.create())
                   .build();
       }
       return retrofit;
   }
}
