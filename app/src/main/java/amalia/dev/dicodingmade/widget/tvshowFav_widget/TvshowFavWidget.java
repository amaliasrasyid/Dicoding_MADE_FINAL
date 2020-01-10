package amalia.dev.dicodingmade.widget.tvshowFav_widget;

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
public class TvshowFavWidget extends AppWidgetProvider {
    public static final String UPDATE_WIDGET_TV = "amalia.dev.dicodingmade.UPDATE_WIDGET_TV";
    public static final String EXTRA_POSITION_ITEM_TV = "amalia.dev.dicodingmade.EXTA_POSITION_ITEM_TV";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        Intent intent = new Intent(context,TvshowFavWidgetService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,appWidgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.tvshow_fav_widget);
        views.setRemoteAdapter(R.id.stackview_tvshowfav_widget,intent);
        views.setEmptyView(R.id.stackview_tvshowfav_widget,R.id.text_empty_view_tvshowfav_widget);

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

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction() != null){
            if(intent.getAction().equals(UPDATE_WIDGET_TV)){
                //refresh data widget using notify data changed
                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                ComponentName thisWidget = new ComponentName(context,TvshowFavWidget.class);
                int[] widgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
                appWidgetManager.notifyAppWidgetViewDataChanged(widgetIds,R.id.stackview_tvshowfav_widget);
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

