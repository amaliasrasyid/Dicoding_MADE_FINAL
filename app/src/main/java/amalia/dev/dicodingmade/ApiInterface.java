package amalia.dev.dicodingmade;

import amalia.dev.dicodingmade.model.Movie;
import amalia.dev.dicodingmade.model.MovieResult;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface  ApiInterface {



//    @GET("tv/{tv_id}")
//    Call<ArrayList<TvShow>> getListTvShow(
//            @Path(("tv_id")) int tv_id
//    );

    @GET("movie/{movie_id}/lists")
    Call<MovieResult> getListMovies(
            @Path("movie_id") int movie_id,
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") int page

    );
}
