package teamupapps.com.smswordcompare;

import android.util.Log;

import java.util.Map;

/**
 * Created by clazell on 30/10/2014.
 */
public class Thread {

    private String contactName;
    private SMS[] content;
    private int totalWordcount;


    public Thread(String contactName, SMS[] content,int totalWordcount) {
        this.totalWordcount = totalWordcount;
        this.contactName = contactName;
        this.content = content;

    }

    public int getTotalWordcount(){
        return totalWordcount;
    }

    public SMS[] getThreadSMS() {
        return content;
    }

    public String getName() {
        return contactName;
    }

    public int getWordCountfromDate(String dateToSearch){
        int dateSearchNum = 0;

        for(SMS sms :content){
            if(sms != null) {
                if (Utils.getDateWithoutTime(Utils.millisToDate(sms.getDate())).equals(dateToSearch)) {
                    dateSearchNum = sms.getWordCount() + dateSearchNum;
                }
            }
        }


        return dateSearchNum;
    }

    public int getWordCountfromTwoDates(){

        return 1;
    }

}
