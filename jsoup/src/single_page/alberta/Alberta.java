package single_page.alberta;

import base.Field;
import base.SinglePageScraper;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Arrays;
import java.util.List;

public class Alberta extends SinglePageScraper {

    public Alberta(String country, String university, String department, String url) {
        super(country, university, department, url);
    }

    public static void main(String[] args) {
        Long t1 = System.currentTimeMillis();

        String country = "Ca";
        String university = "alberta";
        String department = "CS";
        String url = "https://www.ualberta.ca/computing-science/faculty-and-staff/faculty";
        new Alberta(country, university, department, url);

        System.out.println("Time: " + (System.currentTimeMillis() - t1));
    }

    @Override
    protected void createJson() {
        Elements tableRows = document.select("table tbody tr");
        for (Element row : tableRows) {
            try {
                String name = row.select("a:first-child").text();

                String email = row.select("td:last-child a").attr("href");

                String link = row.select("a:first-child").attr("href");

                List<String> researchInterests = arrayToStringWithTrim(row.select("td:nth-last-child(2)").text().split(","));

                Field field = new Field(name, email, link, researchInterests);
                fields.add(field);
            } catch (Exception e) {
                exception.put(document.title(), e.getMessage());
            }
        }
    }

    @Override
    protected void setDir() {
        dir = "src/single_page/alberta/";
    }
}
