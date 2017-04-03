import java.awt.Canvas;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import sun.audio.AudioData;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import sun.audio.ContinuousAudioDataStream;

  /** 
   * <b>Pilkarz</b> określa główną klasę Pilkarz rozszerzająca klasę Canvas oraz implemetującą interface PoleGry
   * @author Tomasz Kodzis
   */
public class Pilkarz extends Canvas implements PoleGry{
/**Określa czas rozpoczęcia gry*/
public double czas_start; 
/**Określa aktualny czas gry*/
public double czas_gry;
/**Przechowuje czas pojedynczego dmuchnięcia */
public double czas_dmuch;
/**Określa czas rozpoczęcia dmuchnięcia*/
public double start_dmuch;
/**Przechowuje sumaryczny czas dmuchania*/
public double calk_czas_dmuch;
/**Przechowuje końcowy czas gry*/
public double czas_koncowy;
/**Obiekt klasy BufferStrategy*/
public BufferStrategy strategia;
/**Obiekt klasy PoborObraz */
private final PoborObraz obraz;
/**Obiekt klasy Pilka*/
private Pilka pilka;
/**Obiekt klasy Bramka*/
private Bramka bramka;
/**Obiekt klasy Bramkarz*/
private Bramkarz bramkarz;
/** Obiekt klasy Cel*/
private Cel cel;
/* Określają położenie (x,y) celu */
protected int xcel, ycel;
/** Określają połozenie (x,y) bramkarza*/
protected int xbram, ybram;
/** Obiekt klasy Random, służacy do losowania położenia celu*/
protected Random r = new Random();
/** Obiekt klasy Random, służacy do losowania położenia bramkarza*/
protected Random rb = new Random();
/** Zmienna pomocnicza*/
protected int q;
/** Obiekt klasy BufferedImage*/
private BufferedImage tlo; //private
/** Obiekt klasy Login*/
public Login log;
/** przechowuje aktualną datę */
public String data;
/** przechowuje imię użytkownika */
public String imie;
/** Określa długość rysowanego paska siły*/
public int z;
/** Kontener; okno klasy Pilkarz*/
public JFrame okno;
/**Określa status przycisku myszy
 * true - wciśnięty
 * false - wyciśnięty
 */
private boolean wcisniety= false;
/** Określa aktualny poziom gry*/
public int poziom=1;
/** Określa szybkosc ładowania paska siły, a jednocześnie wymagany czas dmuchnięcia*/
protected int czas_d;
/** Określa liczbe wymaganych do zdobycie bramek na danym poziomie*/
protected int l_bramek; 
/** Przechowuje sumę bramek w grze*/
protected int suma_bramki;
/** Obiekty klasy Rectangle2D*/
protected Rectangle2D r_exit, r_lvl1, r_lvl2, r_lvl3; 


/** 
 * Konstruktor klasy, tworzy okno gry wraz z MouseListenerem
 *  wykorzystujący podwójne buforowanie
 */ 
public Pilkarz() throws InterruptedException {
    obraz = new PoborObraz();
    okno = new JFrame(".: Maly piłkarz :.");
    JPanel panel = (JPanel)okno.getContentPane();
    setBounds(0,0,PoleGry.SZEROKOSC,PoleGry.WYSOKOSC);
    panel.setPreferredSize(new Dimension(PoleGry.SZEROKOSC,PoleGry.WYSOKOSC));
    panel.setLayout(null);
    panel.add(this);

    okno.setBounds(0,0,PoleGry.SZEROKOSC,PoleGry.WYSOKOSC);
    okno.setVisible(true);
    okno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    okno.setLocation(100, 30);
    okno.setResizable(false);
    createBufferStrategy(2);
    strategia = getBufferStrategy();
    requestFocus();
    this.addMouseListener(new MouseAdapter() {
             @Override
             public void mousePressed(MouseEvent e) {
                wcisniety = true;
               start_dmuch = System.currentTimeMillis();
               pilka.moussePressed(e);
               q=1;

             }
             @Override
              public void mouseReleased(MouseEvent e) {
                  wcisniety= false;
                  xbram= rb.nextInt(610-360+1)+360;
                  ybram= rb.nextInt(345-220+1)+220;
                  bramkarz.ustawX(xbram);
                  bramkarz.ustawY(ybram);
                  czas_dmuch = (System.currentTimeMillis()-start_dmuch)/1000;
                  calk_czas_dmuch = calk_czas_dmuch + czas_dmuch;
                 if(z>100){
                  try {
                     game();
                 } catch (IOException ex) {
                     Logger.getLogger(Pilkarz.class.getName()).log(Level.SEVERE, null, ex);
                 } 
                 }
              }

             @Override
           public void mouseClicked(MouseEvent me)  {
           int mX = me.getX();
           int mY = me.getY();
            if(r_exit.contains(mX,mY)){
            czas_koncowy= czas_gry;
               try {

                   Komunikat_wyjscie("Brawo! Zdobyłeś "+suma_bramki+" bramek.\nTwój czas gry wynosi: "+czas_koncowy+" s\nŁączny czas wydechu: "+calk_czas_dmuch+" s."," ");

               } catch (IOException ex) {
                   Logger.getLogger(Pilkarz.class.getName()).log(Level.SEVERE, null, ex);
               } catch (InterruptedException ex) {
                   Logger.getLogger(Pilkarz.class.getName()).log(Level.SEVERE, null, ex);
               }


            }
            else if(r_lvl1.contains(mX,mY)){
                poziom =1;
                initWorld();
                czas_start = System.currentTimeMillis();
             }
               else if(r_lvl2.contains(mX,mY)){
                poziom =2;
                initWorld();
                czas_start = System.currentTimeMillis();
             }
               else if(r_lvl3.contains(mX,mY)){
                poziom =3;
                initWorld();
                czas_start = System.currentTimeMillis();
             }
             }
             });
    initWorld();
    czas_start = System.currentTimeMillis();
}

/** 
 * Tworzy nowe obiekty klas: Bramka,Cel,Pilka,Bramkarz 
 * W zależności od poziomu ustawia liczbę wymaganych do zdobycia bramek
 * oraz szybkość ładowania paska siły
 */
public void initWorld() {

    switch(poziom){
        case 1: czas_d = 21;
                l_bramek = 3;
            break;
        case 2: czas_d = 30;
                l_bramek = 4;
            break;
        case 3: czas_d = 39; 
                l_bramek =3;
            break;
    }

    bramka = new Bramka(this);
    bramka.ustawX( 350 );        
    bramka.ustawY( 210 );
    xcel= r.nextInt(635-360+1)+360;
    ycel= r.nextInt(370-220+1)+220;  
    cel = new Cel(this);
    cel.ustawX(xcel);
    cel.ustawY(ycel);
    pilka = new Pilka(this);
    pilka.ustawX(500);
    pilka.ustawY(500);

    xbram= rb.nextInt(635-360+1)+360;
    ybram= rb.nextInt(370-220+1)+220;  
    bramkarz = new Bramkarz(this);
    bramkarz.ustawX(450);
    bramkarz.ustawY(250);

    strategia.show();
}
/** Rysuje w oknie gry elementy stworzone w initWorld()
     * @param z - określa współrzędną y rysowanego paska siły (jego aktualną długość) */
public void paintWorld(int z) {
    Graphics2D g = (Graphics2D)strategia.getDrawGraphics();
    tlo = obraz.pobierz_obraz("tlo.jpg");
    g.setPaint(new TexturePaint(tlo, new Rectangle(0,0,tlo.getWidth(),700)));
    g.fillRect(0,0,getWidth(),getHeight());

    bramka.paint(g);
    cel.paint(g);
    if(poziom==3){
        bramkarz.paint(g);
    }
    pilka.paint(g);
    rysuj_wynik(g);
    rysuj_czas_gry(g);
    rysuj_czas_dmuch(g);
    rysuj_pasek_sily(g,z);
    rysuj_wyjscie(g);
    rysuj_poziom1(g);
    rysuj_poziom2(g);
    rysuj_poziom3(g);
    strategia.show();
}
/**
 * Rysuje aktualny wynik gracza (liczbę bramek zdobytych na aktualnym poziomie)
     * @param g
 */
public void rysuj_wynik(Graphics2D g) {
    g.setFont(new Font("Arial",Font.BOLD,20));
    g.setPaint(Color.white);
    g.drawString("Bramki:",20,PoleGry.WYSOKOSC_GRY + 10);
    g.setPaint(Color.white);
    g.drawString(pilka.pobierz_wynik()+"",95,PoleGry.WYSOKOSC_GRY + 10);
}
/**
 * Rysuje całkowity czas gry 
 * @param g
 */
public void rysuj_czas_gry(Graphics2D g) {
    g.setFont(new Font("Arial",Font.BOLD,20));
    g.setPaint(Color.white);
    g.drawString("Czas:",115,PoleGry.WYSOKOSC_GRY + 10);
    g.setPaint(Color.white);
    g.drawString(czas_gry+" s",180,PoleGry.WYSOKOSC_GRY + 10);
}
/**
 * Rysuje czas dmuchnięcia
 * @param g
 */
public void rysuj_czas_dmuch(Graphics2D g) {
    g.setFont(new Font("Arial",Font.BOLD,20));
    g.setPaint(Color.white);
    g.drawString("Czas dmuchnięcia:",265,PoleGry.WYSOKOSC_GRY + 10);
    g.setPaint(Color.white);
    g.drawString(czas_dmuch+" s",450,PoleGry.WYSOKOSC_GRY + 10);
}
/**
 * Rysuje napis WYJŚCIE
 * @param g
 */
public void rysuj_wyjscie(Graphics2D g) {
    g.setFont(new Font("Arial",Font.BOLD,30));
    g.setPaint(Color.yellow);
    g.drawString("WYJŚCIE",800,PoleGry.WYSOKOSC_GRY + 10);
    r_exit = new Rectangle2D.Double(790, 633, 145, 30);
    g.draw(r_exit);
}
/**
 * Rysuje napis POZIOM1
 * @param g
 */
public void rysuj_poziom1(Graphics2D g) {
    g.setFont(new Font("Arial",Font.BOLD,30));
        if(poziom==1)
            g.setPaint(Color.red);
        else
            g.setPaint(Color.yellow);
    g.drawString("Poziom 1",800,PoleGry.WYSOKOSC_GRY - 150);
    r_lvl1 = new Rectangle2D.Double(790, PoleGry.WYSOKOSC_GRY - 175, 150, 30);
    g.draw(r_lvl1);
}
/**
 * Rysuje napis POZIOM2
 * @param g
 */
public void rysuj_poziom2(Graphics2D g) {
    g.setFont(new Font("Arial",Font.BOLD,30));
        if(poziom==2)
            g.setPaint(Color.red);
        else
            g.setPaint(Color.yellow);
    g.drawString("Poziom 2",800,PoleGry.WYSOKOSC_GRY - 100);
    r_lvl2 = new Rectangle2D.Double(790, PoleGry.WYSOKOSC_GRY - 125, 150, 30);
    g.draw(r_lvl2);
}
/**
 * Rysuje napis POZIOM3
 * @param g
 */
public void rysuj_poziom3(Graphics2D g) {
    g.setFont(new Font("Arial",Font.BOLD,30));
        if(poziom==3)
            g.setPaint(Color.red);
        else
            g.setPaint(Color.yellow);
    g.drawString("Poziom 3",800,PoleGry.WYSOKOSC_GRY - 50);
    r_lvl3 = new Rectangle2D.Double(790, PoleGry.WYSOKOSC_GRY - 75, 150, 30);
    g.draw(r_lvl3);
}
/**
 * Rysuje pasek siły o długości zależnej od podanego parametru a
 * @param g
 * @param a
 */
public void rysuj_pasek_sily(Graphics2D g,int a) {
    if(a<100)
        g.setPaint(Color.red);
    else
        g.setPaint(Color.green); 
        g.fillRect(650,550,20,-a);  

}

/** Odświeżanie ruchu piłki */
public void Odswiez() {
    pilka.ruch(pilka.Cx, pilka.Cy);
}
/**Metoda zaimplementowana z interface PoleGry*/
@Override
public PoborObraz pobierzObraz() {
    return obraz;
}
/**Wywołuje funkcję odświeżającą ruch piłki, oraz sprawdzającą kolizje piłki z innymi obiektami
* @throws java.io.IOException 
*/
public void game() throws IOException {
    Odswiez();
    sprawdz_kolizje();
}
/**Liczy czas gry, ustawia wartość parametru odpowiedzialnego
 * za długość paska siły, wywołuje funkcję paintWorld() z tym parametrem ;
 */
public void mierz_czas() throws InterruptedException{

   while(isVisible()){
   czas_gry = (System.currentTimeMillis()-czas_start)/1000;
   if(wcisniety ==true){
       if(z<115)
       z = z+1;
        try{
        Thread.sleep(czas_d);
        } catch (InterruptedException e) {}
   }
   else{
       z=1;
   }
   paintWorld(z);
   }
}
/** Wczytuje i odtwarza dźwięk
 * @param file- ściezka do pliku dźwiękowego
 */
public static void play_sound(String file){
        AudioPlayer MGP = AudioPlayer.player;
        AudioStream BGM;
        AudioData MD;
        ContinuousAudioDataStream play = null;
        try { 
            InputStream test = new FileInputStream(file); 
            BGM = new AudioStream(test); 
            AudioPlayer.player.start(BGM); 

        } 
        catch(FileNotFoundException e){ 
            System.out.print(e.toString()); 
        } 
        catch(IOException error) 
        { 
            System.out.print(error.toString()); 
        } 
    
}

/** Zapisuje dane do pliku
 * 
 * @param nazwa - ścieżka do pliku w którym mają być zapisane dane
 * @param dane - dane podane do zapisania do pliku
 * @throws IOException 
 * Metoda zapisu wyniku danych:
 * Login - login użytkownika
 * Czas całkowity - czas działania programu (w sekundach)
 * Całkowity czas wydechu - łączny czas dmuchania w czasie działania programu (w sekundach)
 * Punkty - całkowita liczba zdobytych bramek
 * Data - data i czas zapisu danych
 * Zapis do pliku, format tekstowy, kolejne wartości rozdzielane średnikami ;
 */
public static void zapisPliku(String nazwa,String dane) throws IOException {
         PrintWriter plik = null;
        try {
           
            plik = new PrintWriter(new FileWriter(nazwa, true));
            plik.println(dane);
            
        } finally {
            if (plik != null) {
                plik.close();
            }
        }
    }
/** Wyświetla komunikat 
 * 
 * @param infoMessage - Tekst jaki ma zostać wyświetlony jako komunikat
 * @param titleBar - nazwa komunikatu
 * @throws IOException
 * @throws InterruptedException 
 */
public  void Komunikat_wyjscie(String infoMessage, String titleBar) throws IOException, InterruptedException
    {
        log = new Login(this);
        log.okno1.requestFocusInWindow();
        int dialogButton = JOptionPane.YES_NO_OPTION;
        JOptionPane.showMessageDialog(null, infoMessage, "Komunikat: " + titleBar, dialogButton);
        
        if(dialogButton == JOptionPane.YES_OPTION){
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        data = dateFormat.format(currentDate);

        }
        
    }

/** Komunikat wyświetlany na koniec poziomu 
 * 
 * @param infoMessage - Tekst jaki ma zostać wyświetlony jako komunikat
 * @param titleBar - nazwa komunikatu
 * @throws IOException
 * @throws InterruptedException 
 */
public  void Komunikat(String infoMessage, String titleBar) throws IOException, InterruptedException
    {
        int dialogButton = JOptionPane.YES_NO_OPTION;
        JOptionPane.showMessageDialog(null, infoMessage, "Komunikat: " + titleBar, dialogButton);
        if(dialogButton == JOptionPane.YES_OPTION){
            Date currentDate = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            data = dateFormat.format(currentDate);

                if(poziom==3){
                    zapisPliku("C:\\Users\\Tomek\\Documents\\NetBeansProjects\\Dmuchawka\\src\\Wyniki.txt", "Login: "+imie+";Czas całkowity: "+czas_koncowy+"s; Całkowity czas wydechu: "+calk_czas_dmuch+"s; Punkty: "+suma_bramki+"; Data: "+data);
                    System.exit(0);
                }
                poziom++;
                pilka.Wynik=0;
                initWorld();
        }
        
    }
/** 
 * Sprawdza kolizje piłki z celem lub/i bramkarzem
 * W przypadku trafienia piłki w cel, dodaje punkty do wyniku
 * Odtwarza dźwięk na koniec każdego poziomu
 * Zwiększa wartośc zmiennej poziom, po ukończeniu aktualnego poziomu
 * Po sprawdzeniu kolizji ustawia współrzędne obiektów 
 */
public void sprawdz_kolizje() throws IOException {
    Rectangle pilkaBounds = pilka.getBounds();
    Rectangle r1 = cel.getBounds();
    Rectangle bramkarzBounds = bramkarz.getBounds();
    if(bramkarzBounds.intersects(r1) && poziom==3){
    bramkarz.ustawX(450);
    bramkarz.ustawY(250);
    xcel= r.nextInt(635-360+1)+360;
    ycel= r.nextInt(370-220+1)+220;  
    cel.ustawX(xcel);
    cel.ustawY(ycel);
    pilka.ustawX(500);
    pilka.ustawY(500);
}else if (pilkaBounds.intersects(r1) && q<=1) {
        pilka.dodaj_punkt(1);
        suma_bramki+=1;
        q++;
            if(pilka.pobierz_wynik()==l_bramek){
                czas_koncowy= czas_gry;
                
                play_sound("C:\\Users\\Tomek\\Documents\\NetBeansProjects\\Dmuchawka\\src\\Dzwiek\\gol.wav"); 
                    if(poziom==3)
                        log= new Login(this);
                    else 
                        try {
                            Komunikat("Brawo! Zdobyłeś "+suma_bramki+" bramek.\nTwój czas to: "+czas_koncowy+" s\nŁączny czas wydechu: "+calk_czas_dmuch+" s\nPrzechodzisz na kolejny poziom!" ,"Wygrałeś");
                            } catch (InterruptedException ex) {
                                Logger.getLogger(Pilkarz.class.getName()).log(Level.SEVERE, null, ex);
                                }
            }else{
                bramkarz.ustawX(450);
                bramkarz.ustawY(250);
                xcel= r.nextInt(635-360+1)+360;
                ycel= r.nextInt(370-220+1)+220;  
                cel.ustawX(xcel);
                cel.ustawY(ycel);
                pilka.ustawX(500);
                pilka.ustawY(500);

            try{
               Thread.sleep(70);
                } catch (InterruptedException e) {}
              }  
    }else{
        bramkarz.ustawX(450);
        bramkarz.ustawY(250);
        xcel= r.nextInt(635-360+1)+360;
        ycel= r.nextInt(370-220+1)+220;   
        cel.ustawX(xcel);
        cel.ustawY(ycel);
        pilka.ustawX(500);
        pilka.ustawY(500);

        try{
           Thread.sleep(70);
            } catch (InterruptedException e) {}
    }

}

/** Gówna metoda aplikacji
* @param args
* @throws java.lang.InterruptedException*/
public static void main(String[] args) throws InterruptedException {
Pilkarz gra = new Pilkarz();
gra.mierz_czas();

}
   
}
