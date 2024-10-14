/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mu_of_thieves;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
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
    private boolean gauche, droite;

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
