/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Sea_of_syst;

import Sea_of_syst.DBA.DBA_Joueur;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import static java.lang.Math.abs;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import java.util.Random;

/**
 *
 * @author vruche
 */
public class Requin extends DBA_Joueur{
    
    protected BufferedImage spriteDroite,spriteGauche;
    protected int x, y;
    protected int posCible;
    private Random random;
    private Joueur joueur;
    private Boolean vaADroite;
    private int idJoueurChasse=-1;
    
    public Requin(Joueur j){
        try {
            this.spriteDroite = ImageIO.read(getClass().getResource("/ressources/requin_droite.png"));
            this.spriteGauche = ImageIO.read(getClass().getResource("/ressources/requin_gauche.png"));
        } catch (IOException ex) {
            Logger.getLogger(Joueur.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.x = 500;
        this.y = 600;
        random = new Random();
        posCible = random.nextInt(1200); // trouver un moyen de lire directement la taille de la fenetre pour remplacer 1200
        joueur = j;
        vaADroite = posCible >= x;
    }
    
    public Requin(int posX, int posY , boolean vaDroite){
        try {
            this.spriteDroite = ImageIO.read(getClass().getResource("/ressources/requin_droite.png"));
            this.spriteGauche = ImageIO.read(getClass().getResource("/ressources/requin_gauche.png"));
        } catch (IOException ex) {
            Logger.getLogger(Joueur.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.x = posX;
        this.y = posY;
        this.vaADroite = vaDroite;
    }
    
    public Requin(){
        try {
            this.spriteDroite = ImageIO.read(getClass().getResource("/ressources/requin_droite.png"));
            this.spriteGauche = ImageIO.read(getClass().getResource("/ressources/requin_gauche.png"));
        } catch (IOException ex) {
            Logger.getLogger(Joueur.class.getName()).log(Level.SEVERE, null, ex);
        }
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
    public void setX(int posX) {
        this.x = posX;
    }
    public void setY(int posY) {
        this.y = posY;
    }
    public boolean getVaADroite(){
        return vaADroite;
    }
    public void setVaADroite(boolean vaDroite){
        vaADroite = vaDroite;
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
    
    //même méthodes mais quand y'a plusieurs joueurs, le requin poursuis un joueur
    //éventuellement celui avec le moins de vie
    
    public void miseAJour(List<Joueur> joueurs) {
        //va parcourir tous les joueurs pour vérifier si il poursuit un joueur et
        //si le joueur qu'il poursuit est encore dans l'eau
        
        for (Joueur j:joueurs){
            if (idJoueurChasse!=-1){ //vérifie si on poursuit déjà un joueur et qu'il est encore dans l'eau
                if(j.getId()==idJoueurChasse){
                    if(j.getY()>750){
                        posCible = j.getX();
                    } else{
                        idJoueurChasse = -1;
                    }
                }
            } else{ 
                /**utiliser un else n'est pas idéal car idJoueurChasse peut repasser à -1 mais c'est pas si génant
                car le requin n'aura pas de cible que pendant 40ms**/
                if(j.getY()>750){
                    posCible = j.getX();
                    idJoueurChasse = j.getId();
                }
            }
            //utiliser un else n'est pa
            if (idJoueurChasse==1){
                
            }
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
            posCible = random.nextInt(1200);
            vaADroite = posCible >= x;
            idJoueurChasse = -1;
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