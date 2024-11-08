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
public class Joueur extends Entite{
    
    protected BufferedImage sprite ;
    protected double x, y ;
    private boolean gauche, droite, bas, haut, clique ;
    private int n ;
    private ArrayList[] listePosSaut ;

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
        this.haut = false;
        this.bas = false;
        this.n = 0;
        this.clique = false;
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
        if (this.bas && !this.haut) {
            y += 5;
        }
        //pour gérer le saut du joueur
        if (this.haut) {
            y -= 5;
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
    public void setHaut(boolean haut) {
        this.haut = haut;
    }
    public void setBas(boolean bas) {
        this.bas = bas;
    }
    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }
    public double getLargeur() {
        return sprite.getHeight();
    }
    public double getHauteur() {
        return sprite.getWidth();
    }
    

}