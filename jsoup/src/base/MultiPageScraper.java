package base;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by szamani on 12/18/2017.
 */
public abstract class MultiPageScraper extends PageScraper {
    protected List<String> urls;  // URLs or each faculty
    protected List<Document> documents;

    public MultiPageScraper(String country, String university, String department,
                            List<String> urls) {
        super(country, university, department);
        this.urls = urls;
        documents = new ArrayList<>();

        try {
            setSiteTexts();
        } catch (IOException e) {
            e.printStackTrace();
        }

        setDir();
        createJson();
        saveToFile();
    }


    protected void setSiteTexts() throws IOException {
        for (String url : urls)
            documents.add(Jsoup.connect(url).get());
    }
}
