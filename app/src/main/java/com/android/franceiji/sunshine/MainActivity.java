package com.android.franceiji.sunshine;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new ForecastFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            //Start the activity corresponding to the Settings
            Intent intent_open_settings = new Intent(this, SettingsActivity.class);
            startActivity(intent_open_settings);

            return true;
        }

        if (id == R.id.action_map) {
            //Start the function showing on the map the location related to the weather
            openPreferredLocationInMap();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void openPreferredLocationInMap()
    {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String location = prefs.getString(getString(R.string.pref_location_key), getString(R.string.pref_location_default));

        Uri geo_location = Uri.parse("geo:0,0?").buildUpon().appendQueryParameter("q", location).build();

        Intent show_on_map = new Intent(Intent.ACTION_VIEW);
        show_on_map.setData(geo_location);

        if (show_on_map.resolveActivity(getPackageManager()) != null)
        {
            startActivity(show_on_map);
        }
        else
        {
            final String LOG_TAG = "DISPLAY ON MAP";
            Log.d(LOG_TAG, "Couldn't display location " + location);
        }

    }

}
