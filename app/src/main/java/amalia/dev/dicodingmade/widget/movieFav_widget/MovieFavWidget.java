package amalia.dev.dicodingmade.widget.movieFav_widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import amalia.dev.dicodingmade.R;

/**
 * Implementation of App Widget functionality.
 */
public class MovieFavWidget extends AppWidgetProvider {
    public static final String EXTRA_POSITION_ITEM_MOVIE = "amalia.dev.dicodingmade.EXTRA_POSITION_ITEM_MOVIE";
    public static final String UPDATE_WIDGET_MOVIE = "amalia.dev.dicodingmade.UPDATE_WIDGET_MOVIE";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        Intent intent = new Intent(context, MovieFavWidgetService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
//        CharSequence widgetText = context.getString(R.string.app_widget_movie_text);

        // Construct the RemoteViews object for getting layout that will used
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.movie_fav_widget);
        views.setRemoteAdapter(R.id.stackview_moviefav_widget, intent);
        views.setEmptyView(R.id.stackview_moviefav_widget, R.id.text_empty_view_moviefav_widget);//set view empty when there's no data

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override //this method implements onReceive in BroadcastReceiver, so there is no need to create class BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() != null) {
            if (intent.getAction().equals(UPDATE_WIDGET_MOVIE)) {
                //notify if data change for auto update
                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                ComponentName thisWidget = new ComponentName(context, MovieFavWidget.class);
                int[] widgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
                appWidgetManager.notifyAppWidgetViewDataChanged(widgetIds, R.id.stackview_moviefav_widget);
            }
        }
        super.onReceive(context, intent);

    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

