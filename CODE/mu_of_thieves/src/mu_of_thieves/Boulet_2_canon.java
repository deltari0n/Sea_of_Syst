/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mu_of_thieves;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
    protected int[] coeffPolynome;
    
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
    
    
    public static double[] creerPolynome(int[] point1, int[] point2, int[] point3) {
        // Convertir les coordonnées en double pour les calculs
        double x1 = point1[0], y1 = point1[1];
        double x2 = point2[0], y2 = point2[1];
        double x3 = point3[0], y3 = point3[1];

        // Calcul des coefficients du système d'équations
        double a1 = x1 * x1, b1 = x1, c1 = 1;
        double a2 = x2 * x2, b2 = x2, c2 = 1;
        double a3 = x3 * x3, b3 = x3, c3 = 1;

        // Déterminants de Cramer
        double D = a1 * (b2 * c3 - b3 * c2) - b1 * (a2 * c3 - a3 * c2) + c1 * (a2 * b3 - a3 * b2);
        double Da = y1 * (b2 * c3 - b3 * c2) - b1 * (y2 * c3 - y3 * c2) + c1 * (y2 * b3 - y3 * b2);
        double Db = a1 * (y2 * c3 - y3 * c2) - y1 * (a2 * c3 - a3 * c2) + c1 * (a2 * y3 - a3 * y2);
        double Dc = a1 * (b2 * y3 - b3 * y2) - b1 * (a2 * y3 - a3 * y2) + y1 * (a2 * b3 - a3 * b2);

        // Résolution pour a, b et c
        double a = Da / D;
        double b = Db / D;
        double c = Dc / D;

        return new double[]{a, b, c};
    }
    
    public static List<int[]> genererPoints(double a, double b, double c, int xDebut, int xFin, int pas) {
        List<int[]> points = new ArrayList<>();
        
        for (int x = xDebut; x <= xFin; x += pas) {
            double yDouble = a * x * x + b * x + c;  // Calcul de y en double
            int y = (int) Math.round(yDouble);        // Arrondir y pour le transformer en int
            points.add(new int[]{x, y});              // Ajouter le point (x, y) à la liste
        }
        
        return points;
    }
}
