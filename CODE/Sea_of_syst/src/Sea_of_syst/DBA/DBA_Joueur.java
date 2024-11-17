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

/**
 *
 * @author PC
 */
public class DBA_Joueur {
    

    // Select joueur
    public String getJoueur(int id_joueur){
        Connection connexion = SQL.getConnection();
        try {
            PreparedStatement requete = connexion.prepareStatement("SELECT username, x, y, niveau_vie, score, avatar FROM joueur WHERE id_joueur = ?");
            requete.setInt(1, id_joueur);
            ResultSet resultat = requete.executeQuery();
            requete.close();
            connexion.close();
            if (resultat.next()){
                return resultat.getString(1) + " " + resultat.getDouble(2)+ " " + resultat.getDouble(3) + " " + resultat.getInt(4)
                        + " " + resultat.getInt(5) + " " + resultat.getString(6);
            }else{
                return "Impossible de trouvé le joueur";
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    // Delete Joeur 
    public void DeleteJoeur(int id_joueur){
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
    
    
    // Insert Joeur 
    public void InsertJoeur(String username, double x, double y, int niveau_vie, int score, String avatar){
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
    
        
    // Update Joeur 
    public void UpdateJoeur(int id_joueur, String username, double x, double y, int niveau_vie, int score, String avatar){
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
    
    public static void main(String[] args){
        DBA_Joueur dba = new DBA_Joueur();
        dba.UpdateJoeur(6,"abdelhakim 2", 10, 10, 12, 12, "avatar");
   
    }
    
    
    
    
}
