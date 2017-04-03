
import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
 
/** 
   * <b>Login</b> określa klasę Login pobierająca i przechowującą login użytkownika
   * @author Tomasz Kodzis
   */
public class Login {
    /** Przechowuje login użytkownika*/
    public String login;
    /** Komponent po naciśnięciu którego zapamiętywany jest login */ 
    private final JButton zapisz;
    /** Obiekt klasy Pilkarz*/
    private final Pilkarz pilkarz;
    /** Pole gdzie wpisywany jest login*/
    private final JTextField log;
    /** Kontener; okno klasy Login*/
    protected final JFrame okno1;
    /** Konstruktor klasy Login, w którym tworzone jest okno wraz z komponentami
     * @param p*/  
    public Login(Pilkarz p){
        okno1 = new JFrame("Login");
        okno1.setVisible(true);
        okno1.setSize(400,200);
        okno1.setLocation(400,300);
        pilkarz=p;
        
        JLabel label = new JLabel("Podaj login");
        JPanel panel = new JPanel();
        zapisz = new JButton("Zapisz");
        zapisz.addActionListener(new LoginListener());
        okno1.add(panel);
        
        log = new JTextField(10);
        panel.add(label,BorderLayout.WEST);
        panel.add(log, BorderLayout.EAST);
        panel.add(zapisz, BorderLayout.SOUTH);
    }
    
/** 
 * <b>LoginListener</b> określa klasę LoginListener implementującą ActionListener,
 * pozwalającą na zapis loginu uzytkownika wraz wynikami gry do pliku
 * @author Tomasz Kodzis
 */
        
class LoginListener implements ActionListener{
    /** Pobiera wpisany login oraz wywołuje odpowiedni komunikat lub zapisuje dane do pliku */    
    @Override
        public void actionPerformed(ActionEvent e){
            login = log.getText();
            pilkarz.imie = login;
            okno1.setVisible(false);
            
            try {
                if(pilkarz.poziom==3)
                pilkarz.Komunikat("Login: "+login+"\nBrawo! Zdobyłeś "+pilkarz.suma_bramki+" bramek.\nTwój czas gry to: "+pilkarz.czas_koncowy+" s\nŁączny czas wydechu: "+pilkarz.calk_czas_dmuch+" s" ,"Wygrałeś");
                else{
                    pilkarz.zapisPliku("C:\\Users\\Tomek\\Documents\\NetBeansProjects\\Dmuchawka\\src\\Wyniki.txt", "Login: "+pilkarz.imie+";Czas całkowity: "+pilkarz.czas_gry+"s; Całkowity czas wydechu: "+pilkarz.calk_czas_dmuch+"s; Punkty: "+pilkarz.suma_bramki+"; Data: "+pilkarz.data);
                    System.exit(0);
                }
                    
                                
            } catch (IOException | InterruptedException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
         
            }
    } 

    
   
}