package hello.rochesterzmanim;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.DecimalFormat;

/**
 * Created by Tatty on 12/30/2017.
 */

public final class Utils {
    public static final String LOG_TAG = Utils.class.getSimpleName();

    /**
     * Query the USGS dataset and return an {@link Time} object to represent a single earthquake.
     */
    public static Time fetchEarthquakeData(String requestUrl) {
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }

        // Extract relevant fields from the JSON response and create an {@link Event} object
        Time earthquake = extractFeatureFromJson(jsonResponse);

        // Return the {@link Event}
        return earthquake;
    }

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating URL ", e);
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    /**
     * Return an {@link Time} object by parsing out information
     * about the first earthquake from the input earthquakeJSON string.
     */
    private static Time extractFeatureFromJson(String earthquakeJSON) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(earthquakeJSON)) {
            return null;
        }

        try {

            JSONObject jsonRootObject = new JSONObject(earthquakeJSON);
            JSONObject jsonObj=jsonRootObject.getJSONObject("results");
            String rise=jsonObj.getString("sunrise");
            String set=jsonObj.getString("sunset");
            String len=jsonObj.getString("day_length");
            // If there are results in the features array
String start=rise.substring(0,2);
            switch(start){
                case"1:":
                   start="9:";
                    break;
                case"8:":
                    start="4:";
                    break;
                case"9:":
                    start="5:";
                    break;
                case"10":
                    start="6";
                    break;
                case"11":
                    start="7";
                    break;
                case"12":
                   start="8";
                    break;}

               rise=start+rise.substring(2,rise.length()-2);

String begin=set.substring(0,2);
            switch(begin){
                case"1:":
                    begin="9:";
                    break;
                case"2:":
                    begin="10:";
                    break;
                case"3:":
                    begin="11";
                    break;

                case"9:":
                    begin="5:";
                    break;
                case"10":
                    begin="6";
                    break;
                case"11":
                    begin="7";
                case"12":
                    begin="8";
                    break;

            }
            set=begin+set.substring(2,set.length()-2);
                // Extract out the first feature (which is an earthquake)
int hour=Integer.parseInt(len.substring(0,2));
            int minute=Integer.parseInt(len.substring(3,5));
            int second=Integer.parseInt(len.substring(6,8));
            int totalSeconds = ((hour * 60) + minute) * 60 + second;
            int split=totalSeconds/4;
            int zmanios=totalSeconds/12;

            DecimalFormat timePattern=new DecimalFormat("00");
            int shu=Integer.parseInt(rise.substring(0,1));
            int doku=Integer.parseInt(rise.substring(2,4));
            int rego=Integer.parseInt(rise.substring(5,7));
            int col=((shu*60)+doku)*60+rego;
            int gomZu=col+split;


            int hours = gomZu / 3600;  // Be sure to use integer arithmetic
            int minutes = ((gomZu) / 60) % 60;
            int seconds = gomZu % 60;
            String ow=Integer.toString(hours);
            String mini=Integer.toString(minutes);
            String sec=Integer.toString(seconds);
            len=" "+ow+":"+timePattern.format(minutes)+":"+timePattern.format(seconds)+"AM";

            int yudChes=gomZu+zmanios;
            int large=yudChes/3600;
            int notLarge=((yudChes)/60)%60;
            int veryNot=yudChes%60;
            String sfard=Integer.toString(large);
            String kiddush=" "+sfard+":"+timePattern.format(notLarge)+":"+timePattern.format(veryNot)+"AM";
//olos
            int first=col-4320;
            int hou=first/3600;
            int minu=((first)/60)%60;
            int seco=first%60;
            String ows=Integer.toString(hou);
            String minis=Integer.toString(minu);
            String secs=Integer.toString(seco);
            //Shema MA
            int avrohom=totalSeconds+8640;
            int twelve=avrohom/12;
            int magen=first+(twelve*3);
            int our=magen/3600;
            int inute=((magen)/60)%60;
            int econd=magen%60;
            String agen=Integer.toString(our);
            String coffee=" "+agen+":"+timePattern.format(inute)+":"+timePattern.format(econd);
//tzais
            int rabbainu=totalSeconds+col+4320-43200;
            int big=rabbainu/3600;
            int little=((rabbainu)/60)%60;
            int tiny=rabbainu%60;
            String all=Integer.toString(big);
            String late=" "+all+":"+timePattern.format(little)+":"+timePattern.format(tiny)+"PM";

            String firstT=" "+ows+":"+timePattern.format(minu)+":"+timePattern.format(seco)+"AM";
           //mincha gedola
            int min=totalSeconds/12;
            int minch=col+(min*6)+(totalSeconds/24);
            int pizza=minch/3600;
            int yogurt=((minch)/60)%60;
            int bars=minch%60;
            String bagel=Integer.toString(pizza);
            if(bagel.equals("13")){
                bagel="1";
            }
            String eggs=" "+bagel+":"+timePattern.format(yogurt)+":"+timePattern.format(bars)+"PM";
            //mincha ketana
            int small=col+(min*9)+(totalSeconds/24);
            int cholent=small/3600;
            int kugel=((small)/60)%60;
            int kishka=small%60;
            String arbis=Integer.toString(cholent);
            if(arbis.equals("14")){
                arbis="2";
            }
            if(arbis.equals("15")){
                arbis="3";
            }
            if(arbis.equals("16")){
                arbis="4";
            }
            if(arbis.equals("17")){
                arbis="5";
            }
            String jello=" "+arbis+":"+timePattern.format(kugel)+":"+timePattern.format(kishka)+"PM";
            //plag
            int quart=totalSeconds/48;
            int plog=col+(min*10)+(quart*3);
            int fish=plog/3600;
            int chrain=((plog)/60)%60;
            int carrot=plog%60;
            String royal=Integer.toString(fish);
            if(royal.equals("15")){
                royal="3";
            }
            if(royal.equals("16")){
                royal="4";
            }
            if(royal.equals("17")){
                royal="5";
            }
            if(royal.equals("18")){
                royal="6";
            }
            if(royal.equals("19")){
                royal="7";
            }

            String unger=" "+royal+":"+timePattern.format(chrain)+":"+timePattern.format(carrot)+"PM";
                return new Time(firstT,rise,set,coffee,len,kiddush,eggs,jello,unger,late);

        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the earthquake JSON results", e);
        }
        return null;
    }
}

