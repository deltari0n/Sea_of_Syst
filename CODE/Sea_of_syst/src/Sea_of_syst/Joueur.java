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
    private BufferedImage sprite, coeur;
    private double x, y;
    private boolean gauche, droite, gravite, saut, clique;
    private int n;
    private int vie ; 
    
    // A RENOMMER listePosSaut !!!!!!!
    private ArrayList<Integer> listePosChute;

    public Joueur() {
        try {
            this.sprite = ImageIO.read(getClass().getResource("/ressources/Pirate_sprite.png"));
            this.coeur = ImageIO.read(getClass().getResource("/ressources/coeur.png"));
        } catch (IOException ex) {
            Logger.getLogger(Joueur.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.x = 500;
        this.y = 300;
        this.gauche = false;
        this.droite = false;
        this.saut = false;
        this.n = 0;
        this.clique = false;
        this.vie=5; 
     
        //Définit la trajectoire du saut
        this.listePosChute = new ArrayList<>(List.of(50, 50,50, 50,25,25,20,15,15,10,10,10,10));
    }
    
    //__________________________________________________________________________
    //guetteur et setteur
    public void setGauche(boolean gauche) {
        this.gauche = gauche;
    }
    public void setDroite(boolean droite) {
        this.droite = droite;
    }
    public void setSaut(boolean saut) {
        this.saut = saut;
    }
    /**
    public void setBas(boolean bas) {
        this.gravite = bas;
    }
    **/
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
    
    public void setCollision(boolean coll){
        this.collision = coll;
    }
    
    //__________________________________________________________________________
    //VIE ET AUTRES PROPRIETE JOUEUR
    

    public int getVie(){
        return vie;

    }
    
    public void setVie(int ptDeVie) {
        vie = ptDeVie;
    }
    
    
    
    //__________________________________________________________________________
    //MàJ et rendu
    
    //mise a jour des déplacements horizontaux
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
        //IMPLEMENTER LES COLLISIONS
        if (this.saut && y>=700) {
            if (n <= listePosChute.size()){
                y -= listePosChute.get(n);
            }
            else{
                n=0;
            }
        }
        
    }
    
    //mise a jour des déplacements verticaux
    public void miseAJourPart2(){
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
        //IMPLEMENTER LES COLLISIONS
        if (this.saut && y>=700) {
            if (n <= listePosChute.size()){
                y -= listePosChute.get(n);
            }
            else{
                n=0;
            }
        }
        
    }
    
    public void rendu(Graphics2D contexte) {

        //affichage du sprite du joueur
        contexte.drawImage(this.sprite, (int) x, (int) y, null);
        //affichage de son nombre de coeur
        for (int i = 0; i < vie; i++) {
            contexte.drawImage(coeur, 10 + (i * 40), 50, 30, 30, null); 
        }

    }
    
}