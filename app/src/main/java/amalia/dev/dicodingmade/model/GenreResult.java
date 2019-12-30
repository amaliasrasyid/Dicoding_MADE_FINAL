package amalia.dev.dicodingmade.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GenreResult {

    @SerializedName("genres")
    @Expose
    private List<GenreRealmObject> genres = null;

    public List<GenreRealmObject> getGenres() {
        return genres;
    }

    public void setGenres(List<GenreRealmObject> genres) {
        this.genres = genres;
    }
}
