package amalia.dev.dicodingmade.repository;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import amalia.dev.dicodingmade.repository.realm.RealmContract;

public class LoadGenresNameAsync extends AsyncTask<Void, Void, ArrayList<String>> {
    private final WeakReference<Context> weakContext; //using weakreference object to avoid "this field leak context object" warning
    private List<Integer> list;
    private ArrayList<String> genresName;

    public LoadGenresNameAsync(Context context, List<Integer> listId, ArrayList<String> listGenresName) {
        weakContext = new WeakReference<>(context);
        this.list = listId;
        this.genresName = listGenresName;
    }

    @Override
    protected final ArrayList<String> doInBackground(Void... voids) {

        ArrayList<String> genresName = new ArrayList<>();
        Context context = weakContext.get();
        for (int j = 0; j < list.size(); j++) {
            int idValue = list.get(j);
            Uri uri = Uri.parse(RealmContract.GenreColumns.CONTENT_URI + "/" + idValue);
            Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
            if (cursor != null && cursor.moveToNext()) {
                genresName.add(cursor.getString(cursor.getColumnIndexOrThrow(RealmContract.GenreColumns.COLUMN_NAME_GENRE_NAME)));
                cursor.close();
            }


        }
        return genresName;
    }

    @Override
    protected void onPostExecute(ArrayList<String> strings) {
        super.onPostExecute(strings);
        genresName = strings;
    }
}
