/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Sea_of_syst;

//import mu_of_thieves.*;

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
import java.awt.Image; 
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
/**
 *
 * @author vruche
 */
public class FenetreDeJeu extends JFrame implements ActionListener, KeyListener, MouseListener {
    
    private BufferedImage framebuffer,coeurImage ;
    private Graphics2D contexte;
    private JLabel jLabel1;
    private Jeu jeu;
    private Timer timer;
    private int elapsedTime; // Temps écoulé en seconde
    private int minutes ; 
    private int secondes ;
   

    public FenetreDeJeu() { 
        // initialisation de la fenetre
        this.setSize(1200, 600);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addKeyListener(this);
        this.addMouseListener(this);
        this.jLabel1 = new JLabel();
        this.jLabel1.setPreferredSize(new java.awt.Dimension(1450, 750));
        this.setContentPane(this.jLabel1);
        this.pack();
    
        // Creation du buffer pour l’affichage du jeu et recuperation du contexte graphique
        this.framebuffer = new BufferedImage(this.jLabel1.getWidth(), this.jLabel1.getHeight(), BufferedImage.TYPE_INT_ARGB);
        this.jLabel1.setIcon(new ImageIcon(framebuffer));
        this.contexte = this.framebuffer.createGraphics();
        //this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        //this.setUndecorated(true);
        
        // Creation du jeu
        this.jeu = new Jeu();
        // Creation du Timer qui appelle this.actionPerformed() tous les 40 ms
        this.timer = new Timer(40, this);
        this.timer.start();
        
        // Initialisation du temps écoulé
        this.elapsedTime = 0;
        this.minutes= 0;
        this.secondes = 0;
        
        
        
        
        
    }
   
    
    
    
    
//      Méthode pour dessiner le Timer sur le jeu
    private void drawTimer() {
       // Effacer l'espace dédié au timer
        contexte.setColor(new java.awt.Color(255, 255, 255, 200)); // Couleur de fond semi-transparente
        contexte.fillRect(10, 10, 150, 30); // Dessiner un rectangle pour le fond du timer

        // Calcul du temps écoulé en minutes et secondes
        minutes = (elapsedTime*40/1000) / 60; // Convertir le temps en minutes
        secondes = (elapsedTime*40/1000) % 60; // Récupérer les secondes restantes

        // Formatage du texte du timer
        String formattedTime = String.format("Temps : %02d:%02d", minutes, secondes); // Format: "Temps : 01:30"

        // Dessiner le texte du timer sur l'écran
        contexte.setColor(java.awt.Color.BLACK); // Couleur du texte
        contexte.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 20)); // Police du texte
        contexte.drawString(formattedTime, 15, 30); // Positionner le texte dans le rectangle
    }
    
 
    
    //_________________________________________________________________________________________________________________________________________
    //méthodes d'implémentation du clavier
    @Override
    public void keyPressed(KeyEvent event) {
        if (event.getKeyCode() == event.VK_RIGHT || event.getKeyCode() == 68){ //si on appuie sur d ou flèche droite pour aller à droite
            this.jeu.getJoueur().setDroite(true);
        }
        if (event.getKeyCode() == event.VK_LEFT || event.getKeyCode() == 81){ //aller a gauche
            this.jeu.getJoueur().setGauche(true);
        }
        if (event.getKeyCode() == event.VK_UP){ //aller en haut
            this.jeu.getJoueur().setSaut(true);
        }
        /**
        if (event.getKeyCode() == event.VK_DOWN){ //aller en bas
            this.jeu.getJoueur().setBas(true);
        }
        * **/
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
        if (event.getKeyCode() == event.VK_UP){ //sauter
            this.jeu.getJoueur().setSaut(false);
        }
        /**
        if (event.getKeyCode() == event.VK_DOWN){ //aller a gauche
            this.jeu.getJoueur().setBas(false);
        }
        **/
        if (event.getKeyCode() == 32 || event.getKeyCode() == 90){ //sauter
            
        }
    }
    
    
    //_________________________________________________________________________________________________________________________________________
    //méthodes implémentation souris
    
    @Override
    //MouseClicked est la méthode que vous devez compléter si vous avez une action à faire à chaque détection de clic !
    public void mouseClicked(MouseEvent event){
        if(!this.jeu.getJoueur().estMort()){
            this.jeu.getListeBouletCanon().add(new Boulet_2_canon(event.getX() , event.getY(), this.jeu.getJoueur()));
            System.out.flush();
        }
    }

    @Override
    //Le clic peut être vu comme la décomposition de deux mouvements : vous pressez, puis pvous relachez. Si vous avez besoin de faire cette distinction, complétez ici !
    public void mousePressed(MouseEvent event) {
    }

    @Override
    public void mouseReleased(MouseEvent event) {
    }
    
     @Override
    public void mouseEntered(MouseEvent e) {
        }

    @Override
    public void mouseExited(MouseEvent e) {
        }
    
    //_________________________________________________________________________________________________________________________________________
    // Methode appelee par le timer et qui effectue la boucle de jeu
    @Override
    public void actionPerformed(ActionEvent e) {
        this.jeu.miseAJour();
        
        // Mettre à jour le temps (toutes les 1000 ms -> 1 seconde)
        elapsedTime++;
        // Mettre à jour l'affichage
        repaint();
        // Rendu graphique
        this.jeu.rendu(contexte);

        // Affichage du temps écoulé sur l'écran
        drawTimer();

        // Rafraîchir l'affichage
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


}
