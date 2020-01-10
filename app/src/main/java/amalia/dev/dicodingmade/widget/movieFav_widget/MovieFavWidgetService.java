package amalia.dev.dicodingmade.widget.movieFav_widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class MovieFavWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new MovieFavRemoteViewsFactory(this.getApplicationContext());
    }
}
