package base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by szamani on 12/18/2017.
 */
public abstract class PageScraper {
    protected String country;
    protected String university;
    protected String department;
    protected List<Field> fields;
    protected String dir;
    protected Map<String, String> exception;
    //             link , exception

    public PageScraper(String country, String university, String department) {
        this.country = country;
        this.university = university;
        this.department = department;
        fields = new ArrayList<>();
        exception = new HashMap<>();
    }

    protected abstract void createJson();

    protected void saveToFile() {
        FileWriterHelper.writeListToFile(dir + country + "_" + university + "_" + department + ".txt", fields);
        FileWriterHelper.writeMapToFile(dir + country + "_" + university + "_" + department + "_exceptions.txt", exception);
    }

    protected abstract void setDir();
}
