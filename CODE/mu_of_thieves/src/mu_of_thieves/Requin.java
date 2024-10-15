/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mu_of_thieves;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import static java.lang.Math.abs;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import java.util.Random;

/**
 *
 * @author vruche
 */
public class Requin extends Entite{
    
    protected BufferedImage sprite;
    protected double x, y;
    protected int posCible;
    private Random random;
    private Joueur joueur;
    
    public Requin(Joueur j){
        try {
            this.sprite = ImageIO.read(getClass().getResource("/ressources/requin.png"));
        } catch (IOException ex) {
            Logger.getLogger(Joueur.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.x = 500;
        this.y = 500;
        random = new Random();
        posCible = random.nextInt(1200); // trouver un moyen de lire directement la taille de la fenetre pour remplacer 1200
        joueur = j;
    }
    
    public void miseAJour() {
        
        if (joueur.getY()>500){
            posCible = (int) joueur.getX();
        }
        
        if (this.posCible < this.x) {
            if (Math.abs(this.posCible - this.x) < 7){
                x -= Math.abs(this.posCible - this.x);
            } else {
                x -= 7;
            }
        }
        if (this.posCible > this.x) {
            if (Math.abs(this.posCible - this.x) < 7){
                x += Math.abs(this.posCible - this.x);
            } else {
                x += 7;
            }
        }
        if (this.posCible == this.x) {
            //Random random = new Random();
            posCible = (int) random.nextInt(1200);
        } 
    }
    
    
    
    
    //guetteur et setteur
    
    public void rendu(Graphics2D contexte) {
        contexte.drawImage(this.sprite, (int) x, (int) y, null);
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
