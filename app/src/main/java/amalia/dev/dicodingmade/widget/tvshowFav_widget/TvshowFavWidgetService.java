package amalia.dev.dicodingmade.widget.tvshowFav_widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class TvshowFavWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new TvshowFavRemoteViewsFactory(this.getApplicationContext());
    }
}
