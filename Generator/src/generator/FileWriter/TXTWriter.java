package generator.FileWriter;

import generator.FileLoader.XMLLoader;

import java.io.*;
import java.util.ArrayList;

/**
 * класс для работы с текстовым файлом
 */
public class TXTWriter {
    private ArrayList<String[]> TSVList;
    private XMLLoader settings;

    public void write(String FILENAME) {
        try {
            Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(FILENAME), "UTF-16"));
            OutputGenerator outputGenerator = new OutputGenerator(TSVList, settings);
            ArrayList<String> result = outputGenerator.buildDocument();
            for (String line : result) {
                writer.append(line);
            }
            writer.flush();
            writer.close();
        } catch (IOException ex) {
            System.out.println("write into file problem");
        }
    }

    public void setTSVList(ArrayList<String[]> TSVList) {
        this.TSVList = TSVList;
    }

    public void setSettings(XMLLoader settings) {
        this.settings = settings;
    }
}

