/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Sea_of_syst;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
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
    private BufferedImage map, mapModified;
    private int hauteur,largeur;
    private int[][][] tabImage;
    private int[][] tabBinImage;
    
    public Map(){
        try {
            this.map = ImageIO.read(getClass().getResource("/ressources/map.png"));
        } catch (IOException ex) {
            Logger.getLogger(Joueur.class.getName()).log(Level.SEVERE, null, ex);
        }
        hauteur = map.getHeight();
        largeur = map.getWidth();
        tabImage = convPNGToTab(map);
        tabBinImage = convTabToBinaire(tabImage);
    }
    
    //getteur & setteur
    
    public BufferedImage getMap(){
        return this.map;
    }
    
    
    
    
    //on va créer un tableau qui va convertir tout les pixel de la map en 1 et le fond en 0
    
    public static int[][][] convPNGToTab(BufferedImage map){
        int h = map.getHeight();
        int l = map.getWidth();
        int[][][] tab = new int[h][l][3];
        for(int i=0;i<h;i++){
            for(int j=0;j<l;j++){
                Color color = new Color(map.getRGB(j, i));
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
    public static int[][] convTabToBinaire(int[][][] tab){
        int h = tab.length;
        int l = tab[0].length;
        int[][] tabBinaire = new int[h][l];
        for(int i=0;i<h;i++){
            for(int j=0;j<l;j++){
                if(tab[i][j][0]==0 && tab[i][j][1]==0 && tab[i][j][2]==0){
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
            for (int i=0; i<this.hauteur; i++){
                sauvegarde.write(Arrays.toString(tab[i])+ System.getProperty("line.separator"));
            }
            sauvegarde.close();
        } catch (IOException e) {
        }
    }
    
    public int[][] lectureFichiertabBinaire(String nomDuFichier){
        try{
            BufferedReader fichier = new BufferedReader(new FileReader(nomDuFichier));
            tabBinImage = new int[hauteur][largeur];
            for(int i=0; i<hauteur ;i++){
                String ligne;
                String eleLigne[];
                ligne = fichier.readLine();
                eleLigne = ligne.split(" ");
                for(int j=0; j<largeur; j++){
                    tabBinImage[i][j] = Integer.parseInt(eleLigne[j]);
                }
            }
            fichier.close();
        } catch (IOException e) {
        }
        return tabBinImage;
    }
    
    public void afficherMap(){
        int[][][] newMap;
    }
    
    
    
    public void miseAJour() {
        
    }
    
    public void rendu(Graphics2D contexte) {
        contexte.drawImage(this.mapModified, 0, 0, null);
    }
}
