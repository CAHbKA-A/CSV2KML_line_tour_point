
//геренит слой клиентов из цсв


import javax.xml.transform.TransformerException;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, TransformerException {
    Tour line = new Tour();
    line.main(args);
    Track track = new Track();
    track.main(args);
    Points points = new Points();
    points.main(args);
    }
}
