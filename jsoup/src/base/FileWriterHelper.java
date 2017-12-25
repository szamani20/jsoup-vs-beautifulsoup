package base;

import com.google.gson.Gson;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by szamani on 12/18/2017.
 */
public class FileWriterHelper {
    public static void writeListToFile(String fileName,
                                       List<Field> fields) {
        FileWriter fw = null;
        Gson gson = new Gson();
        StringBuilder content = new StringBuilder();
        content.append(gson.toJson(fields));

        try {
            fw = new FileWriter(fileName);
            fw.write(content.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fw != null) {
                    fw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void writeMapToFile(String fileName,
                                      Map<String, String> exceptions) {
        FileWriter fw = null;
        StringBuilder content = new StringBuilder();

        for (String link:exceptions.keySet())
            content.append(link)
                    .append(": ")
                    .append(exceptions.get(link))
                    .append("\n");

        try {
            fw = new FileWriter(fileName);
            fw.write(content.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fw != null) {
                    fw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
