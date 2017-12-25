package base;

import java.util.List;

/**
 * Created by szamani on 12/17/2017.
 */
public class Field {
    String name;
    String email;
    String link;
    List<String> research;

    public Field(String name, String email, String link, List<String> research) {
        this.name = name;
        this.email = email;
        this.link = link;
        this.research = research;
    }

    @Override
    public String toString() {
        if (research.size() > 0)
        return name + " " + email + " " + link + " " + research.get(0);
        else return name + " " + email + " " + link + " , No research";
    }
}
