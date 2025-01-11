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

public class Mouette extends Entite {

    private BufferedImage spriteDroiteHaut, spriteGaucheHaut, spriteDroiteBas, spriteGaucheBas;
    private double x, y;
    private int posCibleX, posCibleY;
    private Random random;
    private boolean vaADroite,oscilEnHaut;
    private double oscilAmplitude, oscilVitesse;
    private long debutJeu, apparitionMouette;
    private long dureePartie = 5 * 60 * 1000;
    private boolean mouetteVisible = false;
    
    public Mouette() {
        try {
            this.spriteDroiteHaut = ImageIO.read(getClass().getResource("/ressources/Mouette_ailes_haut_droite.png"));
            this.spriteGaucheHaut = ImageIO.read(getClass().getResource("/ressources/Mouette_ailes_haut_gauche.png"));
            this.spriteDroiteBas = ImageIO.read(getClass().getResource("/ressources/Mouette_ailes_bas_droite.png"));
            this.spriteGaucheBas = ImageIO.read(getClass().getResource("/ressources/Mouette_ailes_bas_gauche.png"));
        } catch (IOException ex) {
            Logger.getLogger(Joueur.class.getName()).log(Level.SEVERE, null, ex);
        }
        x = 500;
        y = 50;
        this.oscilAmplitude = 20;
        this.oscilVitesse = 0.05;
        random = new Random();
        this.debutJeu = System.currentTimeMillis();

        this.apparitionMouette = 50000000;
        vaADroite = posCibleX >= x;
    }
    
    /**constructeur qui va etre utiliser pour créer une mouette via la BdD
     * on utilise le chemin d'acces vers l'image pour afficher directement le bon sprite
     */
    public Mouette(double posX, double posY, boolean vaDroite, boolean oscillHaut) {
        try {
            this.spriteDroiteHaut = ImageIO.read(getClass().getResource("/ressources/Mouette_ailes_haut_droite.png"));
            this.spriteGaucheHaut = ImageIO.read(getClass().getResource("/ressources/Mouette_ailes_haut_gauche.png"));
            this.spriteDroiteBas = ImageIO.read(getClass().getResource("/ressources/Mouette_ailes_bas_droite.png"));
            this.spriteGaucheBas = ImageIO.read(getClass().getResource("/ressources/Mouette_ailes_bas_gauche.png"));
        } catch (IOException ex) {
            Logger.getLogger(Joueur.class.getName()).log(Level.SEVERE, null, ex);
        }
        x = posX;
        y = posY;
        vaADroite = vaDroite;
        oscilEnHaut = oscillHaut;
    }

    //guetteur et setteur
    
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
    
    public boolean getVaADroite(){
        return vaADroite;
    }
    public boolean getOscilEnHaut(){
        return oscilEnHaut;
    }
    
    //__________________________________________________________________________
    //MàJ et rendu
    
    public void miseAJour(long tempsActuel) {
        
        if (!mouetteVisible && tempsActuel >= apparitionMouette) {
            mouetteVisible = true;
            this.x = 150;
            this.y = 50;
            random = new Random();
            posCibleX = random.nextInt(1200); // trouver un moyen de lire directement la taille de la fenetre pour remplacer 1200
            posCibleY = random.nextInt(50) + 50;
            vaADroite = posCibleX >= x;
        }

        if (mouetteVisible) {
            if (this.posCibleX < this.x) {
                if (Math.abs(this.posCibleX - this.x) < 7) {
                    x -= Math.abs(this.posCibleX - this.x);
                } else {
                    x -= 7;
                }
            }
            if (this.posCibleX > this.x) {
                if (Math.abs(this.posCibleX - this.x) < 7) {
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
            oscilEnHaut = y < ancienY;
            vaADroite = posCibleX >= x;
        }
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
