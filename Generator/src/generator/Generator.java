package generator;

import generator.FileLoader.TSVLoader;
import generator.FileLoader.XMLLoader;
import generator.FileWriter.TXTWriter;

public class Generator {

    public static void main(String[] args) {
        try {
            String XMLFILENAME = args[0];
            String TSVFILENAME = args[1];
            String TXTFILENAME = args[2];
            XMLLoader xmlLoader = new XMLLoader();
            xmlLoader.load(XMLFILENAME);
            TSVLoader tsvLoader = new TSVLoader();
            tsvLoader.load(TSVFILENAME);
            TXTWriter txtWriter = new TXTWriter();
            txtWriter.setTSVList(tsvLoader.getTSVList());
            txtWriter.setSettings(xmlLoader);
            txtWriter.write(TXTFILENAME);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Errors in files");
        }
    }
}
