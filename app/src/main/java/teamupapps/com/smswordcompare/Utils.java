package teamupapps.com.smswordcompare;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created by clazell on 29/10/2014.
 */
public class Utils {

    public static int sortFriendsWords(Context context,String match){
        Cursor cursor = context.getContentResolver().query(Uri.parse("content://sms/inbox"), null, null, null, null);
        String line;
        String matchCheck;
        int num = 0;
        if (cursor.moveToFirst()) {
            for (int i = 0; i < cursor.getCount(); i++) {
                line = cursor.getString(cursor.getColumnIndexOrThrow("body")).toString();
                matchCheck = cursor.getString(cursor.getColumnIndexOrThrow("address"));
                if (match.equalsIgnoreCase(matchCheck)) {

                    String[] words = line.split("\\s+");//"[^A-ZÃ…Ã„Ã–a-zÃ¥Ã¤Ã¶]+"
                    for (String word : words) {
                        if ("".equals(word)) {
                            continue;
                        }
                        num++;
                    }
                }
                cursor.moveToNext();
            }
        }
        cursor.close();

        return num;
    }

    public  static int sortUserWords(Context context,String match){
        Cursor cursor = context.getContentResolver().query(Uri.parse("content://sms/sent"), null, null, null, null);
        String line;
        String matchCheck;
        int num = 0;
        if (cursor.moveToFirst()) {
            for (int i = 0; i < cursor.getCount(); i++) {
                line = cursor.getString(cursor.getColumnIndexOrThrow("body")).toString();
                matchCheck = cursor.getString(cursor.getColumnIndexOrThrow("address"));
                if (match.equalsIgnoreCase(matchCheck)) {
                    String[] words = line.split("[^A-ZÃ…Ã„Ã–a-zÃ¥Ã¤Ã¶]+");
                    for (String word : words) {
                        if ("".equals(word)) {
                            continue;
                        }
                        num++;
                    }
                }
                cursor.moveToNext();
            }
        }
        cursor.close();

        return num;
    }

    public static Map getContactList(Context context){
        String zip = "+"+GetCountryZipCode(context);
        Cursor phones = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, null);
        Map<String, String> countMap = new HashMap<String, String>();
        while (phones.moveToNext())
        {
            String name=phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

            if(phoneNumber.startsWith("0")){
                String codeNumber = zip+phoneNumber.substring(1);
                phoneNumber = codeNumber.replaceAll("\\s","");
            }

            countMap.put(name,phoneNumber);
        }
        phones.close();

        return countMap;
    }

    public static String getContactNumber(String name ,Map<String, String> cMap) {

        Map<String, String> contactMap = cMap;
        String contactNumber;
        contactNumber = contactMap.get(name);
        if(contactNumber != null) {

            return contactNumber;
        }else{
            return name;
        }
    }

    public static String GetCountryZipCode(Context context){
        String CountryID="";
        String CountryZipCode="";

        TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        CountryID= manager.getSimCountryIso().toUpperCase();
        String[] rl=context.getResources().getStringArray(R.array.CountryCodes);
        for(int i=0;i<rl.length;i++){
            String[] g=rl[i].split(",");
            if(g[1].trim().equals(CountryID.trim())){
                CountryZipCode=g[0];
                break;
            }
        }
        return CountryZipCode;
    }


}
