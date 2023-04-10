
//геренит точек из цсв

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.BufferedReader;
import java.io.FileOutputStream;
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

        Element KmlElement = doc.createElement("kml");
        KmlElement.setAttribute("xmlns", "http://www.opengis.net/kml/2.2");
        KmlElement.setAttribute("xmlns:gx", "hhttp://www.google.com/kml/ext/2.2");
        KmlElement.setAttribute("xmlns:kml", "http://www.opengis.net/kml/2.2");
        KmlElement.setAttribute("xmlns:atom", "http://www.w3.org/2005/Atom");
        //создаем кмл

        Element Documen = doc.createElement("Document");
        KmlElement.appendChild(Documen);

        //задаем стиль сот
        Element Style = doc.createElement("Style");
        Documen.appendChild(Style);

        //Значек
        Element StylePoint = doc.createElement("Style");
        StylePoint.setAttribute("id", "Client_icon");
        Element IconStyle = doc.createElement("IconStyle");
        Element scale = doc.createElement("scale");
        scale.appendChild(doc.createTextNode("0.5"));
        StylePoint.appendChild(scale);
        Element icon = doc.createElement("Icon");
        Element href = doc.createElement("href");
        href.appendChild(doc.createTextNode("http://maps.google.com/mapfiles/kml/shapes/target.png"));
        icon.appendChild(href);
        IconStyle.appendChild(scale);
        IconStyle.appendChild(icon);
        StylePoint.appendChild(IconStyle);
        Documen.appendChild(StylePoint);

        Element GFolderElement;
        GFolderElement = doc.createElement("Folder");
        KmlElement.appendChild(Documen);
        Documen.appendChild(GFolderElement);

        Element NameGF = doc.createElement("name");
        NameGF.appendChild(doc.createTextNode("Points"));
        GFolderElement.appendChild(NameGF);
        Element open = doc.createElement("open");
        open.appendChild(doc.createTextNode("0"));
        GFolderElement.appendChild(open);


        String sourseFileName = "Dots.csv";
        BufferedReader reader = new BufferedReader(new FileReader(sourseFileName));
        String line = null;
        String ClientName;
        int lines = 0;
        String[] stroka;
        Element Placemark;
        String lat;
        String lon;
        getDotsFromCSV(doc, GFolderElement, reader, lines);

        doc.appendChild(KmlElement);
        reader.close(); //закрываем ф-л

        new KmlSaver().save(doc, "Points");

        System.out.println("Done3");

    }

    private static void getDotsFromCSV(Document doc, Element GFolderElement, BufferedReader reader, int lines) throws IOException {
        String[] stroka;
        String line;
        String ClientName;
        String lon;
        Element Placemark;
        String lat;
        while ((line = reader.readLine()) != null)  //чтение построчно
        {
            lines = lines + 1;
            if (lines != 1)//первую строку пропускаем)
            {

                //выдераем из каждой строчки  данные
                stroka = line.split(";");

                ClientName = stroka[0];// по строчкам, пока не закончится
                lat = stroka[1];
                lon = stroka[2];


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
