package generator.FileLoader;

import java.io.*;
import java.util.ArrayList;

/**
 * класс для загрузки tsv файла
 */
public class TSVLoader {
    private ArrayList<String[]> TSVList;

    public void load(String FILENAME) {
        TSVList = new ArrayList<String[]>();
        try {
            final BufferedReader br = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(FILENAME), "UTF-16"));
            String nextString;
            while ((nextString = br.readLine()) != null) {
                String[] values = nextString.split("\t");
                TSVList.add(values);
            }
            br.close();
        } catch (UnsupportedEncodingException ex) {
            System.out.println("encoding problem");
        } catch (IOException ex) {
            System.out.println("tsv file problem");
        }
    }

    public ArrayList<String[]> getTSVList() {
        return TSVList;
    }
}
