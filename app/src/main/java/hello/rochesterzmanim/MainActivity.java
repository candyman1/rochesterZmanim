package hello.rochesterzmanim;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Intent;
import android.content.Loader;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity  implements LoaderCallbacks<Time> {


    private static final int EARTHQUAKE_LOADER_ID = 1;

    private static final String ZMAN_REQUEST_URL =
            "https://api.sunrise-sunset.org/json?lat=43.1323651,&lng=-77.5734929&date=";
    private static final int TIME_LOADER_ID = 1;

    String currentDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //https://api.sunrise-sunset.org/json?lat=40.087528,&lng=-74.208183
        TimeZone timeZone = TimeZone.getDefault();
        Date currentTime = Calendar.getInstance(timeZone).getTime();
        String time = currentTime.toString();

        String month = time.substring(4, 7);
        String day = time.substring(8, 10);
        String year = time.substring(time.length() - 4, time.length());
        String month1 = month;
        switch (month) {
            case "Jan":
                month1 = "01";
                break;
            case "Feb":
                month1 = "02";
                break;
            case "Mar":
                month1 = "03";
                break;
            case "Apr":
                month1 = "04";
                break;
            case "May":
                month1 = "05";
                break;
            case "Jun":
                month1 = "06";
                break;
            case "Jul":
                month1 = "07";
                break;
            case "Aug":
                month1 = "08";
                break;
            case "Sep":
                month1 = "09";
                break;
            case "Oct":
                month1 = "10";
                break;
            case "Nov":
                month1 = "11";
                break;
            case "Dec":
                month1 = "12";
                break;
        }
        int yor=Integer.parseInt(year);
        int mont=Integer.parseInt(month1);
        int da=Integer.parseInt(day);

currentDate = year + "-" + month1 + "-" + day;

        TextView todays = (TextView) findViewById(R.id.today);
        todays.setText(month + " " + day + ", " + year);

        Button next = (Button) findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent startIntent = new Intent(MainActivity.this, Main2Activity.class);

                startActivity(startIntent);
            }
        });


        // String jason="{\"results\":{\"sunrise\":\"12:18:01 PM\",\"sunset\":\"9:38:38 PM\",\"solar_noon\":\"4:58:19 PM\",\"day_length\":\"09:20:37\",\"civil_twilight_begin\"" +
        //        ":\"11:47:34 AM\",\"civil_twilight_end\":\"10:09:04 PM\",\"nautical_twilight_begin\":\"11:13:30 AM\",\"nautical_twilight_end\":\"10:43:09 PM\"," +
        //      "\"astronomical_twilight_begin\":\"10:40:30 AM\",\"astronomical_twilight_end\":\"11:16:09 PM\"},\"status\":\"OK\"}";

        ZmanAsyncTask newTask = new ZmanAsyncTask();
        newTask.execute(ZMAN_REQUEST_URL+currentDate);

        LoaderManager loaderManager = getLoaderManager();

        // Initialize the loader. Pass in the int ID constant defined above and pass in null for
        // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
        // because this activity implements the LoaderCallbacks interface).
        loaderManager.initLoader(TIME_LOADER_ID, null, this);

    }


    private void updateUi(Time earthquake) {
        TextView olos = (TextView) findViewById(R.id.dawn);
        olos.setText(earthquake.early);
        TextView sunrise = (TextView) findViewById(R.id.rise);
        sunrise.setText(earthquake.rise + "AM");

        TextView sunset = (TextView) findViewById(R.id.set);
        sunset.setText(earthquake.set + "PM");

        TextView magen = (TextView) findViewById(R.id.shema1);
        magen.setText(earthquake.magenA + "AM");

        TextView goan = (TextView) findViewById(R.id.shema);
        goan.setText(earthquake.dayLength);

        TextView daven = (TextView) findViewById(R.id.shmona);
        daven.setText(earthquake.amida);

        TextView gadol = (TextView) findViewById(R.id.minchaG);
        gadol.setText(earthquake.gedola);

        TextView katan = (TextView) findViewById(R.id.minchaK);
        katan.setText(earthquake.ketana);

        TextView plogM = (TextView) findViewById(R.id.plagM);
        plogM.setText(earthquake.plag);

        TextView rabbi = (TextView) findViewById(R.id.late);
        rabbi.setText(earthquake.tzais);
    }

    private class ZmanAsyncTask extends AsyncTask<String, Void, Time> {
        @Override
        protected Time doInBackground(String... urls) {
            // Don't perform the request if there are no URLs, or the first URL is null.
            if (urls.length < 1 || urls[0] == null) {
                return null;
            }
            // Perform the HTTP request for earthquake data and process the response.
            Time result = Utils.fetchEarthquakeData(urls[0]);
            return result;
        }

        @Override
        protected void onPostExecute(Time result) {
            // If there is no result, do nothing.
            if (result == null) {
                return;
            }

            // Update the information displayed to the user.

            updateUi(result);
        }
    }


    @Override
    public Loader<Time> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Time> loader, Time earthquakes) {

    }

    @Override
    public void onLoaderReset(Loader<Time> loader) {

    }
}


