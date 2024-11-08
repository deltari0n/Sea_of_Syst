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
public class Map {
    
    protected BufferedImage map;
    
    public Map(){
        try {
            this.map = ImageIO.read(getClass().getResource("/ressources/Boulet.png"));
        } catch (IOException ex) {
            Logger.getLogger(Joueur.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    //on va créer un tableau qui va convertir tout les pixel de la map en 1 et le fond en 0
    
    public void convPNGToBinaire(){
        int n = map.getWidth();
        int m = map.getHeight();
        int[][] tab = new int[n][m];
        for(int i=0;i<n;n++){
            for(int j=0;j<m;j++){
                System.out.println(map.getRGB(i, j));
            }
        }
   }
    
}
