package serviceclasses;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by syv on 25.02.15.
 */
public class Date {
    SimpleDateFormat sdf;
    Calendar calendar;
    public String currentData;

    public Date(){
        this.sdf = new SimpleDateFormat("yyMMddHHmm");
        this.calendar = new GregorianCalendar(2013,10,28);
        calendar = Calendar.getInstance();
        currentData=sdf.format(calendar.getTime());
    }


    public String getCurrentDate(){// Get current date from System
        calendar = Calendar.getInstance();
        currentData=sdf.format(calendar.getTime());
        //System.out.println(currentData);
        return currentData;
    }
}
