package huedev.org.utils.helpers;

import android.content.Context;
import android.widget.RadioButton;

import java.text.SimpleDateFormat;
import java.util.Date;

import huedev.org.utils.AppPrefs;

public class StringHelper {
    public static String dateToString(){
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("d/M/yyyy");
        long Ltime = date.getTime();
        String Stime = simpleDateFormat.format(Ltime);
        return Stime;
    }

    public static String dateToString(int date, int month){
        return date + "/" + month;
    }

    public static String dateToString(long date){
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMMM , 'yyyy");
        String sDate = sdf.format(date);
        return sDate;
    }

    public static String dateToString(int date, int month, int year){
        return date + "/" + (month + 1) + "/" + year;
    }



    public static String getStringResourceByName(String aString, Context mContext) {
        String packageName = mContext.getPackageName();
        int resId = mContext.getResources().getIdentifier(aString, "string", packageName);
        return mContext.getString(resId);
    }

    public static String formatString(int n){
        String format = String.valueOf(n);
        return format;
    }
    public static String formatStringRole(int role, Context mContext){
        String sRole;
        if (role== 0){
            sRole = StringHelper.getStringResourceByName("member", mContext);
        }else if (role == 1){
            sRole = StringHelper.getStringResourceByName("technicians", mContext);
        }else {
            sRole = StringHelper.getStringResourceByName("admin", mContext);
        }
        return sRole;
    }

    public static String formatStringStatus(int status, Context mContext){
        String sStatus;
        if (status == 0){
            sStatus = "Active";
        }else if (status == 1){
            sStatus = "Repair";
        }else {
            sStatus = "Broken";
        }
        return sStatus;
    }

}
