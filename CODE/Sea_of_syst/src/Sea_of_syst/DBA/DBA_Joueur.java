/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Sea_of_syst.DBA;

import java.sql.Connection;
import Sea_of_syst.SQL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author PC
 */
public class DBA_Joueur {
    
  // Select joueur
   public List<Object> getJoueur(int id_joueur) {
    Connection connexion = SQL.getConnection();
    List<Object> joueurData = new ArrayList<>();
    try {
        PreparedStatement requete = connexion.prepareStatement(
            "SELECT username, x, y, niveau_vie, score, avatar FROM joueur WHERE id_joueur = ?");
        requete.setInt(1, id_joueur);
        ResultSet resultat = requete.executeQuery();
        ResultSet resultat = requete.executeQuery();   
        if (resultat.next()) {
            joueurData.add(resultat.getString("username"));  // Nom d'utilisateur
            joueurData.add(resultat.getDouble("x"));         // Coordonnée x
            joueurData.add(resultat.getDouble("y"));         // Coordonnée y
            joueurData.add(resultat.getInt("niveau_vie"));   // Niveau de vie
            joueurData.add(resultat.getInt("score"));        // Score
            joueurData.add(resultat.getString("avatar"));    // Avatar
        } else {
            joueurData.add("Impossible de trouver le joueur");
        }

        }
        // Fermer la requête et la connexion
        resultat.close();
        requete.close();
        connexion.close();
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    return joueurData;
    
    // Delete Joueur 
    public void DeleteJoueur(int id_joueur){
        Connection connexion = SQL.getConnection();
        try {
            PreparedStatement requete = connexion.prepareStatement("DELETE FROM joueur WHERE id_joueur = ?");
            requete.setInt(1, id_joueur);
            requete.executeUpdate();
            requete.close();
            connexion.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    
    // Insert Joueur 
    public void InsertJoueur(String username, double x, double y, int niveau_vie, int score, String avatar){
        Connection connexion = SQL.getConnection();
        try {
            PreparedStatement requete = connexion.prepareStatement("INSERT INTO joueur (username, x, y, niveau_vie, score, avatar) VALUES (?, ?, ?, ?, ?, ? )");
            requete.setString(1, username);
            requete.setDouble(2, x);
            requete.setDouble(3, y);
            requete.setInt(4, niveau_vie);
            requete.setInt(5, score);
            requete.setString(6, avatar);
            requete.executeUpdate();
            requete.close();
            connexion.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
        
    // Update Joueur 
    public void UpdateJoueur(int id_joueur, String username, double x, double y, int niveau_vie, int score, String avatar){
        Connection connexion = SQL.getConnection();
        try {
             PreparedStatement requete = connexion.prepareStatement("UPDATE joueur SET username = ?, x = ?"
                     + ", y = ?, niveau_vie = ?, score = ?, avatar = ? WHERE id_joueur = ?");
            requete.setString(1, username);
            requete.setDouble(2, x);
            requete.setDouble(3, y);
            requete.setInt(4, niveau_vie);
            requete.setInt(5, score);
            requete.setString(6, avatar);
            requete.setInt(7, id_joueur);
            requete.executeUpdate();
            requete.close();
            connexion.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

//    public static void main(String[] args){
//        DBA_Joueur dba = new DBA_Joueur();
//        dba.UpdateJoeur(6,"abdelhakim 2", 10, 10, 12, 12, "avatar");
//   
    public static void main(String[] args){
        DBA_Joueur dba = new DBA_Joueur();
        dba.UpdateJoeur(6,"abdelhakim 2", 10, 10, 12, 12, "avatar");
   
    }  
    
}

//     public static void main(String[] args) {
//    // Création d'une instance de la classe DBA_Joueur
//    DBA_Joueur dbaJoueur = new DBA_Joueur();
//    // Appel de la méthode getJoueur pour récupérer les données du joueur avec id = 1
//    List<Object> joueur = dbaJoueur.getJoueur(1);
//    // Vérification du contenu de la liste et affichage
//    if (!joueur.isEmpty() && joueur.size() > 1) {
//        System.out.println("Données du joueur : " + joueur);
//    } else {
//        System.out.println(joueur.get(0)); // Message d'erreur
//    }
//}

 

