/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mu_of_thieves;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author vruche
 */
public class Boulet_2_canon extends Entite {
    
    protected int x,y;
    protected BufferedImage sprite;
    protected Arraylist[] listePos;
    
    public Boulet_2_canon(){
        try {
            this.sprite = ImageIO.read(getClass().getResource("/ressources/Boulet.png"));
        } catch (IOException ex) {
            Logger.getLogger(Joueur.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
