import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.HashMap;
import javax.imageio.ImageIO;

/** <b>PoborObraz</b> odpowiedzialna za pobieranie i ładowanie obrazów */
public class PoborObraz {
    /** Kontener w do którego ładowane są pobrane obrazy*/
    public HashMap obrazy;

/** konstruktor PoborObraz, w którym tworzony jest kontener do przechowywania obrazów */
    public PoborObraz() {
        obrazy = new HashMap();
    }
    /** Pobiera ściezkę do pliku
     * @param sciezka - określa ścieżkę do pliku(obrazu)
     */
    private BufferedImage zaladuj_obraz(String sciezka) {
        URL url=null;
            try {
                url = getClass().getClassLoader().getResource(sciezka);
                return ImageIO.read(url);
            } catch (Exception e) {
            System.out.println("Przy otwieraniu " + sciezka +" jako " + url);
            System.out.println("Wystapil blad : "+e.getClass().getName()+" "+e.getMessage());
            System.exit(0);
            return null;
        }
    }
    /**Pobiera obraz i umieszcza go w Hashmap'ie
     * @param sciezka- określa ścieżke do pliku(obrazu)
         * @return o
     */
    public BufferedImage pobierz_obraz(String sciezka) {
        BufferedImage obr = (BufferedImage)obrazy.get(sciezka);
        if (obr == null) {
            obr = zaladuj_obraz("grafika/"+sciezka);
            obrazy.put(sciezka,obr);
        }
        return obr;
        }
}
