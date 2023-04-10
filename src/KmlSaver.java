import org.w3c.dom.Document;

import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileOutputStream;
import java.io.IOException;

public class KmlSaver {
    public void save (Document doc, String fileName) throws TransformerException, IOException {
        //сохраняем кмл
        Transformer t= TransformerFactory.newInstance().newTransformer();
        t.setOutputProperty(OutputKeys.METHOD, "xml");
        t.setOutputProperty(OutputKeys.INDENT, "yes");
        FileOutputStream outs = new FileOutputStream(fileName+".kml");

        t.transform(new DOMSource(doc), new StreamResult(outs));



        outs.close();
    }
}
