package amalia.dev.dicodingmade;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie  implements  Parcelable{
    private String judul;
    private String sinopsis;
    private int poster;
    private double rating;
    private Pemeran[] pemeran;
    private String tglRilis;
    private String status;

    public Movie(){}

    public Movie(String judul, String sinopsis, int poster, double rating, Pemeran[] pemeran, String tglRilis, String status) {
        this.judul = judul;
        this.sinopsis = sinopsis;
        this.poster = poster;
        this.rating = rating;
        this.pemeran = pemeran;
        this.tglRilis = tglRilis;
        this.status = status;
    }

    protected Movie(Parcel in) {
        judul = in.readString();
        sinopsis = in.readString();
        poster = in.readInt();
        rating = in.readDouble();
        tglRilis = in.readString();
        status = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(judul);
        dest.writeString(sinopsis);
        dest.writeInt(poster);
        dest.writeDouble(rating);
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

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public int getPoster() {
        return poster;
    }

    public void setPoster(int poster) {
        this.poster = poster;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public Pemeran[] getPemeran() {
        return pemeran;
    }

    public void setPemeran(Pemeran[] pemeran) {
        this.pemeran = pemeran;
    }

    public String getTglRilis() {
        return tglRilis;
    }

    public void setTglRilis(String tglRilis) {
        this.tglRilis = tglRilis;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
