package hello.rochesterzmanim;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

/**
 * Created by Tatty on 1/7/2018.
 */

public class TimeLoader extends AsyncTaskLoader<Time> {
    /** Tag for log messages */
    private static final String LOG_TAG =TimeLoader.class.getName();

    /** Query URL */
    private String mUrl;

    /**
     * Constructs a new {@link TimeLoader}.
     *
     * @param context of the activity
     * @param url to load data from
     */
    public TimeLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    /**
     * This is on a background thread.
     */
    @Override
    public Time loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of earthquakes.
        Time earthquakes =Utils.fetchEarthquakeData(mUrl);
        return earthquakes;
    }
}


