package single_page.mcgill;

import base.Field;
import base.SinglePageScraper;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class Mcgill extends SinglePageScraper {

    public Mcgill(String country, String university, String department, String url) {
        super(country, university, department, url);
    }

    public static void main(String[] args) {
        Long t1 = System.currentTimeMillis();

        String country = "Ca";
        String university = "mcgill";
        String department = "CS";
        String url = "http://www.cs.mcgill.ca/people/faculty/faculty";
        new Mcgill(country, university, department, url);

        System.out.println("Time: " + (System.currentTimeMillis() - t1));
    }

    @Override
    protected void createJson() {
        Elements tableRows = document.select("div.col-md-6");
        for (Element row : tableRows) {
            try {
                String name = row.select("a.list-group-item h4").text();

                String email = row.select("div.panel-collapse a:last-of-type").text();
                email = email.substring(email.indexOf("Email") + 7);

                String link = row.select("div.panel-collapse a:first-of-type").attr("href");

                Elements research = row.select("a.list-group-item p");
                List<String> researchInterests = new ArrayList<>();
                for (Element research_interest : research)
                    researchInterests.add(research_interest.text().trim());

                Field field = new Field(name, email, link, researchInterests);
                fields.add(field);
            } catch (Exception e) {
                exception.put(document.title(), e.getMessage());
            }
        }
    }

    @Override
    protected void setDir() {
        dir = "src/single_page/mcgill/";
    }
}
