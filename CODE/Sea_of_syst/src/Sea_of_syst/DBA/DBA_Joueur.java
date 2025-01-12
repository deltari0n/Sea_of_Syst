/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Sea_of_syst.DBA;

import Sea_of_syst.Joueur;
import java.sql.Connection;
import Sea_of_syst.SingletonJDBC;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Statement;

/**
 *
 * @author PC
 */
public class DBA_Joueur {

    Connection connexion;
    
    public DBA_Joueur(){
        connexion = SingletonJDBC.getInstance().getConnection();
    }
    
    public int getNombreDeJoueurs() {
        String sql = "SELECT COUNT(*) AS total FROM joueur";
        int nombreDeJoueurs = 0; // Initialisation par défaut

        try (PreparedStatement requete = connexion.prepareStatement(sql);
             ResultSet resultat = requete.executeQuery()) {
            if (resultat.next()) {
                nombreDeJoueurs = resultat.getInt("total"); // Récupère le résultat
            }
        } catch (SQLException ex) {
            System.err.println("Erreur lors de la récupération du nombre de joueurs.");
            ex.printStackTrace();
        }

        return nombreDeJoueurs; // Retourne le nombre de joueurs
    }
    
    // Select joueur
    public List<Object> getJoueur(int id_joueur) {
        List<Object> joueurData = new ArrayList<>();
        try {
            PreparedStatement requete = connexion.prepareStatement(
                    "SELECT username, x, y, niveau_vie, score, avatar, team FROM joueur WHERE id_joueur = ?");
            requete.setInt(1, id_joueur);
            ResultSet resultat = requete.executeQuery();
            if (resultat.next()) {
                joueurData.add(resultat.getString("username"));  // Nom d'utilisateur
                joueurData.add(resultat.getDouble("x"));         // Coordonnée x
                joueurData.add(resultat.getDouble("y"));         // Coordonnée y
                joueurData.add(resultat.getInt("niveau_vie"));   // Niveau de vie
                joueurData.add(resultat.getInt("score"));        // Score
                joueurData.add(resultat.getString("avatar"));    // Avatar
                joueurData.add(resultat.getString("team"));     //team
            } else {
                joueurData.add("Impossible de trouver le joueur");
            }
            // Fermer la requête et la connexion
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return joueurData;
    }
    
    
    public List<Joueur> getTousLesJoueursSaufUn(int idJoueurExclu) {
        List<Joueur> joueurs = new ArrayList<>();
        String sql = "SELECT id_joueur, username, x, y, score, score, avatar, team FROM joueur WHERE id_joueur != ?";

        try {
            PreparedStatement requete = connexion.prepareStatement(sql);

            // Paramétrer la requête pour exclure le joueur avec l'id spécifié
            requete.setInt(1, idJoueurExclu);

            // Exécuter la requête
            try (ResultSet resultat = requete.executeQuery()) {
                while (resultat.next()) {
                    // Créer un objet Joueur pour chaque ligne récupérée
                    Joueur joueur = new Joueur(
                        resultat.getInt("id_joueur"),
                        resultat.getString("username"),
                        resultat.getInt("x"),
                        resultat.getInt("y"),
                        resultat.getInt("score"),
                        resultat.getString("avatar"),
                        resultat.getBoolean("team")
                    );
                    joueurs.add(joueur);  // Ajouter le joueur à la liste
                }
            }

        } catch (SQLException ex) {
            System.err.println("Erreur lors de la récupération des joueurs sauf l'ID : " + idJoueurExclu);
            ex.printStackTrace();
        }

    return joueurs;
}

    
    
    
    public int getIdJoueur(String pseudo) {
    String sql = "SELECT id_joueur FROM joueur WHERE username = ?";
    int idJoueur = -1; // Valeur par défaut si aucun joueur n'est trouvé

    try { // Récupération de la connexion
        PreparedStatement requete = connexion.prepareStatement(sql);

        // Paramétrer la requête
        requete.setString(1, pseudo);

        // Exécuter la requête
        try (ResultSet resultat = requete.executeQuery()) {
            // Vérifier si un résultat existe
            if (resultat.next()) {
                idJoueur = resultat.getInt("id_joueur"); // Récupérer l'ID
            }
        }

    } catch (SQLException ex) {
        // Gestion de l'erreur
        System.err.println("Erreur lors de la récupération de l'ID du joueur pour le pseudo : " + pseudo);
        ex.printStackTrace();
    }

    return idJoueur; // Retourne -1 si aucun joueur n'est trouvé
    }
    
        // Delete Joueur 
    public void deleteJoueur(int id_joueur) {
        try {
            PreparedStatement requete = connexion.prepareStatement("DELETE FROM joueur WHERE id_joueur = ?");
            requete.setInt(1, id_joueur);
            requete.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Insert Joueur 
    public void insertJoueur(String username, double x, double y, int niveau_vie, int score, String avatar, boolean team) {
        try {
            PreparedStatement requete = connexion.prepareStatement("INSERT INTO joueur (username, x, y, niveau_vie, score, avatar, team) VALUES (?, ?, ?, ?, ?, ?, ?)");
            requete.setString(1, username);
            requete.setDouble(2, x);
            requete.setDouble(3, y);
            requete.setInt(4, niveau_vie);
            requete.setInt(5, score);
            requete.setString(6, avatar);
            requete.setBoolean(7, team);
            requete.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Update Joueur 
    public void updateJoueur(int id_joueur, String username, double x, double y, int niveau_vie, int score, String avatar) {
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
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void main(String[] args) {
    }

}