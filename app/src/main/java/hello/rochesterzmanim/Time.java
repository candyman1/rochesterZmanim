package hello.rochesterzmanim;

/**
 * Created by Tatty on 12/30/2017.
 */

public class Time {

    public final String early;

    public final String rise;

    public final String set;

    public final String magenA;

    public final String dayLength;

    public final String amida;

    public final String gedola;

    public final String ketana;

    public final String plag;

    public final String tzais;



    public Time(String earlyMorn, String sunrise, String sunset, String magenAv, String day,
                String esrai, String mincha, String little, String pMincha, String late){

        early=earlyMorn;

        rise=sunrise;

        set=sunset;

        magenA=magenAv;

        dayLength=day;

        amida=esrai;

        gedola=mincha;

        ketana=little;

        plag=pMincha;

        tzais=late;
    }
}
