package com.universalstudios.orlandoresort.controller.userinterface.events;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.universalstudios.orlandoresort.BuildConfig;
import com.universalstudios.orlandoresort.R;
import com.universalstudios.orlandoresort.controller.userinterface.data.DatabaseQuery;
import com.universalstudios.orlandoresort.controller.userinterface.explore.ExploreType;
import com.universalstudios.orlandoresort.controller.userinterface.explore.map.ExploreMapFragment;
import com.universalstudios.orlandoresort.controller.userinterface.network.NetworkRefreshActivity;
import com.universalstudios.orlandoresort.model.network.response.GsonObject;

/**
 * @author acampbell
 *
 */
public class EventMapActivity extends NetworkRefreshActivity {

    private static final String TAG = EventMapActivity.class.getSimpleName();

    private static final String KEY_ARG_TITLE = "KEY_ARG_TITLE";
    private static final String KEY_ARG_DATABASE_QUERY_JSON = "KEY_ARG_DATABASE_QUERY_JSON";

    private String mTitle;
    private DatabaseQuery mDatabaseQuery;

    public static Bundle newInstanceBundle(String title, DatabaseQuery databaseQuery) {
        Bundle args = new Bundle();
        args.putString(KEY_ARG_TITLE, title);
        args.putString(KEY_ARG_DATABASE_QUERY_JSON, databaseQuery.toJson());

        return args;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (BuildConfig.DEBUG) {
            Log.v(TAG, "onCreate: savedInstanceState " + (savedInstanceState == null ? "==" : "!=") + " null");
        }
        setContentView(R.layout.activity_event_map);

        // Default parameters
        Bundle args = getIntent().getExtras();
        if (args != null) {
            mTitle = args.getString(KEY_ARG_TITLE);
            mDatabaseQuery = GsonObject.fromJson(args.getString(KEY_ARG_DATABASE_QUERY_JSON),
                    DatabaseQuery.class);
        }

        // If this is the first creation, default state variables
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.activity_event_map_fragment_container,
                            ExploreMapFragment.newInstance(mDatabaseQuery, ExploreType.EVENT_MAP)).commit();
        }
        // Otherwise, restore state
        else {}

        getActionBar().setTitle(mTitle);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (BuildConfig.DEBUG) {
            Log.v(TAG, "onOptionsItemSelected");
        }

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
