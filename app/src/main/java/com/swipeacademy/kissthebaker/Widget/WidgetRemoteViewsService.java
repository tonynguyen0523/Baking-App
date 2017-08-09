package com.swipeacademy.kissthebaker.Widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.database.Cursor;
import android.os.Binder;
import android.support.annotation.Nullable;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.swipeacademy.kissthebaker.R;
import com.swipeacademy.kissthebaker.data.RecipeListColumns;
import com.swipeacademy.kissthebaker.data.RecipeProvider;

/**
 * Created by tonyn on 8/8/2017.
 */

public class WidgetRemoteViewsService extends IntentService{

    private static final String[] RECIPE_COLUMNS = {
            RecipeListColumns._ID,
            RecipeListColumns.RECIPE_NAME,
            RecipeListColumns.SERVING_SIZE
    };

    private static final int INDEX_ID = 0;
    private static final int INDEX_RECIPE_NAME = 1;
    private static final int INDEX_RECIPE_SERVING_SIZE = 2;

    public WidgetRemoteViewsService(){
        super("WidgetRemoteViewsService");
    }

//    @Override
//    public RemoteViewsService.RemoteViewsFactory onGetViewFactory(Intent intent) {
//        return new RemoteViewsFactory() {
//            private Cursor data = null;
//
//            @Override
//            public void onCreate() {
//
//            }
//
//            @Override
//            public void onDataSetChanged() {
//                if(data != null){
//                    data.close();
//                }
//
//                final long identityToken = Binder.clearCallingIdentity();
//                data = getContentResolver().query(RecipeProvider.RecipeList.CONTENT_URI,
//                        RECIPE_COLUMNS,
//                        null,
//                        null,
//                        null);
//                Binder.restoreCallingIdentity(identityToken);
//            }
//
//            @Override
//            public void onDestroy() {
//                if (data != null){
//                    data.close();
//                    data = null;
//                }
//            }
//
//            @Override
//            public int getCount() {
//                return data == null ? 0 : data.getCount();
//            }
//
//            @Override
//            public RemoteViews getViewAt(int position) {
//                if(position == AdapterView.INVALID_POSITION ||
//                        data == null || !data.moveToPosition(position)) {
//                    return null;
//                }
//
//                RemoteViews views = new RemoteViews(getPackageName(),
//                        R.layout.favorite_recipe_widget_list_item);
//
//                String name = data.getString(INDEX_RECIPE_NAME);
//                int servingSize = data.getInt(INDEX_RECIPE_SERVING_SIZE);
//
//                views.setTextViewText(R.id.widget_recipe_name, name);
////                views.setTextViewText(R.id.widget_serving_size,getApplicationContext().getString(R.string.servings,servingSize));
//
//                return views;
//            }
//
//            @Override
//            public RemoteViews getLoadingView() {
//                return new RemoteViews(getPackageName(),R.layout.favorite_recipe_widget_list_item);
//            }
//
//            @Override
//            public int getViewTypeCount() {
//                return 0;
//            }
//
//            @Override
//            public long getItemId(int i) {
//                if (data.moveToPosition(i))
//                    return data.getLong(INDEX_ID);
//                return i;
//            }
//
//            @Override
//            public boolean hasStableIds() {
//                return false;
//            }
//        };
//    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, FavoriteRecipeWidgetProvider.class));

        Cursor data = getContentResolver().query(RecipeProvider.RecipeList.CONTENT_URI, RECIPE_COLUMNS, null,
                null, null);

        if(data == null){
            return;
        }

        if (!data.moveToFirst()){
            data.close();
            return;
        }

        String recipeName = data.getString(INDEX_RECIPE_NAME);
        int servingSize = data.getInt(INDEX_RECIPE_SERVING_SIZE);
        data.close();

        for (int appWidgetId : appWidgetIds){
            int layoutId = R.layout.favorite_recipe_widget;
            RemoteViews views = new RemoteViews(getPackageName(),layoutId);

            views.setTextViewText(R.id.);
        }
    }
}
