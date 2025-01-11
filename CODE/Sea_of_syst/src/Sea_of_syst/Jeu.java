    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Sea_of_syst;

import Sea_of_syst.DBA.*;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;
import javax.imageio.ImageIO;
//import Sea_of_syst.DBA;

/**
 *
 * @author vruche
 */
public class Jeu {
    //atributs
    private BufferedImage decor, coeur,coeurG ;;
    private Joueur unJoueur;
    private Requin requin;
    private Mouette mouette;
    private ArrayList<Boulet_2_canon> boulets;
    private Map map;
    private long tempsActuel = System.currentTimeMillis();
    private DBA_Joueur dbaJoueur;
    private DBA_Mouette dbaMouette;
    private DBA_Requin dbaRequin;
    private DBA_Boulet_2_Canon dbaBoulets;
    private int idJoueur, idRequin, idMouette;
    private List<Joueur> joueurs;
    private boolean hoteRequin = false, hoteMouette = false; //Pour savoir qui génère et gère le requin/mouette et ses déplacements
    private String pseudo;
    private ArrayList<Integer> idsBoulets;
    //constructeur
    
    public Jeu(String username, boolean estTeamBleu) {
        try {
            this.decor = ImageIO.read(getClass().getResource("/ressources/ocean.png"));
            this.coeur = ImageIO.read(getClass().getResource("/ressources/coeur.png"));
            this.coeurG = ImageIO.read(getClass().getResource("/ressources/coeurGr.png"));
        } catch (IOException ex) {
            Logger.getLogger(Jeu.class.getName()).log(Level.SEVERE, null, ex);
        }
        pseudo = username;
        this.unJoueur = new Joueur();
        this.mouette = new Mouette();
        this.boulets = new ArrayList<>();
        this.idsBoulets = new ArrayList<>();
        this.map = new Map();
        //Initialisation des Base de données
            //Init joueur
        this.dbaJoueur = new DBA_Joueur();
        this.dbaJoueur.insertJoueur(pseudo, unJoueur.getX(), unJoueur.getY(), unJoueur.getVie(), 0, "ok", estTeamBleu);
        idJoueur = this.dbaJoueur.getIdJoueur(pseudo);  
        System.out.println(idJoueur);
        // on va initialiser mouette et Requin, pour cela on vérifie si un mob a déjà été créé ou non
            //init mouette
        this.dbaMouette = new DBA_Mouette();
        if (dbaMouette.getNombreMouette()==0){ //vérifie si aucun requin n'a déjà été créé
            this.mouette = new Mouette(); //on initalise un requin avec des coords et une position à atteindre pour son déplacement
            dbaMouette.InsertMouette(500, 50, true, true);
            hoteMouette = true;
        }
        idMouette = dbaMouette.getPremierIdMouette(); //pour connaitre l'id du requin (c'est le seul donc le premier dans la BdD).
        
            //init Requin
        this.dbaRequin = new DBA_Requin();
        if (dbaRequin.getNombreRequin()==0){ //vérifie si aucun requin n'a déjà été créé
            this.requin = new Requin(unJoueur); //on initalise un requin avec des coords et une position à atteindre pour son déplacement
            dbaRequin.InsertRequin(500, 600, requin.getVaADroite());
            hoteRequin = true;
        } else{
            this.requin = new Requin(); //on initialise juste un Requin "passif"
        }
        idRequin = dbaRequin.getPremierIdRequin(); //pour connaitre l'id du requin (c'est le seul donc le premier dans la BdD).
        
            //Init boulet de canon
        this.dbaBoulets = new DBA_Boulet_2_Canon();
    }
    //__________________________________________________________________________
    //getteurs et setteurs
    
    public Joueur getJoueur(){
        return this.unJoueur;
    }
    
    public ArrayList<Boulet_2_canon> getListeBouletCanon(){
        return this.boulets;
    }
    
    public DBA_Joueur getDBAJoueur(){
        return this.dbaJoueur;
    }
    public DBA_Requin getDBARequin(){
        return this.dbaRequin;
    }
    public DBA_Mouette getDBAMouette(){
        return this.dbaMouette;
    }
    
    public int getIDJoueur(){
        return this.idJoueur;
    }
    public int getIDRequin(){
        return this.idRequin;
    }
    public int getIDMouette(){
        return this.idMouette;
    }
    
    public boolean getHoteRequin(){
        return hoteRequin;
    }
    public boolean getHoteMouette(){
        return hoteMouette;
    }
    
    //__________________________________________________________________________
    //Methodes de mise à jour
    
    public void rendu(Graphics2D contexte) {
        contexte.drawImage(this.decor, 0, 0, null);
        this.map.rendu(contexte);
        // 2. Rendu des sprites

        if(!unJoueur.getEstMort()){
            this.unJoueur.rendu(contexte);
        }

        
        // rendu des autres Joueur
        for (Joueur j : joueurs){
            j.rendu(contexte);
        }
        
        //rendu du requin
        requin.rendu(contexte);
        
        this.mouette.rendu(contexte);
        
        for (Boulet_2_canon boule : boulets){
            boule.rendu(contexte);
        }
        // 3. Rendu des textes
            //affichage du nombre de coeur
            // Dessiner les cœurs en fonction de la vie du joueur
            for (int i = 0; i < 5; i++) {
                if (i < unJoueur.getVie()) {
                    // Afficher un cœur rouge pour chaque vie
                    contexte.drawImage(coeur, 10 + (i * 40), 50, 30, 30, null); 
                } else {
                    // Afficher un cœur gris pour les vies restantes
                    contexte.drawImage(coeurG, 10 + (i * 40), 50, 30, 30, null);
                }
            }
    }
    

    public void miseAJour() {
        unJoueur.setEstMort(unJoueur.estMort());
        joueurs = dbaJoueur.getTousLesJoueursSaufUn(idJoueur);
        // Mise à jour du joueur
        if (!unJoueur.getEstMort()){
            //1. mises à jour de l'avatar en fonction des collisions avec la map

                // 1. Sauvegarde de la position actuelle du joueur
            int oldX = unJoueur.getX();
            int oldY = unJoueur.getY();

                // 2. Mise à jour du joueur (calcule ses nouvelles positions potentielles)
            unJoueur.miseAJour();

                // 3. Gestion des collisions horizontales
            if (collisionEntreJoueurEtMap( unJoueur.getX(), oldY, 
                    unJoueur.getHauteur(), unJoueur.getLargeur(), map)){
                unJoueur.setX(oldX); // Revenir à la position précédente si collision
            }
                // 4. Gestion des collisions verticales

            if (collisionEntreJoueurEtMap( unJoueur.getX(), unJoueur.getY(), 
                    unJoueur.getHauteur(), unJoueur.getLargeur(), map)){
                unJoueur.setY(oldY); // Revenir à la position précédente si collision
            }

            //on va vérifier si il y'a du sol en deddous du joueur pour pouvoir sauter
            if (collisionEntreJoueurEtMap(unJoueur.getX(), (unJoueur.getY() + 5), 
                    unJoueur.getHauteur(), unJoueur.getLargeur(), map) || (unJoueur.getY() + 5) >= 700){ // Vérifie une collision juste en dessous
                unJoueur.setEstAuSol(true);
            } else {
                unJoueur.setEstAuSol(false);
            }

            // 2. Mise à jour du joueur (calcule ses nouvelles positions potentielles)
            unJoueur.miseAJour();
        
            // 3. Gestion des collisions horizontales en prenant en compte les limites
            // de la map

            if ((unJoueur.getX()+unJoueur.getLargeur()) < map.getLargeur()){
                if (collisionEntreJoueurEtMap( unJoueur.getX(), oldY, 
                    unJoueur.getHauteur(), unJoueur.getLargeur(), map)){
                unJoueur.setX(oldX); // Revenir à la position précédente si collision
                } 
            } else{
                unJoueur.setX(oldX);
            }
            // 4. Gestion des collisions verticales
        
            if (collisionEntreJoueurEtMap( unJoueur.getX(), unJoueur.getY(), 
                    unJoueur.getHauteur(), unJoueur.getLargeur(), map)){
                unJoueur.setY(oldY); // Revenir à la position précédente si collision
            }
        
        //on va vérifier si il y'a du sol en desous du joueur pour pouvoir sauter
            if (collisionEntreJoueurEtMap(unJoueur.getX(), (unJoueur.getY() + 5), 
                    unJoueur.getHauteur(), unJoueur.getLargeur(), map) || (unJoueur.getY() + 5) >= 700){ // Vérifie une collision juste en dessous
                unJoueur.setEstAuSol(true);
            } else {
                unJoueur.setEstAuSol(false);

            }
        }
       
        // 2. Mise à jour des autres éléments (objets, monstres, etc.)
            //mise a jour du requin
        //On vérifie déjà si il y'a toujours un requin dans la BdD t si c'est pas le cas le joueur deviens le nouvel hote
        if (dbaRequin.getNombreRequin()==0){ //vérifie si aucun requin n'a déjà été créé
            this.requin = new Requin(unJoueur); //on initalise un requin avec des coords et une position à atteindre pour son déplacement
            dbaRequin.InsertRequin(500, 600, requin.getVaADroite());
            hoteRequin = true;
        }
        //on vérifie si l'ID du requin est toujours la même et sinon on la met à jour
        
        if(idRequin!=dbaRequin.getPremierIdRequin()){
            idRequin = dbaRequin.getPremierIdRequin();
        }
        
        //si hote, update le requin en prenant la liste de tout les joueurs
        if(hoteRequin){
            this.requin.miseAJour(joueurs);
            this.dbaRequin.UpdateRequin(idRequin, requin.getX(), requin.getY(),requin.getVaADroite());
        } else { //sinon on update la position du requin via la BdD
            requin = dbaRequin.getRequin(idRequin); //requin "passif" avec les coordonnées attritré dans la BdD
        }
        
        
        //même chose pour la mouette
        if (dbaMouette.getNombreMouette()==0){ //vérifie si aucun requin n'a déjà été créé
            this.mouette = new Mouette(); //on initalise un requin avec des coords et une position à atteindre pour son déplacement
            dbaMouette.InsertMouette(500, 600, true, true);
            hoteMouette = true;
        }
        
        if(idMouette!=dbaMouette.getPremierIdMouette()){
            idMouette = dbaMouette.getPremierIdMouette();
        }
        
        if(hoteMouette){
            this.mouette.miseAJour(tempsActuel);
            this.dbaMouette.updateMouette(idMouette, mouette.getX(), mouette.getY(), mouette.getVaADroite(), mouette.getOscilEnHaut());
        } else { //sinon on update la position du requin via la BdD
            mouette = dbaMouette.getMouette(idMouette); //requin "passif" avec les coordonnées attritré dans la BdD
        }
        
        
            //mise à jour des boulets de canon
        for (int n=0; n<this.boulets.size(); n++){
            boulets.get(n).miseAJour();
            if (boulets.get(n).getIDBoulet() == -1){
                dbaBoulets.InsertBoulet(boulets.get(n).getX(),boulets.get(n).getX(), idJoueur);
                idsBoulets = dbaBoulets.getIdBouletJoueur(idJoueur);
                /** dans la liste des id, l'id d'indice n corepond au boulet d'indice n dans la liste des boulets
                On va donc ajouter a chaque boulet son id dans la BdD**/
                System.out.println(idsBoulets.size());
                System.out.println(boulets.size());
                boulets.get(n).setIDBoulet(idsBoulets.get(n));
            }
            
            if (boulets.get(n).getTrajFini()){
                dbaBoulets.DeleteBoulet(boulets.get(n).getIDBoulet());
                boulets.remove(n);
                n--;
            }
        }
        
        
        // 3. Gérer les interactions
        
        if (!unJoueur.getEstMort()){
            if (collisionEntreJoueurEtRequin()){
                this.unJoueur.setX(200);
                this.unJoueur.setY(200);
                this.unJoueur.setVie(unJoueur.getVie()-1);
            }

            if (collisionEntreJoueurEtMouette()) {
                // Si une collision est détectée, on augmente la vie du joueur
                unJoueur.setVie(unJoueur.getVie() + 1);  // Augmenter la vie du joueur de 1

                // Après la collision, on déplace la mouette pour éviter les collisions répétées immédiates
                mouette.setX(0);  // Vous pouvez ajuster cette valeur pour repositionner la mouette comme vous le souhaitez
                mouette.setY(0);  // Ou ajuster la position de la mouette en fonction de votre logique
            }
        }
        
        if (collisionEntreMouetteEtBoulet()){
            this.mouette.setX(0);
        }

        //4. Envoie des données dans la base des données
        if (!unJoueur.getEstMort()){
            this.dbaJoueur.updateJoueur(idJoueur, pseudo , unJoueur.getX(), unJoueur.getY(), unJoueur.getVie(), 0, "ok");
            for (Boulet_2_canon boulet : boulets){
                //this.dbaBoulets.UpdateBoulet_2_Canon();
            }
        } else{
            this.dbaJoueur.deleteJoueur(idJoueur);
        }
        //this.dbaRequin.UpdateRequin(idRequin, requin.getX(), requin.getY(), requin.getVaADroite());
    }


    
    public boolean estTermine() {
        // Renvoie vrai si la partie est terminée (gagnée ou perdue)
        return false;
    }
    
    
    //__________________________________________________________________________

    //gestion des collisions
    public boolean collisionEntreJoueurEtRequin() {
        if ((requin.getX() >= unJoueur.getX() + unJoueur.getLargeur()) // trop à droite
                || (requin.getX() + requin.getLargeur() <= unJoueur.getX()) // trop à gauche
                || (requin.getY() >= unJoueur.getY() + unJoueur.getHauteur()) // trop en bas
                || (requin.getY() + requin.getHauteur() <= unJoueur.getY())) { // trop en haut
            return false;
        } else {
            return true;
        }
    }
    
    //les collisions entre joueur et Mouette c'est un bonous qui augmente duree de vie
    
    public boolean collisionEntreJoueurEtMouette() {
        // Vérification des coordonnées pour savoir si les hitboxes du joueur et de la mouette se chevauchent
        if ((mouette.getX() >= unJoueur.getX() + unJoueur.getLargeur()) // trop à droite
                || (mouette.getX() + mouette.getLargeurDroiteHaut()<= unJoueur.getX()) // trop à gauche
                || (mouette.getY() >= unJoueur.getY() + unJoueur.getHauteur()) // trop en bas
                || (mouette.getY() + mouette.getHauteurDroiteHaut()<= unJoueur.getY())) { // trop en haut
            return false;  // Pas de collision
        } else {
            return true;   // Collision détectée
        }
}
    //peut etre à retravailler parceque le code est dégueulasse
    
    public boolean collisionEntreMouetteEtBoulet(){
        for(Boulet_2_canon boulet : boulets){
            /** on reprend le même code que précédent, si y'a pas de collision, on fait r
            et si y'en a une on return direct true **/
            if ((boulet.getX() >= mouette.getX() + mouette.getLargeurDroiteHaut()) // trop à droite
                    || (boulet.getX() + boulet.getLargeur() <= mouette.getX()) // trop à gauche
                    || (boulet.getY() >= mouette.getY() + mouette.getHauteurDroiteHaut()) // trop en bas
                    || (boulet.getY() + boulet.getHauteur() <= mouette.getY())) { // trop en haut
            } else {
                return true;
            }
        }
        return false; 
    }
    
    
    //gérer la collision entre le joueur et les boulets (que équipe ennemie ou tous ?)
    public void collisionEntreJoueurEtBoulet(){
        
    }
    
    /** pour gérer la collision entre le joueur et la map on va faire du pixel
    perfect avec des masques
    On veut savoir en plus si les collisions
    **/
    public static boolean collisionEntreJoueurEtMap(int x, int y, int h, int l, Map map){
        /** pour chaque pixel de la hitbox du joueur on va vérifier si sur la map
         * il y'a un 1 c'est a dire une collision
         */
        for (int i = y; i<=( y + h); i++){
            for (int j = x; j<=(x + l); j++){
                if(map.getMasque()[i][j] == 1){
                    return true;
                }
            }
        }
        return false;
    }
    
    
    //même chose pour les boulets
    public boolean collisionEntreBouletEtMap(){
        
        
        
        return false;
    }
}
