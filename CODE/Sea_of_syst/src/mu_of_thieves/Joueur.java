/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mu_of_thieves;

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
public class Joueur extends Entite{
    
    protected BufferedImage sprite;
    protected double x, y;

    
    /**
    public void setListePosSaut(ArrayList[] listePosSaut) {
        this.listePosSaut = listePosSaut;
    }
    * */
    private boolean gauche, droite, gravite, saut, clique;
    private int n;
    private ArrayList<Integer> listePosChute;

    public Joueur() {
        try {
            this.sprite = ImageIO.read(getClass().getResource("/ressources/Pirate_sprite.png"));
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
        this.listePosChute = new ArrayList<>(List.of(70, 70,50, 50,25,25,20,15,15,10,10,10,10));
    }
    
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
        //pour gérer la chute du joueur penser a renommer bas et haut de manière compréhensible
        if (y <400) {
            if (5> Math.abs( y - 400)){
                y+= Math.abs( y - 400) ;     
            } 
            else {
                y += 5;
            }
        }
        //pour gérer le saut du joueur
        if (this.saut && y>=400) {
            if (n <= listePosChute.size()){
                y -= listePosChute.get(n);
            }
            else{
                n=0;
            }
        }
        
        if (y > 700 - sprite.getWidth()) { // collision avec le bord droit de la scene
            y = 700 - sprite.getWidth();
        }
        if (y < 0) { // collision avec le bord gauche de la scene
            y = 0;
        }
    }
    
    
    //guetteur et setteur
    
    public void rendu(Graphics2D contexte) {
        contexte.drawImage(this.sprite, (int) x, (int) y, null);
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
    /**
    public void setBas(boolean bas) {
        this.gravite = bas;
    }
    **/
    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
    public double getLargeur() {
        return sprite.getHeight();
    }
    public double getHauteur() {
        return sprite.getWidth();
    }
    
    

}