
//геренит видеотур

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

public class Tour {


    static DocumentBuilder builder;
    public static void main(String[] args) throws IOException, TransformerFactoryConfigurationError, TransformerException {



        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try { builder = factory.newDocumentBuilder(); }
        catch (ParserConfigurationException e) { e.printStackTrace(); }


        Document doc=builder.newDocument();

        Element KmlElement=doc.createElement("kml");
        KmlElement.setAttribute("xmlns", "http://www.opengis.net/kml/2.2");
        KmlElement.setAttribute("xmlns:gx", "hhttp://www.google.com/kml/ext/2.2");
        KmlElement.setAttribute("xmlns:kml", "http://www.opengis.net/kml/2.2");
        KmlElement.setAttribute("xmlns:atom", "http://www.w3.org/2005/Atom");
        //создаем кмл

        Element FlyTo;
        Element TourElement;
        Element LookAt;
        Element GFolderElement;
        Element Playlist;
        TourElement=doc.createElement("gx:Tour");
        KmlElement.appendChild(TourElement);
        Element NameGF=doc.createElement("name");
        NameGF.appendChild(doc.createTextNode( "VideoTour"));
        TourElement.appendChild(NameGF);

        Playlist=doc.createElement("gx:Playlist");
        TourElement.appendChild(Playlist);


        String sourseFileName = "Dots.csv";
        BufferedReader reader = new BufferedReader (new FileReader(sourseFileName));
        String line = null;
       int lines=0;
        String[] stroka;
        String lat;
        String lon;
        while ( (line = reader.readLine()) != null )  //чтение построчно
        {
            lines=lines+1;
            if  (lines != 1)//первую строку пропускаем)
            {

                //выдераем из каждой строчки  данные
                stroka = line.split(";");


                lat=stroka[1];
                lon=stroka[2];


                FlyTo=doc.createElement("gx:FlyTo");
                Playlist.appendChild(FlyTo);
                //	имя
                Element duration2 = doc.createElement("gx:duration");
                duration2.appendChild(doc.createTextNode("2"));
                FlyTo.appendChild(duration2);
                Element flyToMode = doc.createElement("gx:flyToMode");
                flyToMode.appendChild(doc.createTextNode("smooth"));
                FlyTo.appendChild(flyToMode);
                LookAt=doc.createElement("LookAt");
                FlyTo.appendChild(LookAt);


                Element coordinatesDisc = doc.createElement("longitude");
                coordinatesDisc.appendChild(doc.createTextNode(lon));
                Element coordinatesDisc2 = doc.createElement("latitude");
                coordinatesDisc2.appendChild(doc.createTextNode(lat));
                Element range = doc.createElement("range");
                range.appendChild(doc.createTextNode("600"));

                LookAt.appendChild(coordinatesDisc);
                LookAt.appendChild(coordinatesDisc2);
                LookAt.appendChild(range);
                Element Wait = doc.createElement("gx:Wait");
                Element duration = doc.createElement("gx:duration");
                duration.appendChild(doc.createTextNode( "3"));
                Wait.appendChild(duration);
                Playlist.appendChild(Wait);
            }




        } //конец списка

        TourElement.appendChild(Playlist);
        doc.appendChild(KmlElement);


//сохраняем кмл
        Transformer t= TransformerFactory.newInstance().newTransformer();
        t.setOutputProperty(OutputKeys.METHOD, "xml");
        t.setOutputProperty(OutputKeys.INDENT, "yes");
        FileOutputStream outs = new FileOutputStream("Tour.kml");
        t.transform(new DOMSource(doc), new StreamResult(outs));

        outs.close();
        reader.close(); //закрываем ф-л
        System.out.println ("Done1");

    }}
