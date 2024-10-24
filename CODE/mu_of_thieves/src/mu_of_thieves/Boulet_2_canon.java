/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mu_of_thieves;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author vruche
 */
public class Boulet_2_canon extends Entite {
    
    protected int x,y;
    protected int x_clique, y_clique;
    protected BufferedImage sprite;
    protected ArrayList[] listePos;
    
    public Boulet_2_canon(){
        try {
            this.sprite = ImageIO.read(getClass().getResource("/ressources/Boulet.png"));
        } catch (IOException ex) {
            Logger.getLogger(Joueur.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Boulet_2_canon(int xC, int yC){
        try {
            this.sprite = ImageIO.read(getClass().getResource("/ressources/Boulet.png"));
        } catch (IOException ex) {
            Logger.getLogger(Joueur.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.x_clique = xC;
        this.y_clique = yC;
    }
    
    
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public void setX(int x){
        this.x = x;
    }
    public void setY(int y){
        this.y = y;
    }
    
    public int getX_clique(){
        return x_clique;
    }
    public int getY_clique(){
        return y_clique;
    }
    public void setX_clique(int x){
        this.x_clique = x;
    }
    public void setY_clique(int y){
        this.y_clique = y;
    }
    
    public ArrayList[] getListePos(){
        return listePos;
    }
    
    
    
}
