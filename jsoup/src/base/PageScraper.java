package base;

import java.util.*;

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
        FileWriterHelper.writeListToFile(dir + country + "_" + university + "_" + department + ".html", fields);
        FileWriterHelper.writeMapToFile(dir + country + "_" + university + "_" + department + "_exceptions.txt", exception);
    }

    protected List<String> arrayToStringWithTrim(String[] array) {
        return Arrays.asList(Arrays.stream(array).map(String::trim).toArray(unused -> array));
    }

    protected abstract void setDir();
}
