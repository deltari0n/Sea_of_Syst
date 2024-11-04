/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mu_of_thieves;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.logging.Level;
import javax.imageio.ImageIO;

/**
 *
 * @author vruche
 */
public class Jeu {
    //atributs
    private BufferedImage decor;
    private Joueur unJoueur;
    private Requin requin;
    private ArrayList<Boulet_2_canon> boulets;
    
    //constructeurs
    public Jeu() {
        try {
            this.decor = ImageIO.read(getClass().getResource("/ressources/ocean.png"));
        } catch (IOException ex) {
            Logger.getLogger(Jeu.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.unJoueur = new Joueur();
        this.requin = new Requin(unJoueur);
        this.boulets = new ArrayList<>();
    }
    
    //getteurs et setteurs
    
    public Joueur getJoueur(){
        return this.unJoueur;
    }
    
    public ArrayList<Boulet_2_canon> getListeBouletCanon(){
        return this.boulets;
    }
    
    //Methodes
    
    public void rendu(Graphics2D contexte) {
        contexte.drawImage(this.decor, 0, 0, null);
        // 2. Rendu des sprites
        this.unJoueur.rendu(contexte);
        this.requin.rendu(contexte);
        for (Boulet_2_canon boule : boulets){
            boule.rendu(contexte);
        }
        // 3. Rendu des textes
    }
    public void miseAJour() {
        // 1. Mise à jour de l’avatar en fonction des commandes des joueurs
        this.unJoueur.miseAJour();
        // 2. Mise à jour des autres éléments (objets, monstres, etc.)
        this.requin.miseAJour();
        for (int n=0; n<this.boulets.size(); n++){
            boulets.get(n).miseAJour();
            if (boulets.get(n).getTrajFini()){
                boulets.remove(n);
                n--;
            }
        }
        // 3. Gérer les interactions (collisions et autres règles)
    }
    public boolean estTermine() {
        // Renvoie vrai si la partie est terminée (gagnée ou perdue)
        return false;
    }
}
