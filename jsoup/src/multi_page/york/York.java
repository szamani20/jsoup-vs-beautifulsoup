package multi_page.york;

import base.Field;
import base.MultiPageScraper;
import multi_page.calgary.Calgary;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class York extends MultiPageScraper {

    public York(String country, String university, String department, List<String> urls) {
        super(country, university, department, urls);
    }

    @Override
    protected void createJson() {
        for (Document document : documents) {
            try {
                String name = document.select("header#title-area h1").text() +
                        document.select("header#title-area p").text();

                String link = document.select("div.staff-information ul li:nth-last-of-type(1) a").attr("href");

                Elements research = document.select("div.staff-content ul:nth-of-type(1) li");
                List<String> researchInterests = new ArrayList<>();
                for (Element research_interest : research)
                    researchInterests.add(research_interest.text().trim());

                String email = document.select("div.staff-information ul li:nth-last-of-type(2) a").attr("href");

                Field field = new Field(name, email, link, researchInterests);
                fields.add(field);
            } catch (Exception e) {
                exception.put(document.title(), e.getMessage());
            }
        }
    }

    @Override
    protected void setDir() {
        dir = "src/multi_page/york/";
    }

    public static void main(String[] args) {
        Long t1 = System.currentTimeMillis();

        String country = "Ca";
        String university = "York";
        String department = "CS";
        String facultyPageUrl = "http://eecs.lassonde.yorku.ca/community/faculty-members/page/";
        String uniBaseUrl = "";
        List<String> urls = extractFacultyUrls(facultyPageUrl, uniBaseUrl);
        new York(country, university, department, urls);

        System.out.println("Time: " + (System.currentTimeMillis() - t1));
    }

    private static List<String> extractFacultyUrls(String facultyPageUrl, String uniBaseUrl) {
        List<String> urls = new ArrayList<>();

        for (int i = 1; i < 10; i++) {

            try {
                Document soup = Jsoup.connect(facultyPageUrl + String.valueOf(i)).get();
                Elements links = soup.select("ul li.entry-item div.entry-text-wrap a");

                for (Element link : links)
                    urls.add(uniBaseUrl + link.attr("href"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return urls;
    }
}
