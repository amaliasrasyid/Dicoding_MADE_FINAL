package amalia.dev.dicodingmade.widget;

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
public class ImgFavWidgetProvider extends AppWidgetProvider {
    private static final String TOAST_ACTION = "amalia.dev.dicodingmade.TOAST_ACTION";
    public static final String EXTRA_ITEM = "amalia.dev.dicodingmade.EXTRA_ITEM";
    public static final String UPDATE_WIDGET = "amalia.dev.dicodingmade.UPDATE_WIDGET";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        Intent intent = new Intent(context, StackWidgetService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
        CharSequence widgetText = context.getString(R.string.app_widget_text);

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.img_favorites_widget);
        views.setRemoteAdapter(R.id.stackview_imgfavoriteswidget, intent);
        views.setEmptyView(R.id.stackview_imgfavoriteswidget, R.id.text_empty_view);//set view empty when there's no data


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
        if (intent.getAction() != null) {
            if (intent.getAction().equals(UPDATE_WIDGET)) {
                //notify if data change for auto update (?)
                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                ComponentName thisWidget = new ComponentName(context, ImgFavWidgetProvider.class);
                int[] widgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
                appWidgetManager.notifyAppWidgetViewDataChanged(widgetIds, R.id.stackview_imgfavoriteswidget);
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

