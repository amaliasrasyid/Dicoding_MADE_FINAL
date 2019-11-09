package amalia.dev.dicodingmade;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {
    private final String judul;
    private final String sinopsis;
    private final int poster;
    private final String tglRilis;
    private final double rating;
    private String status = "Released";

    public Movie(String judul, String sinopsis, int poster, String tglRilis, double rating) {
        this.judul = judul;
        this.sinopsis = sinopsis;
        this.poster = poster;
        this.tglRilis = tglRilis;
        this.rating = rating;
    }

    protected Movie(Parcel in) {
        judul = in.readString();
        sinopsis = in.readString();
        poster = in.readInt();
        tglRilis = in.readString();
        rating = in.readDouble();
        status = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(judul);
        dest.writeString(sinopsis);
        dest.writeInt(poster);
        dest.writeString(tglRilis);
        dest.writeDouble(rating);
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

    public String getJudul() {
        return judul;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public int getPoster() {
        return poster;
    }

    public String getTglRilis() {
        return tglRilis;
    }

    public double getRating() {
        return rating;
    }

    public String getStatus() {
        return status;
    }
}
