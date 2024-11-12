/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Sea_of_syst;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author vruche
 */
public class Map {
    private BufferedImage map;
    private int hauteur,largeur;
    
    
    public Map(){
        try {
            this.map = ImageIO.read(getClass().getResource("/ressources/Boulet.png"));
        } catch (IOException ex) {
            Logger.getLogger(Joueur.class.getName()).log(Level.SEVERE, null, ex);
        }
        hauteur = map.getHeight();
        largeur = map.getWidth();
    }
    
    
    //on va créer un tableau qui va convertir tout les pixel de la map en 1 et le fond en 0
    
    public int[][][] convPNGToTab(){
        int n = map.getWidth();
        int m = map.getHeight();
        int[][][] tab = new int[n][m][3];
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                //System.out.println(map.getPixel(i, j));
                Color color = new Color(map.getRGB(i, j));
                int red = color.getRed();
                int green = color.getGreen();
                int blue = color.getBlue();
                tab[i][j][0] = red;
                tab[i][j][1] = green;
                tab[i][j][2] = blue;
            }
        }
        return tab;
    }
    
    //méthodes pour convertir les pixels de la map en 1 et 0 si pas de pixel(fond)
    public int[][] convTabToBinaire(int[][][] tab){
        int n = map.getWidth();
        int m = map.getHeight();
        int[][] tabBinaire = new int[n][m];
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                if(tab[i][i][0]==0 && tab[i][j][1]==0 && tab[i][j][2]==0){
                    tabBinaire[i][j] = 0;
                } else{
                    tabBinaire[i][j] = 1;
                }
            }
        }
        return tabBinaire;
    }
    
    public void ecritureFichierTabBinaire(int[][] tab){
        try {
            String sauvegardeMap = "map";
            FileWriter sauvegarde = new FileWriter(sauvegardeMap);
            for (int i=0; i<this.largeur; i++){
                sauvegarde.write(Arrays.toString(tab[i])+ System.getProperty("line.separator"));
            }
            sauvegarde.close();
        } catch (IOException e) {
        }
    }
    
    public void afficherMap(){
        
    }
}
