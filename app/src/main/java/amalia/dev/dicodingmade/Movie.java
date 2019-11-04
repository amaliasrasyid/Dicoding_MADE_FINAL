package amalia.dev.dicodingmade;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie  implements  Parcelable{
    private final String judul;
    private final String sinopsis;
    private final String poster;
    private final String tglRilis;
    private String status = "Released";


    Movie(String judul, String sinopsis, String poster, String tglRilis) {
        this.judul = judul;
        this.sinopsis = sinopsis;
        this.poster = poster;
        this.tglRilis = tglRilis;
    }

    private Movie(Parcel in) {
        judul = in.readString();
        sinopsis = in.readString();
        poster = in.readString();
        tglRilis = in.readString();
        status = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(judul);
        dest.writeString(sinopsis);
        dest.writeString(poster);
        dest.writeString(tglRilis);
        dest.writeString(status);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    String getJudul() {
        return judul;
    }

    String getSinopsis() {
        return sinopsis;
    }

    String getPoster() {
        return poster;
    }

    String getTglRilis() {
        return tglRilis;
    }

    String getStatus() {
        return status;
    }
}
