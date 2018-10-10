package generator.FileLoader;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * класс для загрузки xml файлов
 */
public class XMLLoader {
    private ArrayList<column> columns;
    private int height;
    private String columnTitle = "";

    public void load(String FILENAME) {
        try {
            columns = new ArrayList<column>();
            final File xmlFile = new File(System.getProperty("user.dir")
                    + File.separator + FILENAME);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(xmlFile);
            doc.getDocumentElement().normalize();
            if (doc.hasChildNodes()) {
                loadNodes(doc.getChildNodes());
            }
        } catch (ParserConfigurationException | SAXException
                | IOException | NumberFormatException ex) {
            System.out.println("xml file problem");
        }
    }

    private void loadNodes(NodeList nodeList) {
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (Node.ELEMENT_NODE == node.getNodeType()) {
                String nodeName = node.getNodeName();
                String nodValue = node.getTextContent();
                if (node.getParentNode().getNodeName().equals("page")) {
                    if (nodeName.equals("height")) {
                        height = Integer.valueOf(nodValue);
                    }
                }
                if (node.getParentNode().getNodeName().equals("column")) {
                    if (nodeName.equals("title")) {
                        columnTitle = nodValue;
                    }
                    if (nodeName.equals("width")) {
                        columns.add(new column(columnTitle, Integer.valueOf(nodValue)));
                    }
                }
                if (node.hasChildNodes()) {
                    loadNodes(node.getChildNodes());
                }
            }
        }
    }

    public ArrayList<column> getColumns() {
        return columns;
    }

    public int getHeight() {
        return height;
    }
}
