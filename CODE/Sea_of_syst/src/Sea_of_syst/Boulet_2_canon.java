/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Sea_of_syst;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author vruche
 */
public class Boulet_2_canon extends Entite {
    
    protected int xB,yB, xJ, yJ;
    protected int x_clique, y_clique;
    protected BufferedImage sprite;
    protected ArrayList<int[]> listePos;
    //protected int[] coeffPolynome;
    protected Joueur joueur;
    protected int indPos;
    protected boolean trajFini;
    private int largeur, hauteur;
    private int idBoulet, idJoueur;
    
    //__________________________________________________________________________
    //constructeurs
    
    public Boulet_2_canon(){
        try {
            this.sprite = ImageIO.read(getClass().getResource("/ressources/Boulet.png"));
        } catch (IOException ex) {
            Logger.getLogger(Joueur.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Boulet_2_canon(int xC, int yC, Joueur j){
        try {
            this.sprite = ImageIO.read(getClass().getResource("/ressources/Boulet.png"));
        } catch (IOException ex) {
            Logger.getLogger(Joueur.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.x_clique = xC;
        this.y_clique = yC;
        this.xJ = (int) j.getX();
        this.yJ = (int) j.getY();
        this.listePos = setTraj(x_clique,y_clique, xJ, yJ);
        this.indPos = 0;
        this.trajFini = false;
        this.hauteur = sprite.getHeight();
        this.largeur = sprite.getWidth();
        this.idBoulet = -1; //par défault on met une id impossible quand le boulet n'est pas encore connecté à la BdD 
        this.idJoueur = -1;
    }
    
    //constructeur pour récupérer un boulet de la BdD
    public Boulet_2_canon(int idB, int xC, int yC, int idJ){
        try {
            this.sprite = ImageIO.read(getClass().getResource("/ressources/Boulet.png"));
        } catch (IOException ex) {
            Logger.getLogger(Joueur.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.x_clique = xC;
        this.y_clique = yC;
        this.hauteur = sprite.getHeight();
        this.largeur = sprite.getWidth();
        this.idBoulet = idB;
        this.idJoueur = idJ;
    }
    
    //__________________________________________________________________________
    //getteur & setteur
    
    public int getX(){
        return xB;
    }
    public int getY(){
        return yB;
    }
    public void setX(int x){
        this.xB = x;
    }
    public void setY(int y){
        this.yB = y;
    }
    
    public int getIDBoulet(){
        return this.idBoulet;
    }
    public void setIDBoulet(int idBdD){
        this.idBoulet = idBdD;
    }
    
    public int getIDJoueur(){
        return this.idJoueur;
    }
    public void setIDJoueur(int idBdD){
        this.idJoueur = idBdD;
    }
    
    public int getLargeur(){
        return this.largeur;
    }
    public int getHauteur(){
        return this.hauteur;
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
    
    public boolean getTrajFini(){
        return trajFini;
    }
    
    public ArrayList<int[]> getListePos(){
        return listePos;
    }
    
    public String toString(List<int[]> laliste){
        StringBuilder sb = new StringBuilder();
        sb.append("[\n");

        // Itération sur chaque liste dans la liste principale
        for (int[] list : laliste) {
            sb.append(Arrays.toString(list)).append("\n");
        }

        sb.append("]");
        return sb.toString();
    }
    
    
    //__________________________________________________________________________
    
    //Créer le polynome associé aux trois points choisis (joueur, clique, milieu)
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
    
    
    //Méthodes pour générer la liste de points par lequel va passer le boulet en 
    //fonction de sa vitesse (son pas) et de sa direction
    
    
    //version avec un pas absolu
    public static ArrayList<int[]> genererPoints(double[] coeff, int xDebut, int xMax,
            double pas, boolean cliqueADroite) {
        ArrayList<int[]> points = new ArrayList<>();
        double a = coeff[0];
        double b = coeff[1];
        double c = coeff[2];
        
        //on initialise le premier point
        double yInd = a*xDebut*xDebut + b*xDebut + c;
        points.add(new int[]{ xDebut, (int) yInd});
        
        //Si on clique a droite du perso on parcours la traj parabolique vers les x+
        if (cliqueADroite){
            /**on créé une liste de position que prend le boulet de canon entre le Joueur et la coordonnées max
            de la map pour que le boulet continue sa traj au delà du clique
            **/
            int xInd = xDebut;
            int xNext = xInd+1;
            while(xInd < xMax && xNext < xMax+pas){
                double yNext;
                /** on va déterminer ou se trouve le prochain point sachant qu'on se déplace d'un pas absolu
                 * on va donc pour chaque x le y associé puis on va calculer la distance euclidienne (le pas)
                 * et si ce pas est assez proche du pas voulu on ajouter (x,y) à points
                 * Pour commencer simplement on cherche le premier point x tel que distEucl>=pas
                 */
                
                yInd = a*xInd*xInd + b*xInd + c;
                yNext = a*xNext*xNext + b*xNext + c;
                double distEucl = Math.sqrt((xNext-xInd)*(xNext-xInd) + (yNext-yInd)*(yNext-yInd));
                if(distEucl>=pas){
                    points.add(new int[]{ xNext, (int) yNext});
                    xInd = xNext;
                }
                xNext++;
            }
        } else { //Si on clique a droite du perso on parcours la traj parabolique vers les x-
            //même chose mais jusqu'à la bordure gauche donc x=0
            int xInd = xDebut;
            int xNext = xInd-1;
            while(xInd > 0 && xNext > -pas){
                double yNext;
                /** on va déterminer ou se trouve le prochain point sachant qu'on se déplace d'un pas absolu
                 * on va donc pour chaque x le y associé puis on va calculer la distance euclidienne (le pas)
                 * et si ce pas est assez proche du pas voulu on ajouter (x,y) à points
                 * Pour commencer simplement on cherche le premier point x tel que distEucl>=pas
                 */
                
                yInd = a*xInd*xInd + b*xInd + c;
                yNext = a*xNext*xNext + b*xNext + c;
                double distEucl = Math.sqrt((xNext-xInd)*(xNext-xInd) + (yNext-yInd)*(yNext-yInd));
                if(distEucl>=pas){
                    points.add(new int[]{ xNext, (int) yNext});
                    xInd = xNext;
                }
                xNext--;
            }
        }
        return points;
    }
    
    
    //Méthode pour créer la traj du boulets de canon en combinant les méthodes précédente
    public static ArrayList<int[]> setTraj(int xClique, int yClique, int xJoueur, int yJoueur){
        int[] p1 = {xJoueur,yJoueur};
        int[] p3 = {xClique,yClique};
        int[] p2 = {(int) Math.round((xJoueur+xClique)/2), (int) Math.round((yJoueur+yClique)/2-200)};
        //on créé un booleen qui vérifie si on clique à droite ou a gauche du joueur pour
        //connaitre la direction de la trajectoire
        boolean cliqueADroite;
        if (xClique >= xJoueur){
            cliqueADroite = true;
        } else{
            cliqueADroite = false;
        }
        double[] coeffPoly = creerPolynome(p1,p2,p3);
        ArrayList<int[]> pos = genererPoints(coeffPoly, xJoueur, 2500, 20, 
                cliqueADroite); //ajouter lecture taille map
        return pos;
    }
    
    
    //__________________________________________________________________________
    //mise à jour et rendu pour la classe Jeu
    
    public void miseAJour(){
        xB = listePos.get(indPos)[0];
        yB = listePos.get(indPos)[1];
        if (indPos == listePos.size()-1){
            trajFini = true;
        } else {
            indPos++;
        }
    }
    
    public void rendu(Graphics2D contexte) {
        contexte.drawImage(this.sprite, xB, yB, null);
    }
}
