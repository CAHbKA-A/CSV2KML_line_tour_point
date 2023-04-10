
//геренит линию  из цсв

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

public class Track {

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
        //создаем кмл
        Element Document;
        Element Placemark;
        Document = doc.createElement("Document");
        KmlElement.appendChild(Document);
        Element NameGF = doc.createElement("name");
        NameGF.appendChild(doc.createTextNode("Track_line"));
        Document.appendChild(NameGF);

        //Стиль

        Element Style = doc.createElement("Style");
        Document.appendChild(Style);
        Style.setAttribute("id", "s_ylw-pushpin");
        Element PolyStyle = doc.createElement("LineStyle");
        Style.appendChild(PolyStyle);
        //цвет
        Element Color = doc.createElement("color");
        Color.appendChild(doc.createTextNode("ff0000aa"));
        PolyStyle.appendChild(Color);
        Element width = doc.createElement("width");
        width.appendChild(doc.createTextNode("3"));
        PolyStyle.appendChild(width);

        Element StyleMap = doc.createElement("StyleMap");
        StyleMap.setAttribute("id", "m_ylw-pushpin");
        Document.appendChild(StyleMap);
        Element Pair = doc.createElement("Pair");
        StyleMap.appendChild(Pair);
        Element styleUrl = doc.createElement("styleUrl");
        styleUrl.appendChild(doc.createTextNode("#s_ylw-pushpin"));
        Pair.appendChild(styleUrl);

        //рисуем линию

        Placemark = doc.createElement("Placemark");
        Document.appendChild(Placemark);
        //применяем стиль
        Element styleSet = doc.createElement("styleUrl");
        styleSet.appendChild(doc.createTextNode("#m_ylw-pushpin"));
        Placemark.appendChild(styleSet);
        //сама линия
        Element lineString = doc.createElement("LineString");
        Placemark.appendChild(lineString);
        Element tessellate = doc.createElement("tessellate");
        tessellate.appendChild(doc.createTextNode("1"));
        lineString.appendChild(tessellate);
        Element coordinates = doc.createElement("coordinates");


        String sourceFileName = "Dots.csv";
        BufferedReader reader = new BufferedReader(new FileReader(sourceFileName));
        int lines = 0;
        String lomanaya = "";
        lomanaya = getPointsFromCSV(reader, lines, lomanaya);
        lineString.appendChild(coordinates);
        coordinates.appendChild(doc.createTextNode(lomanaya));

        doc.appendChild(KmlElement);

        reader.close(); //закрываем ф-л
//сохраняем кмл
        new KmlSaver().save(doc, "line");

        System.out.println("Done2");
    }

    private static String getPointsFromCSV(BufferedReader reader, int lines, String lomanaya) throws IOException {
        String[] stroka;
        String line;
        String lat;
        String lon;
        while ((line = reader.readLine()) != null)  //чтение построчно
        {
            lines = lines + 1;
            if (lines != 1)//первую строку пропускаем)
            {

                //выдераем из каждой строчки  данные
                stroka = line.split(";");
                lat = stroka[1];
                lon = stroka[2];
                lomanaya = lomanaya + " " + lon + "," + lat + ",0";

            }


        } //конец списка
        return lomanaya;
    }
}
