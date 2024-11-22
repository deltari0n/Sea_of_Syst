/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Sea_of_syst;

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
    private Mouette mouette;
    private ArrayList<Boulet_2_canon> boulets;
    private Map map;
    
    //constructeur
    public Jeu() {
        try {
            this.decor = ImageIO.read(getClass().getResource("/ressources/ocean.png"));
        } catch (IOException ex) {
            Logger.getLogger(Jeu.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.unJoueur = new Joueur();
        this.requin = new Requin(unJoueur);
        this.mouette = new Mouette();
        this.boulets = new ArrayList<>();
        this.map = new Map();
    }
    
    
    //__________________________________________________________________________
    //getteurs et setteurs
    
    public Joueur getJoueur(){
        return this.unJoueur;
    }
    
    public ArrayList<Boulet_2_canon> getListeBouletCanon(){
        return this.boulets;
    }
    
    
    //__________________________________________________________________________
    //Methodes de mise à jour
    
    public void rendu(Graphics2D contexte) {
        contexte.drawImage(this.decor, 0, 0, null);
        this.map.rendu(contexte);
        // 2. Rendu des sprites
        this.unJoueur.rendu(contexte);
        this.requin.rendu(contexte);
        this.mouette.rendu(contexte);
        for (Boulet_2_canon boule : boulets){
            boule.rendu(contexte);
        }
        // 3. Rendu des textes
            //affichage du nombre de coeur
        
            
            
    }
    

    public void miseAJour() {
        // Mise à jour de la map
        
        // 1. Mise à jour de l’avatar en fonction des commandes des joueurs
        //this.unJoueur.miseAJour();
        // 2. Mise à jour des autres éléments (objets, monstres, etc.)
        this.requin.miseAJour();
        this.mouette.miseAJour();
        for (int n=0; n<this.boulets.size(); n++){
            boulets.get(n).miseAJour();
            if (boulets.get(n).getTrajFini()){
                boulets.remove(n);
                n--;
            }
        }
        // gérer les collisions entre le joueur et la map
        
            // 1. Sauvegarde de la position actuelle du joueur
        int oldX = unJoueur.getX();
        int oldY = unJoueur.getY();

            // 2. Mise à jour du joueur (calcule ses nouvelles positions potentielles)
        unJoueur.miseAJour();
        
            // 3. Gestion des collisions horizontales
        if (collisionEntreJoueurEtMap( unJoueur.getX(), oldY, 
                unJoueur.getHauteur(), unJoueur.getLargeur(), map)){
            unJoueur.setX(oldX); // Revenir à la position précédente si collision
        }
            // 4. Gestion des collisions verticales
        
        if (collisionEntreJoueurEtMap( unJoueur.getX(), unJoueur.getY(), 
                unJoueur.getHauteur(), unJoueur.getLargeur(), map)){
            unJoueur.setY(oldY); // Revenir à la position précédente si collision
        }
        
        // 3. Gérer les interactions
        if (collisionEntreJoueurEtRequin()){
            this.unJoueur.setX(200);
            this.unJoueur.setY(200);
        }
        if (collisionEntreMouetteEtBoulet()){
            this.mouette.setX(0);
        }

        // 4. Diminution de la vie de joueur si il y a de collision 
        if (collisionEntreJoueurEtRequin()){
            this.unJoueur.setVie(unJoueur.getVie()-1);
        }

    }
    
    public boolean estTermine() {
        // Renvoie vrai si la partie est terminée (gagnée ou perdue)
        return false;
    }
    
    //_________________________________________________________________________
    //implémentation des méthodes communes aux autres classes
    
    //implémentation des méthodes pour générer des masques de collision
    
    
    
    
    
    //__________________________________________________________________________

    //gestion des collisions
    public boolean collisionEntreJoueurEtRequin() {
        if ((requin.getX() >= unJoueur.getX() + unJoueur.getLargeur()) // trop à droite
                || (requin.getX() + requin.getLargeur() <= unJoueur.getX()) // trop à gauche
                || (requin.getY() >= unJoueur.getY() + unJoueur.getHauteur()) // trop en bas
                || (requin.getY() + requin.getHauteur() <= unJoueur.getY())) { // trop en haut
            return false;
        } else {
            return true;
        }
    }
    
    //peut etre à retravailler parceque le code est dégueulasse
    
    public boolean collisionEntreMouetteEtBoulet(){
        for(Boulet_2_canon boulet : boulets){
            /** on reprend le même code que précédent, si y'a pas de collision, on fait r
            et si y'en a une on return direct true **/
            if ((boulet.getX() >= mouette.getX() + mouette.getLargeurDroiteHaut()) // trop à droite
                    || (boulet.getX() + boulet.getLargeur() <= mouette.getX()) // trop à gauche
                    || (boulet.getY() >= mouette.getY() + mouette.getHauteurDroiteHaut()) // trop en bas
                    || (boulet.getY() + boulet.getHauteur() <= mouette.getY())) { // trop en haut
            } else {
                return true;
            }
        }
        return false; 
    }
    
    
    //gérer la collision entre le joueur et les boulets (que équipe ennemie ou tous ?)
    public void collisionEntreJoueurEtBoulet(){
        
    }
    
    /** pour gérer la collision entre le joueur et la map on va faire du pixel
    perfect avec des masques
    On veut savoir en plus si les collisions
    **/
    public static boolean collisionEntreJoueurEtMap(int x, int y, int h, int l, Map map){
        /** pour chaque pixel de la hitbox du joueur on va vérifier si sur la map
         * il y'a un 1 c'est a dire une collision
         */
        for (int i = y; i<=( y + h); i++){
            for (int j = x; j<=(x + l); j++){
                if(map.getMasque()[i][j] == 1){
                    return true;
                }
            }
        }
        return false;
    }
    
    
    //même chose pour les boulets
    public boolean collisionEntreBouletEtMap(){
        
        
        
        return false;
    }
}
