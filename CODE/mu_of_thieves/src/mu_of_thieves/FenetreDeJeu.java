/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mu_of_thieves;

import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;

/**
 *
 * @author vruche
 */
public class FenetreDeJeu extends JFrame implements ActionListener, KeyListener, MouseListener {
    
    private BufferedImage framebuffer;
    private Graphics2D contexte;
    private JLabel jLabel1;
    private Jeu jeu;
    private Timer timer;

    public FenetreDeJeu() { 
        // initialisation de la fenetre
        this.setSize(1200, 600);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addKeyListener(this);
        this.jLabel1 = new JLabel();
        this.jLabel1.setPreferredSize(new java.awt.Dimension(1450, 750));
        this.setContentPane(this.jLabel1);
        this.pack();
    
        // Creation du buffer pour l’affichage du jeu et recuperation du contexte graphique
        this.framebuffer = new BufferedImage(this.jLabel1.getWidth(), this.jLabel1.getHeight(), BufferedImage.TYPE_INT_ARGB);
        this.jLabel1.setIcon(new ImageIcon(framebuffer));
        this.contexte = this.framebuffer.createGraphics();
        
        // Creation du jeu
        this.jeu = new Jeu();
        // Creation du Timer qui appelle this.actionPerformed() tous les 40 ms
        this.timer = new Timer(40, this);
        this.timer.start();

    }
    
    
    //_________________________________________________________________________________________________________________________________________
    //méthodes d'implémentation du clavier
    
    @Override
    public void keyPressed(KeyEvent event) {
        if (event.getKeyCode() == event.VK_RIGHT || event.getKeyCode() == 68){ //si on appuie sur d ou flèche droite pour aller à droite
            this.jeu.getJoueur().setDroite(true);
            System.out.println("check d");
            //System.out.flush();
        }
        if (event.getKeyCode() == event.VK_LEFT || event.getKeyCode() == 81){ //aller a gauche
            this.jeu.getJoueur().setGauche(true);
            System.out.println("check g");
            //System.out.flush();
        }
        if (event.getKeyCode() == 32 || event.getKeyCode() == 90){ //sauter
            
        }
    }
    
    
    @Override
    public void keyTyped(KeyEvent event) {
        
    }
    
    @Override
    public void keyReleased(KeyEvent event) {
         if (event.getKeyCode() == event.VK_RIGHT || event.getKeyCode() == 68){ //si on appuie sur d ou flèche droite pour aller à droite
            this.jeu.getJoueur().setDroite(false);
        }
        if (event.getKeyCode() == event.VK_LEFT  || event.getKeyCode() == 81){ //aller a gauche
            this.jeu.getJoueur().setGauche(false);

        }
        if (event.getKeyCode() == 32 || event.getKeyCode() == 90){ //sauter
            
        }
    }
    
    
    //_________________________________________________________________________________________________________________________________________
    //méthodes implémentation souris
    
    @Override
    //MouseClicked est la méthode que vous devez compléter si vous avez une action à faire à chaque détection de clic !
    public void mouseClicked(MouseEvent event){

    }

    @Override
    //Le clic peut être vu comme la décomposition de deux mouvements : vous pressez, puis pvous relachez. Si vous avez besoin de faire cette distinction, complétez ici !
    public void mousePressed(MouseEvent event) {

    }

    @Override
    public void mouseReleased(MouseEvent event) {

    }
    
    
    //_________________________________________________________________________________________________________________________________________
    // Methode appelee par le timer et qui effectue la boucle de jeu
    @Override
    public void actionPerformed(ActionEvent e) {
        this.jeu.miseAJour();
        this.jeu.rendu(contexte);
        this.jLabel1.repaint();
        /**
        if (this.jeu.estTermine()) {
            this.timer.stop();
        }
        **/
    }

    
    public static void main(String[] args) {
        FenetreDeJeu fenetre = new FenetreDeJeu();
        fenetre.setVisible(true);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        }

    @Override
    public void mouseExited(MouseEvent e) {
        }

}
