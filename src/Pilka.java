
import java.awt.event.MouseEvent;

  /** 
   * <b>Pilka</b> określa klasę Pilka rozszerzająca klasę Elementy
   * @author Tomasz Kodzis
   */
public class Pilka extends Elementy {
/** Określa prędkość ruchu piłki po osi x oraz y*/
protected double vx, vy;
/** Przechowują położenie kursora myszy(x,y) w momencie strzału*/
protected int Cx,Cy;
/** Współczynniki równania prostej po której porusza się piłka*/
private int a,b;
/** Przechowuje liczbę zdobytych bramek*/
protected int Wynik;
//double mAng;

/** Konstruktor umożliwia utworzenie obiektu o określonych właściwościach
* @param stage
*/
public Pilka(PoleGry stage) {
    super(stage);
    ustawNazweObrazu("pilka2.png" );
    }
/**Opisuje ruch piłki
 * @param px 
 * @param py 
 */
public void ruch(int px, int py)  {
    vx=1;
    vy= 1;
//mAng = Math.toDegrees(Math.atan2(py - y, px - x));

    a= (Cy-this.pobierzY())/(Cx-this.pobierzX());
    b= this.pobierzY()-(this.pobierzX()*a);

    while(x!=px || y!=py){
        if(x<px){ x+=vx;}
        if(x>px){ x-=vx;} 
        if(y>py){ y-=vy;}
/*if(x<px){ x+=vx;

    y-=y- ((a*x)+b);
}
if(x>px){x-=vx;

    y-=y- ((a*x)+b);
}*/
     
    try {
    Thread.sleep(PoleGry.OPOZNIENIE);
    } catch (InterruptedException e) {}
    }

}
/** Funkcja zwracająca wartość przechowywaną
 * przez zmienną Wynik 
 * @return Wynik
 */
public int pobierz_wynik() { return Wynik; }
/**Ustawia wartość zmiennej Wynik
 * na wartośc podana jako parametr 
 * @param i
 */
public void ustaw_wynik(int i) { Wynik = i; }

/** Dodaje punkty
 * @param i - wartość o jaką zwiększany jest Wynik
 */
public void dodaj_punkt(int i) { Wynik += i; }

/** Pobiera położenie kursora(x,y) kiedy wciśnięty jest klawisz myszy
 * @param e - MouseEvent
 */
public void moussePressed(MouseEvent e) {
    Cx= e.getX();
    Cy= e.getY();
    }


}