
//геренит видеотур

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Tour {


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

        Element tourElement;
        Element Playlist;
        tourElement = doc.createElement("gx:Tour");
        kmlElement.appendChild(tourElement);
        Element nameGF = doc.createElement("name");
        nameGF.appendChild(doc.createTextNode("VideoTour"));
        tourElement.appendChild(nameGF);

        Playlist = doc.createElement("gx:Playlist");
        tourElement.appendChild(Playlist);


        String sourseFileName = "Dots.csv";
        BufferedReader reader = new BufferedReader(new FileReader(sourseFileName));
        String line = null;
        int lines = 0;

        getPoinsFromCSV(doc, Playlist, reader, lines);

        tourElement.appendChild(Playlist);
        doc.appendChild(kmlElement);
        reader.close(); //закрываем ф-л

//сохраняем кмл
        new KmlSaver().save(doc, "Tour");

        System.out.println("Done1");

    }

    private static void getPoinsFromCSV(Document doc, Element Playlist, BufferedReader reader, int lines) throws IOException {
        String lat;
        String[] stroka;
        String lon;
        String line;

        while ((line = reader.readLine()) != null)  //чтение построчно
        {
            lines = lines + 1;
            if (lines != 1)//первую строку пропускаем)
            {

                //выдераем из каждой строчки  данные
                stroka = line.split(";");


                lat = stroka[1];
                lon = stroka[2];


                dotToKml(doc, Playlist, lat, lon);
            }


        } //конец списка
    }

    private static void dotToKml(Document doc, Element Playlist, String lat, String lon) {
        Element lookAt;
        Element flyTo;
        flyTo = doc.createElement("gx:FlyTo");
        Playlist.appendChild(flyTo);
        //	имя
        Element duration2 = doc.createElement("gx:duration");
        duration2.appendChild(doc.createTextNode("2"));
        flyTo.appendChild(duration2);
        Element flyToMode = doc.createElement("gx:flyToMode");
        flyToMode.appendChild(doc.createTextNode("smooth"));
        flyTo.appendChild(flyToMode);
        lookAt = doc.createElement("LookAt");
        flyTo.appendChild(lookAt);


        Element coordinatesDisc = doc.createElement("longitude");
        coordinatesDisc.appendChild(doc.createTextNode(lon));
        Element coordinatesDisc2 = doc.createElement("latitude");
        coordinatesDisc2.appendChild(doc.createTextNode(lat));
        Element range = doc.createElement("range");
        range.appendChild(doc.createTextNode("600"));

        lookAt.appendChild(coordinatesDisc);
        lookAt.appendChild(coordinatesDisc2);
        lookAt.appendChild(range);
        Element Wait = doc.createElement("gx:Wait");
        Element duration = doc.createElement("gx:duration");
        duration.appendChild(doc.createTextNode("3"));
        Wait.appendChild(duration);
        Playlist.appendChild(Wait);
    }
}
