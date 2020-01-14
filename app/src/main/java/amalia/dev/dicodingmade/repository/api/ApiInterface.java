package amalia.dev.dicodingmade.repository.api;

import amalia.dev.dicodingmade.model.GenreResult;
import amalia.dev.dicodingmade.model.MovieResult;
import amalia.dev.dicodingmade.model.TvShowResult;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface  ApiInterface {



    @GET("discover/tv")
    Call<TvShowResult> getListTvShow(
            @Query("api_key") String apiKey
    );

    @GET("discover/movie")
    Call<MovieResult> getListMovies(
            @Query("api_key") String apiKey
    );
    @GET("genre/movie/list")
    Call<GenreResult> getGenres(
            @Query("api_key") String apiKey
    );

    @GET("discover/movie")
    Call<MovieResult> getListNewReleaseMovies(
            @Query("api_key") String apiKey,
            @Query("primary_release_date.gte") String todayDate,
            @Query("primary_release_date.lte") String todayDate2
    );
}
