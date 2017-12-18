package multi_page.calgary;

import base.Field;
import base.MultiPageScraper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by szamani on 12/18/2017.
 */
public class Calgary extends MultiPageScraper {

    public Calgary(String country, String university, String department, List<String> urls) {
        super(country, university, department, urls);
    }

    @Override
    protected void createJson() {
        for (Document document : documents) {
            try {
                String name = document.select("div.wrapper h1").text() +
                        document.select("div#unitis-profile-block-roles-positions-list li").text();

                String link = document.select("div.unitis-website-list a").attr("href");

                String research = document.select("div#unitis-profile-block-profileblock_401 p").toString();
                if (research == null || research.length() == 0)
                    research = document.select("div#unitis-profile-block-profileblock_0 p").toString();
                research = research.replace("<p>","");
                research = research.replace("</p>", "");
                List<String> researchInterests = Arrays.asList(research.split("<br>"));

                String email = document.select("div.unitis-email-list a").attr("href");

                Field field = new Field(name, email, link, researchInterests);
                fields.add(field);
            } catch (Exception e) {
                exception.put(document.title(), e.getMessage());
            }
        }
    }

    @Override
    protected void setDir() {
        dir = "src\\multi_page\\calgary\\";
    }

    public static void main(String[] args) {
        Long t = System.currentTimeMillis();
        String country = "Ca";
        String university = "calgary";
        String department = "CS";
        String facultyPageUrl = "http://www.ucalgary.ca/cpsc_info/contact-us";
        String uniBaseUrl = "http://www.ucalgary.ca";
        List<String> urls = extractFacultyUrls(facultyPageUrl, uniBaseUrl);
        new Calgary(country, university, department, urls);
        System.out.println(System.currentTimeMillis() - t);
    }

    private static List<String> extractFacultyUrls(String facultyPageUrl, String uniBaseUrl) {
        List<String> urls = new ArrayList<>();
        try {
            Document soup = Jsoup.connect(facultyPageUrl).get();
            Elements links = soup.select("table#uofc-table-7 tr td:first-child a");

            for (Element link : links)
                urls.add(uniBaseUrl + link.attr("href"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return urls;
    }
}
