package hello.rochesterzmanim;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class Main2Activity extends AppCompatActivity implements LoaderCallbacks<Time> {

    private static final String ZMAN_REQUEST_URL =
            "https://api.sunrise-sunset.org/json?lat=43.1323651,&lng=-77.5734929&date=";
    String month1;
    String day;
    private static final int TIME_LOADER_ID = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);




        TimeZone timeZone = TimeZone.getDefault();
        Date currentTime = Calendar.getInstance(timeZone).getTime();
        String time=currentTime.toString();

        String month=time.substring(4,7);
       day =time.substring(8,10);
        String year=time.substring(time.length()-4,time.length());



        switch(month){
            case"Jan":
                month1="01";
                break;
            case"Feb":
                month1="02";
                break;
            case"Mar":
                month1="03";
                break;
            case"Apr":
                month1="04";
                break;
            case"May":
                month1="05";
                break;
            case"Jun":
                month1="06";
                break;
            case"Jul":
                month1="07";
                break;
            case"Aug":
                month1="08";
                break;
            case"Sep":
                month1="09";
                break;
            case"Oct":
                month1="10";
                break;
            case"Nov":
                month1="11";
                break;
            case"Dec":
                month1="12";
                break;
        }
        int yor=Integer.parseInt(year);
        int mont=Integer.parseInt(month1);
        int da=Integer.parseInt(day);
        if(da==31&&mont==1){
            month="Feb";
           da=0;
            month1="02";
            day="01";
        }
        if(da==28&&mont==2){
            month="Mar";
            month1="03";
            da=0;
            day="01";
        }
        if(da==29&&mont==2){
            month="Mar";
            month1="03";
            da=0;
            day="01";
        }
        if(da==31&&mont==3){
            month="Apr";
            month1="04";
            da=0;
            day="01";
        }
        if(da==30&&mont==4){
            month="May";
            month1="05";
            da=0;
            day="01";
        }
        if(da==31&&mont==5){
            month="Jun";
            month1="06";
            da=0;
            day="01";
        }
        if(da==30&&mont==6){
            month="Jul";
            month1="07";
            da=0;
            day="01";
        }
        if(da==31&&mont==7){
            month="Aug";
            da=0;
            month1="08";
            day="01";
        }
        if(da==31&&mont==8){
            month="Sep";
            month1="09";
            da=0;
            day="01";
        }
        if(da==30&&mont==9){
            month="Oct";
            month1="10";
            da=0;
            day="01";
        }
        if(da==31&&mont==10){
            month="Nov";
            month1="11";
            da=0;
            day="01";
        }
        if(da==30&&mont==11){
            month="Dec";
            month1="12";
            da=0;
            day="01";
        }
        if(da==31&&mont==12){
            month="Jan";
            month1="01";
            da=0;
            day="01";
            yor++;
            year=Integer.toString(yor);
        }
        da++;
        day=Integer.toString(da);


        TextView todays=(TextView)findViewById(R.id.today);
        todays.setText(month +" "+day+", "+year);

        int yea=Integer.parseInt(year);
        String currentDate=year+"-"+month1+"-"+day;

        ZmanAsyncTask newTask = new ZmanAsyncTask();
        newTask.execute(ZMAN_REQUEST_URL+currentDate);

        LoaderManager loaderManager = getLoaderManager();

        // Initialize the loader. Pass in the int ID constant defined above and pass in null for
        // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
        // because this activity implements the LoaderCallbacks interface).
        loaderManager.initLoader(TIME_LOADER_ID, null, this);
    }

    /**
     * Update the UI with the given earthquake information.
     */
    private void updateUi(Time earthquake) {
        TextView olos=(TextView)findViewById(R.id.dawn);
        olos.setText(earthquake.early);
        TextView sunrise = (TextView) findViewById(R.id.rise);
        sunrise.setText(earthquake.rise+"AM");

        TextView sunset = (TextView) findViewById(R.id.set);
        sunset.setText(earthquake.set+"PM");

        TextView magen=(TextView)findViewById(R.id.shema1);
        magen.setText(earthquake.magenA+"AM");

        TextView goan=(TextView)findViewById(R.id.shema);
        goan.setText(earthquake.dayLength);

        TextView daven=(TextView)findViewById(R.id.shmona);
        daven.setText(earthquake.amida);

        TextView gadol=(TextView)findViewById(R.id.minchaG);
        gadol.setText(earthquake.gedola);

        TextView katan=(TextView)findViewById(R.id.minchaK);
        katan.setText(earthquake.ketana);

        TextView plogM=(TextView)findViewById(R.id.plagM);
        plogM.setText(earthquake.plag);

        TextView rabbi=(TextView)findViewById(R.id.late);
        rabbi.setText(earthquake.tzais);
    }

    @Override
    public Loader<Time> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Time> loader, Time data) {

    }

    @Override
    public void onLoaderReset(Loader<Time> loader) {

    }

    private class ZmanAsyncTask extends AsyncTask<String,Void,Time> {
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
        protected void onPostExecute(Time result){
            // If there is no result, do nothing.
            if (result == null) {
                return;
            }

            // Update the information displayed to the user.

            updateUi(result);
        }
    }

}





