package amalia.dev.dicodingmade.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;
import android.widget.Toast;

import amalia.dev.dicodingmade.R;

/**
 * Implementation of App Widget functionality.
 */
public class ImgFavoritesWidget extends AppWidgetProvider {
    private static final String TOAST_ACTION = "amalia.dev.dicodingmade.TOAST_ACTION";
    public static final String EXTRA_ITEM = "amalia.dev.dicodingmade.EXTRA_ITEM";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        Intent intent = new Intent(context,StackWidgetService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,appWidgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
        CharSequence widgetText = context.getString(R.string.app_widget_text);

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.img_favorites_widget);
        views.setRemoteAdapter(R.id.stackview_imgfavoriteswidget,intent);
        views.setEmptyView(R.id.stackview_imgfavoriteswidget,R.id.text_empty_view);//set view empty when there's no data

        Intent toastIntent = new Intent(context,ImgFavoritesWidget.class);
        toastIntent.setAction(ImgFavoritesWidget.TOAST_ACTION);
        toastIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,appWidgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

        PendingIntent toastPendingIntent = PendingIntent.getBroadcast(context,0,toastIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.stackview_imgfavoriteswidget,toastPendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId,views);

//        CharSequence widgetText = context.getString(R.string.app_widget_text);
//        // Construct the RemoteViews object
//        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.img_favorites_widget);
//        views.setTextViewText(R.id.appwidget_text, widgetText);
//
//        // Instruct the widget manager to update the widget
//        appWidgetManager.updateAppWidget(appWidgetId, views);
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
        super.onReceive(context, intent);
        if(intent.getAction() != null){
            if(intent.getAction().equals(TOAST_ACTION)){
                int viewIndex = intent.getIntExtra(EXTRA_ITEM,0);
                Toast.makeText(context,"Touched view "+viewIndex,Toast.LENGTH_SHORT).show();
            }
        }
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

