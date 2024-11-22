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
public class Mouette extends Entite{
    
    //__________________________________________________________________________
    // attributs et constructeur
    
    protected BufferedImage spriteDroiteHaut,spriteGaucheHaut,spriteDroiteBas,spriteGaucheBas;
    protected double x, y;
    protected int posCibleX;
    protected int posCibleY;
    private Random random;
    private Boolean vaADroite;
    private double oscilAmplitude = 20;
    private double oscilVitesse = 0.05;
    private boolean oscilEnHaut;
    
    public Mouette(){
        try {
            this.spriteDroiteHaut = ImageIO.read(getClass().getResource("/ressources/Mouette_ailes_haut_droite.png"));
            this.spriteGaucheHaut = ImageIO.read(getClass().getResource("/ressources/Mouette_ailes_haut_gauche.png"));
            this.spriteDroiteBas= ImageIO.read(getClass().getResource("/ressources/Mouette_ailes_bas_droite.png"));
            this.spriteGaucheBas = ImageIO.read(getClass().getResource("/ressources/Mouette_ailes_bas_gauche.png"));
        } catch (IOException ex) {
            Logger.getLogger(Joueur.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.x = 150;
        this.y = 150;
        random = new Random();
        posCibleX = random.nextInt(1200); // trouver un moyen de lire directement la taille de la fenetre pour remplacer 1200
        posCibleY = random.nextInt(50)+150 ;
        vaADroite = posCibleX >= x;
    }
    
    //__________________________________________________________________________
    //Guetteur & setteur
    
    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
    public void setX(int x){
        this.x = x;
    }
    public void setY(int y){
        this.y = y;
    }
    
    public double getLargeurDroiteHaut() {
        return spriteDroiteHaut.getHeight();
    }
    public double getLargeurDroiteBas() {
        return spriteDroiteBas.getHeight();
    }
    public double getLargeurGaucheHaut() {
        return spriteGaucheHaut.getHeight();
    }
    public double getLargeurGaucheBas() {
        return spriteGaucheBas.getHeight();
    }
    
    public double getHauteurDroiteHaut() {
        return spriteDroiteHaut.getWidth();
    }
    public double getHauteurDroiteBas() {
        return spriteDroiteBas.getWidth();
    }
    public double getHauteurGaucheHaut() {
        return spriteGaucheHaut.getWidth();
    }
    public double getHauteurGaucheBas() {
        return spriteGaucheBas.getWidth();
    }
    
    
    //__________________________________________________________________________
    //MàJ et rendu
    
    public void miseAJour() {
        
        if (this.posCibleX < this.x) {
            if (Math.abs(this.posCibleX - this.x) < 7){
                x -= Math.abs(this.posCibleX - this.x);
            } else {
                x -= 7;
            }
        }
        if (this.posCibleX > this.x) {
            if (Math.abs(this.posCibleX - this.x) < 7){
                x += Math.abs(this.posCibleX - this.x);
            } else {
                x += 7;
            }
        }
        if (this.posCibleX == this.x) {
            //Random random = new Random();
            posCibleX = (int) random.nextInt(1200);
            vaADroite = posCibleX >= x;
        } 
        double ancienY = y;
        y = posCibleY + oscilAmplitude * Math.sin(x * oscilVitesse);
        oscilEnHaut = y <ancienY;
    }
    
    public void rendu(Graphics2D contexte) {
        if (vaADroite){
            if(oscilEnHaut){
                contexte.drawImage(this.spriteDroiteBas, (int) x, (int) y, null);
            }
            else{
                contexte.drawImage(this.spriteDroiteHaut, (int) x, (int) y, null);
            }
        } else{
            if(oscilEnHaut){
                contexte.drawImage(this.spriteGaucheBas, (int) x, (int) y, null);
            }
            else{
                contexte.drawImage(this.spriteGaucheHaut, (int) x, (int) y, null);
            }
        }
    }
    
}
