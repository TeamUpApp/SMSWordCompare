package teamupapps.com.smswordcompare;

/**
 * Created by clazell on 30/10/2014.
 */
public class SMS {

    private String name;
    private String content;
    int wordCount;
    private Long date;

    public SMS(String name, String content, Long date, int wordCount) {
        this.wordCount = wordCount;
        this.name = name;
        this.content = content;
        this.date = date;
    }

    public int getWordCount(){
        return  wordCount;
    }

    public String getContent() {
        return content;
    }

    public String getName() {
        return name;
    }

    public Long getDate() {
        return date;
    }
}
