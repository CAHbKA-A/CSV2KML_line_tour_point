
//геренит слой клиентов из цсв

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

public class Track {


    static DocumentBuilder builder;
    public static void main(String[] args) throws IOException, TransformerFactoryConfigurationError, TransformerException {



        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try { builder = factory.newDocumentBuilder(); }
        catch (ParserConfigurationException e) { e.printStackTrace(); }


        Document doc=builder.newDocument();

        Element KmlElement=doc.createElement("kml");
        //создаем кмл


        Element Document;
        Element TourElement;
        Element LookAt;
        Element Placemark;
        Element Playlist;
        Document=doc.createElement("Document");
        KmlElement.appendChild(Document);
        Element NameGF=doc.createElement("name");
        NameGF.appendChild(doc.createTextNode( "Трек"));
        Document.appendChild(NameGF);

        //Стиль

        Element Style=doc.createElement("Style");
        Document.appendChild(Style);
        Style.setAttribute("id", "s_ylw-pushpin");
        Element PolyStyle=doc.createElement("LineStyle");
        Style.appendChild(PolyStyle);
        //цвет
        Element Color=doc.createElement("color");
        Color.appendChild(doc.createTextNode("ff0000aa"));
        PolyStyle.appendChild(Color);
        Element width =doc.createElement("width");
        width.appendChild(doc.createTextNode("3"));
        PolyStyle.appendChild(width);

        Element StyleMap=doc.createElement("StyleMap");
        StyleMap.setAttribute("id", "m_ylw-pushpin");
       // StyleMap.appendChild(doc.createTextNode("#s_ylw-pushpin"));
        Document.appendChild(StyleMap);
        Element Pair=doc.createElement("Pair");
        StyleMap.appendChild(Pair);
        Element styleUrl=doc.createElement("styleUrl");
        styleUrl.appendChild(doc.createTextNode("#s_ylw-pushpin"));
        Pair.appendChild(styleUrl);



//рисуем линию

        Placemark=doc.createElement("Placemark");
        Document.appendChild(Placemark);
//применяем стиль
        Element StyleSet =doc.createElement("styleUrl");
        StyleSet.appendChild(doc.createTextNode("#m_ylw-pushpin"));
        Placemark.appendChild(StyleSet);
//сама линия
        Element LineString =doc.createElement("LineString");
        Placemark.appendChild(LineString);
        Element tessellate =doc.createElement("tessellate");
        tessellate.appendChild(doc.createTextNode( "1"));
        LineString.appendChild(tessellate);
        Element   coordinates=doc.createElement("coordinates");


        String sourseFileName = "Dots.csv";
        BufferedReader reader = new BufferedReader (new FileReader(sourseFileName));
        String line = null;
        String addres_client,ClientName;
        int lines=0;
        String[] stroka;
        String lomanaya = "";
        while ( (line = reader.readLine()) != null )  //чтение построчно
        {
            lines=lines+1;
            if  (lines != 1)//первую строку пропускаем)
            {

                //выдераем из каждой строчки  данные
                stroka = line.split(";");





                String lat;
                String lon;

                //lat=(float) 52.259309; lon = (float) 104.285561;// (на случай если  в не определятся коор-ты)
               // lat="52.259309"; lon = "104.285561" ;// (на случай если  в не определятся коор-ты)
                addres_client   = stroka[1];

                ClientName =stroka[0];// по строчкам, пока не закончится
                lat=stroka[1];
                lon=stroka[2];
                lomanaya = lomanaya +" " +lon +","+lat+",0";

            }




        } //конец списка клиентов
        LineString.appendChild(coordinates);
        coordinates.appendChild(doc.createTextNode( lomanaya));
       // TourElement.appendChild(Playlist);

        doc.appendChild(KmlElement);





//сохраняем кмл
        Transformer t= TransformerFactory.newInstance().newTransformer();
        t.setOutputProperty(OutputKeys.METHOD, "xml");
        t.setOutputProperty(OutputKeys.INDENT, "yes");
         FileOutputStream outs = new FileOutputStream("line.kml");
         t.transform(new DOMSource(doc), new StreamResult(outs));



        outs.close();
        reader.close(); //закрываем ф-л
        System.out.println ("Done2");

    }}
