/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Sea_of_syst;

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
    
    protected BufferedImage spriteDroite,spriteGauche;
    protected int x, y;
    protected int posCible;
    private Random random;
    private Joueur joueur;
    private Boolean vaADroite;
    
    public Requin(Joueur j){
        try {
            this.spriteDroite = ImageIO.read(getClass().getResource("/ressources/requin_droite.png"));
            this.spriteGauche = ImageIO.read(getClass().getResource("/ressources/requin_gauche.png"));
        } catch (IOException ex) {
            Logger.getLogger(Joueur.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.x = 500;
        this.y = 0;
        random = new Random();
        posCible = random.nextInt(1200); // trouver un moyen de lire directement la taille de la fenetre pour remplacer 1200
        joueur = j;
        vaADroite = posCible >= x;
    }
    
    //__________________________________________________________________________
    // Guetteur et setteur
    
    public int getX() {
        return this.x;
    }
    public int getY() {
        return this.y;
    }
    public int getLargeur() {
        return spriteDroite.getHeight();
    }
    public int getHauteur() {
        return spriteDroite.getWidth();
    }
    
    
    //_________________________________________________________________________
    //MàJ et rendu
    
    public void miseAJour() {
        
        if (joueur.getY()>750){
            posCible = joueur.getX();
            vaADroite = posCible >= x;
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
            posCible = random.nextInt(1200);
            vaADroite = posCible >= x;
        } 
    }
    
    public void rendu(Graphics2D contexte) {
        if (vaADroite){
            contexte.drawImage(this.spriteDroite, x, y, null);
        } else{
            contexte.drawImage(this.spriteGauche, x, y, null);
        }
    }
    
}