import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

  /** 
   * <b>Elementy</b> określa klasę Elementy
   * @author Tomasz Kodzis
   */
public class Elementy {
/** Określają położenie obiektu*/
protected int x,y;
/** Określają szerokośc i wysokośc obiektu*/
protected int szerokosc, wysokosc;
/** Określa nazwę pliku (obrazu)*/
protected String nazwa_obrazu;
/** Obiekt klasy PoleGry*/
protected PoleGry pole;
/** Obiekt klasy PoborObraz*/
protected PoborObraz obraz;




/** Konstruktor umożliwia utworzenie obiektu o określonych właściwościach
 * @param stage - pole gry
 */
public Elementy(PoleGry stage) {
        this.pole = stage;
        obraz = stage.pobierzObraz();
    }


/** Rysuje obraz pobrany z plik
     * @param g- Graphics2D*/
public void paint(Graphics2D g){
g.drawImage(obraz.pobierz_obraz(nazwa_obrazu), x,y, pole );
    }
/** Zwraca krawędzie obiektu
 * @return  Rectangle*/
public Rectangle getBounds() {
return new Rectangle(x,y,szerokosc,wysokosc);
}

/** Zwraca położenie obiektu na osi
 * @return x - położenie x
 */
public int pobierzX() { return x; }
/** Ustawia położenie obiektu na osi x 
 * @param i - położenie x
 */
public void ustawX(int i) { x = i; }
/**Zwraca położenie obiektu na osi
 * @return y*/
public int pobierzY() { return y; }
/** Ustawia położenie obiektu na osi y
 * @param i - położenie y*/
public void ustawY(int i) { y = i; }
/** Zwraca nazwę obrazu
 * @return nazwa_obrazu*/
public String pobierzNazweObrazu() { return nazwa_obrazu; }
/**
 * Na podstawie podanej nazwy pliku pobiera obraz
 * oraz zapisuje jego szerokosc i wysokosć
 *@param string- określa nazwę pliku graficznego
 */
public void ustawNazweObrazu(String string) {
    nazwa_obrazu = string;
    BufferedImage image = obraz.pobierz_obraz(nazwa_obrazu);
    wysokosc = image.getHeight();
    szerokosc = image.getWidth();
    }
/** Zwraca wysokosć obiektu
 * @return wysokosc - wysokosc obiektu
 */
public int pobierzWysokosc() { return wysokosc; }
/** Zwraca szerokość obiektu
 * @return szerokosc - szaerokosc obiektu
 */
public int pobierzSzerokosc() { return szerokosc; }
/** Ustawia wysokosc obiektu 
 * @param i - wysokosc obiektu
 */
public void ustawWysokosc(int i) {wysokosc = i; }
/** Ustawia szerokość obiektu
 * @param i - szerokosc obiektu
 */
public void ustawSzerokosc(int i) { szerokosc = i; }


}

