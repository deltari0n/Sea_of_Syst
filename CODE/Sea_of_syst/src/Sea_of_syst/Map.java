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
public class Map{
    private BufferedImage map, mapModified;
    private int hauteur,largeur;
    private int[][][] tabImage;
    private int[][] masqueMap;
    
    public Map(){
        try {
            this.map = ImageIO.read(getClass().getResource("/ressources/map.png"));
        } catch (IOException ex) {
            Logger.getLogger(Joueur.class.getName()).log(Level.SEVERE, null, ex);
        }
        hauteur = map.getHeight();
        largeur = map.getWidth();
        tabImage = convPNGToTab(map);
        masqueMap = creationMasque(tabImage);
    }
    
    
    //__________________________________________________________________________
    //getteur & setteur
    
    public BufferedImage getMap(){
        return this.map;
    }
    
    public int[][] getMasque(){
        return this.masqueMap;
    }
    
    
    //__________________________________________________________________________
    //EXTRACTION ET ECRITURE DE L'AFFICHAGE ET DES HITBOX DE LA MAP
    
    //on va créer une méthode qui va convertir la map sous format BufferedImage en un tableau de int
    
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
    
    //méthode pour convertir les pixels de la map en 1 et 0 si pas de pixel(fond)
    //cela servira entre autre pour gérer les collisions
    public static int[][] creationMasque(int[][][] tab){
        int h = tab.length;
        int l = tab[0].length;
        int[][] masque = new int[h][l];
        for(int i=0;i<h;i++){
            for(int j=0;j<l;j++){
                if(tab[i][j][0]==0 && tab[i][j][1]==0 && tab[i][j][2]==0){
                    masque[i][j] = 0;
                } else{
                    masque[i][j] = 1;
                }
            }
        }
        return masque;
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
            masqueMap = new int[hauteur][largeur];
            for(int i=0; i<hauteur ;i++){
                String ligne;
                String eleLigne[];
                ligne = fichier.readLine();
                eleLigne = ligne.split(" ");
                for(int j=0; j<largeur; j++){
                    masqueMap[i][j] = Integer.parseInt(eleLigne[j]);
                }
            }
            fichier.close();
        } catch (IOException e) {
        }
        return masqueMap;
    }
    
    
    //__________________________________________________________________________
    //comme toutes les autres classes...
    
    public void miseAJour() {
        
    }
    
    public void rendu(Graphics2D contexte) {
        contexte.drawImage(this.map, 0, 0, null);
    }
    
    
    //__________________________________________________________________________
    //DESTRUCTION ET MODIFICATION DE LA MAP
    
    // les méthodes suivantes servent pour la destruction et la modif de la map
    
    
    /** 
     * on recombine le tableau binaire avec le tableau de la map pour pouvoir 
     * afficher le map en enlevant les parties détruites.
     * */
    public void combinaisonBinTab(){
        int[][][] newMap;
    }
    
    /**
     * Méthode pour mettre à jour la carte en fonction des modifs
     */
    
    public void miseAJourCarte(){
        
    }
    
    
}
