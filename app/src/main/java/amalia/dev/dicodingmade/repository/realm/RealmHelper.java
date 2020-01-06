package amalia.dev.dicodingmade.repository.realm;



import androidx.annotation.NonNull;


import amalia.dev.dicodingmade.model.GenreRealmObject;
import amalia.dev.dicodingmade.model.MovieRealmObject;
import amalia.dev.dicodingmade.model.TvShowRealmObject;
import io.realm.Realm;
import io.realm.RealmResults;

public class RealmHelper {

    private final Realm realm;

    public RealmHelper(Realm realm) {
        this.realm = realm;
    }

    /**HOW TO INSERT
     * there is two way,first by creating the object itself and second using
     * copyToRealm() method and pass the object into it
     * */
    //create or insert data MovieRealmObject
    public void insertMovie(final MovieRealmObject movie){
        //by using executeTransaction the beginTransaction and commintTransaction will call automatically
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm realm) {
                    //set the number for PK
                    movie.setId(movie.getId());
                    //and inserting into db
                    realm.copyToRealm(movie);

            }
        });
    }

    //create or insert data tvshow
    public void insertTvShow(final TvShowRealmObject tvShow){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm realm) {
                    tvShow.setId(tvShow.getId());
                    //and insert into db local
                    realm.copyToRealm(tvShow);
            }
        });
    }

    //get list movie from local db
    public RealmResults<MovieRealmObject> getListFavoriteMovies(){
        realm.refresh();//untuk menghindari data tertimpa dg data lain jika realm dijalankan secara async (khusus kasus my content provider). more information (https://github.com/realm/realm-java/issues/3932)
        return realm.where(MovieRealmObject.class).equalTo("tmpDelete",false).findAll();
    }

    //get list tvshow from local db
    public RealmResults<TvShowRealmObject> getListFavoriteTvShows(){
        realm.refresh();
        return realm.where(TvShowRealmObject.class).equalTo("tmpDelete",false).findAll();
    }

    //search data for checking if marked or not (favorite)
    public boolean isMovieExist(int id){
        MovieRealmObject movie = realm.where(MovieRealmObject.class).equalTo("id",id).findFirst();
        return (movie != null);//when there is no data, it will null
    }

    public boolean isTvShowExist(int id){
        TvShowRealmObject tvShow = realm.where(TvShowRealmObject.class).equalTo("id",id).findFirst();
        return (tvShow != null);//when there is no data, it will return  null
    }

    //delete data fav movie
    public void deleteFavMovies(int id){
        //first, find the movie with the id
        final RealmResults<MovieRealmObject> movie = realm.where(MovieRealmObject.class).equalTo("id",id).findAll();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(@NonNull  Realm realm) {
                movie.deleteFromRealm(0);//todo:why "0" ?
            }
        });
    }

    //delete data fav tv show
    public void deleteFavTvShow(int id){
        //first, find the tvshow based the given id
        final RealmResults<TvShowRealmObject> tvShow = realm.where(TvShowRealmObject.class).equalTo("id",id).findAll();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm realm) {
                tvShow.deleteFromRealm(0);
            }
        });
    }

    //update tmpDelete status movie
    public void updateTmpDeleteM(final int id, final boolean tmpDeleteStatus){
        //update objects on a background thread
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm realm) {
                MovieRealmObject mMovie = realm.where(MovieRealmObject.class).equalTo("id",id).findFirst();
                if(mMovie != null){
                    mMovie.setTmpDelete(tmpDeleteStatus);
                    realm.insertOrUpdate(mMovie);
                }
            }
        });
    }

    //update tmpDelete status tvshow
    public void updateTmpDeleteTS(final int id, final boolean tmpDeleteStatus){
        //update objects on a background thread
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                TvShowRealmObject mTvshow = realm.where(TvShowRealmObject.class).equalTo("id",id).findFirst();
                if(mTvshow != null){
                    mTvshow.setTmpDelete(tmpDeleteStatus);
                    realm.insertOrUpdate(mTvshow);
                }
            }
        });

    }

    //load genre data from json (API)
    public void insertGenre(final GenreRealmObject genre){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm realm) {
                genre.setId(genre.getId());
                realm.copyToRealm(genre);
            }
        });
    }

    public String getGenreName(int id){
        GenreRealmObject genre = realm.where(GenreRealmObject.class).equalTo("id",id).findFirst();
        if(genre != null){
            return genre.getName();
        }else{
            return "";
        }
    }

    public GenreRealmObject getGenre(int id){
        return realm.where(GenreRealmObject.class).equalTo("id",id).findFirst();
    }


}