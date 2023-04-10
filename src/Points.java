
//геренит точек из цсв

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Points {


    static DocumentBuilder builder;

    public static void main(String[] args) throws IOException, TransformerFactoryConfigurationError, TransformerException {


        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }


        Document doc = builder.newDocument();

        Element kmlElement = doc.createElement("kml");
        kmlElement.setAttribute("xmlns", "http://www.opengis.net/kml/2.2");
        kmlElement.setAttribute("xmlns:gx", "hhttp://www.google.com/kml/ext/2.2");
        kmlElement.setAttribute("xmlns:kml", "http://www.opengis.net/kml/2.2");
        kmlElement.setAttribute("xmlns:atom", "http://www.w3.org/2005/Atom");
        //создаем кмл

        Element document = doc.createElement("Document");
        kmlElement.appendChild(document);

        //задаем стиль сот
        Element Style = doc.createElement("Style");
        document.appendChild(Style);

        //Значек
        Element stylePoint = doc.createElement("Style");
        stylePoint.setAttribute("id", "Client_icon");
        Element iconStyle = doc.createElement("IconStyle");
        Element scale = doc.createElement("scale");
        scale.appendChild(doc.createTextNode("0.5"));
        stylePoint.appendChild(scale);
        Element icon = doc.createElement("Icon");
        Element href = doc.createElement("href");
        href.appendChild(doc.createTextNode("http://maps.google.com/mapfiles/kml/shapes/target.png"));
        icon.appendChild(href);
        iconStyle.appendChild(scale);
        iconStyle.appendChild(icon);
        stylePoint.appendChild(iconStyle);
        document.appendChild(stylePoint);

        Element gFolderElement;
        gFolderElement = doc.createElement("Folder");
        kmlElement.appendChild(document);
        document.appendChild(gFolderElement);

        Element nameGF = doc.createElement("name");
        nameGF.appendChild(doc.createTextNode("Points"));
        gFolderElement.appendChild(nameGF);
        Element open = doc.createElement("open");
        open.appendChild(doc.createTextNode("0"));
        gFolderElement.appendChild(open);


        String sourceFileName = "Dots.csv";
        BufferedReader reader = new BufferedReader(new FileReader(sourceFileName));
        int lines = 0;

        getDotsFromCSV(doc, gFolderElement, reader, lines);

        doc.appendChild(kmlElement);
        reader.close(); //закрываем ф-л

        new KmlSaver().save(doc, "Points");

        System.out.println("Done3");

    }

    private static void getDotsFromCSV(Document doc, Element GFolderElement, BufferedReader reader, int lines) throws IOException {
        String[] StringLine;
        String line;
        String ClientName;
        String lon;
        String lat;
        while ((line = reader.readLine()) != null)  //чтение построчно
        {
            lines = lines + 1;
            if (lines != 1)//первую строку пропускаем)
            {

                //выдераем из каждой строчки  данные
                StringLine = line.split(";");

                ClientName = StringLine[0];// по строчкам, пока не закончится
                lat = StringLine[1];
                lon = StringLine[2];


                dotsToXML(doc, GFolderElement, ClientName, lon, lat);

            }


        } //конец списка
    }

    private static void dotsToXML(Document doc, Element GFolderElement, String ClientName, String lon, String lat) {
        Element Placemark;
        Placemark = doc.createElement("Placemark");
        Element PlacemarkName = doc.createElement("name");
        PlacemarkName.appendChild(doc.createTextNode(ClientName));
        Placemark.appendChild(PlacemarkName);
        GFolderElement.appendChild(Placemark);

        //применяем стиль для клиента
        Element styleUrl = doc.createElement("styleUrl");
        styleUrl.appendChild(doc.createTextNode("Client_icon"));
        Placemark.appendChild(styleUrl);
        Element Point = doc.createElement("Point");
        Element coordinates = doc.createElement("coordinates");
        coordinates.appendChild(doc.createTextNode(lon + "," + lat + ",0"));
        Point.appendChild(coordinates);
        Placemark.appendChild(Point);
    }
}
