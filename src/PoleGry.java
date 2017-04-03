import java.awt.image.ImageObserver;

  /** 
   * <b>PoleGry</b> określa interface PoleGry przechowujący właściwości okna gry, rozszerzający klasę ImageObserver
   * @author Tomasz Kodzis
   */
public interface PoleGry extends ImageObserver {
        /**Okreslająca szerokość okna gry*/
    public static final int SZEROKOSC = 1000;
    /**Okreslająca wysokosc okna gry*/
    public static final int WYSOKOSC = 700;
    /**Okreslająca opóznienie wykonywania funkcji,pętli itp. */
    public static final int OPOZNIENIE = 5;
    /**Okreslająca wysokość pola gry*/
    public static final int WYSOKOSC_GRY = 650;
    /**Deklaracja funkcji pobierającej obraz
     * @return obr
     */
    public PoborObraz pobierzObraz();

}
