/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Sea_of_syst;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;
import javax.imageio.ImageIO;

/**
 *
 * @author vruche
 */
public class Joueur{
    
    //attributs et constructeur
    private BufferedImage sprite, coeur,coeurG ;
    private int x, y;
    private boolean gauche, droite, gravite, saut, clique, collision, estAuSol,estMort, estTeamBleu;
    private int n;
    private int vie ; 
    private int idJoueur;
    private String pseudo, avatar;
    
    private ArrayList<Integer> listePosSaut;

    public Joueur() {
        try {
            this.sprite = ImageIO.read(getClass().getResource("/ressources/Pirate_sprite.png"));
        } catch (IOException ex) {
            Logger.getLogger(Joueur.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.x = 800;
        this.y = 300;
        this.gauche = false;
        this.droite = false;
        this.saut = false;
        this.n = 0;
        this.clique = false;
        this.vie=5; 
        this.estMort=false;
     
        //Définit la trajectoire du saut
        this.listePosSaut = new ArrayList<>(List.of(50, 50,50, 50,25,25,20,15,15,10,10,10,10));
    }
    
    //constructeur pour les autres joueurs
    public Joueur(int id_joueur, String username, int xJ, int yJ, int niveau_vie, String avatar, boolean team){
        try {
            this.sprite = ImageIO.read(getClass().getResource("/ressources/Pirate_sprite.png"));
        } catch (IOException ex) {
            Logger.getLogger(Joueur.class.getName()).log(Level.SEVERE, null, ex);
        } 
        this.idJoueur = id_joueur;
        this.pseudo = username;
        this.x = xJ;
        this.y = yJ;
        this.vie = niveau_vie;
        this.avatar = avatar;
        this.estTeamBleu = team;
    }
    
    //__________________________________________________________________________
    //guetteur et setteur
    
    public int getId(){
        return this.idJoueur;
    }
    
    public void setGauche(boolean gauche) {
        this.gauche = gauche;
    }
    public void setDroite(boolean droite) {
        this.droite = droite;
    }
    public void setSaut(boolean saut) {
        this.saut = saut;
    }
    public void setEstAuSol(boolean sol){
        this.estAuSol = sol;
    }

    public int getX() {
        return x;
    }
    public int getY() {
        
        return y;
        
    }
    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }
    public int getLargeur() {
        return sprite.getHeight();
    }
    public int getHauteur() {
        return sprite.getWidth();
    }
    
//    public void setCollision(boolean coll){
//        // Le joueur ne fait rien si il est mort
//        if (!estMort){
//            this.collision = coll;
//        }
//    }
    
    //__________________________________________________________________________
    //VIE ET AUTRES PROPRIETE JOUEUR
    

    public int getVie(){
        return vie ;
    }
    
    public void setVie(int ptDeVie) {
        vie = ptDeVie;
    }
    
    // Méthode pour vérifier si le joueur est mort
    public boolean estMort() {
        if (vie<=0){
            return true;  // le joueur est mort 
        }
        else{
            return false;    //still with us
        }
    }
    
    public boolean getEstMort(){
        return estMort;
    }
    
    public void setEstMort(boolean state){
        estMort = state;
    }
    
    //__________________________________________________________________________
    //MàJ et rendu
    
    //mise a jour des déplacements
    public void miseAJour() {
        if (this.gauche) {
            x -= 5;
        }

        if (this.droite) {
            x += 5;
        }

        if (x > 1450 - sprite.getWidth()) { // collision avec le bord droit de la scene
            x = 1450 - sprite.getWidth();
        }

        if (x < 0) { // collision avec le bord gauche de la scene
            x = 0;
        }

        //pour gérer la chute du joueur
        /** On va faire chuter le joueur si sa position est supérieur au niveau
         * de l'océan en vérifiant qu'il ne rentre pas en collision avec un obstacle
         */
        if (y <700) {
            if (5> Math.abs( y - 700)){
                y+= Math.abs( y - 700) ;
            } 
            else {
                y += 5;
            }
        }

        //pour gérer le saut du joueur

        if (saut && estAuSol) {
            if (n <= listePosSaut.size()){
                y -= listePosSaut.get(n);
            }
            else{
                n=0;
                saut = false;
            }
        }
    }
    
    public void rendu(Graphics2D contexte) {
        if (!estMort){
            //affichage du sprite du joueur   
            contexte.drawImage(this.sprite, (int) x, (int) y, null); 
        }
       
    }    
}   
