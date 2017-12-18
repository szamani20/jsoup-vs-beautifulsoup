package base;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * Created by szamani on 12/18/2017.
 */
public abstract class SinglePageScraper extends PageScraper {
    protected String url;
    protected Document siteText;

    public SinglePageScraper(String country, String university, String department,
                             String url) {
        super(country, university, department);
        this.url = url;

        try {
            setSiteText();
        } catch (IOException e) {
            e.printStackTrace();
        }

        setDir();
        createJson();
        saveToFile();
    }

    protected void setSiteText() throws IOException {
        siteText = Jsoup.connect(url).get();
    }
}
