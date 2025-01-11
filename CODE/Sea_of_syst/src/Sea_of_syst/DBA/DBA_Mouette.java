/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Sea_of_syst.DBA;

import Sea_of_syst.Mouette;
import java.sql.Connection;
import Sea_of_syst.SingletonJDBC;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author PC
 */
public class DBA_Mouette {
    
    Connection connexion;
    
    public DBA_Mouette(){
        connexion = SingletonJDBC.getInstance().getConnection();
    }
    
    //insert Mouette
    
    public void InsertMouette( double x, double y, boolean vaDroite, boolean oscillHaut){
     try {
         PreparedStatement requete = connexion.prepareStatement("INSERT INTO mouette ( x, y, vaADroite, oscillEnHaut) VALUES ( ?, ?, ?, ?)");
         requete.setDouble(1, x);
         requete.setDouble(2, y);
         requete.setBoolean(3, vaDroite);
         requete.setBoolean(4, oscillHaut);
         requete.executeUpdate();
     } catch (SQLException ex) {
         ex.printStackTrace();
     }
    }
    
    // Select Mouette
    public Mouette getMouette(int id_mouette){
        try {
            PreparedStatement requete = connexion.prepareStatement("SELECT x, y, vaADroite, oscillEnHaut FROM mouette WHERE id_mouette = ?");
            requete.setInt(1, id_mouette);
            ResultSet resultat = requete.executeQuery();
            if (resultat.next()){
                Mouette ChroicocephalusRidibundus = new Mouette(
                        resultat.getDouble("x"),
                        resultat.getDouble("y"),
                        resultat.getBoolean("vaADroite"),
                        resultat.getBoolean("oscillEnHaut"));
                return ChroicocephalusRidibundus;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
     
    // Delete Mouette
     public void DeleteMouette(int id_mouette){
        try {
            PreparedStatement requete = connexion.prepareStatement("DELETE FROM mouette WHERE id_mouette = ?");
            requete.setInt(1, id_mouette);
            requete.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public int getNombreMouette() {
        String sql = "SELECT COUNT(*) AS total FROM mouette";
        int nombreDeMouette = 0; // Initialisation par défaut

        try (PreparedStatement requete = connexion.prepareStatement(sql);
             ResultSet resultat = requete.executeQuery()) {
            if (resultat.next()) {
                nombreDeMouette = resultat.getInt("total"); // Récupère le résultat
            }
        } catch (SQLException ex) {
            System.err.println("Erreur lors de la récupération du nombre de mouette");
            ex.printStackTrace();
        }

        return nombreDeMouette; // Retourne le nombre de joueurs
    } 
     
    public int getPremierIdMouette() {
        String sql = "SELECT id_mouette FROM mouette ORDER BY id_mouette ASC LIMIT 1";
        int idMouette = -1; // Valeur par défaut si aucun requin n'est trouvé

        try (PreparedStatement requete = connexion.prepareStatement(sql);
             ResultSet resultat = requete.executeQuery()) {
            if (resultat.next()) {
                idMouette = resultat.getInt("id_mouette"); // Récupère l'ID de la première mouette
            }
        } catch (SQLException ex) {
            System.err.println("Erreur lors de la récupération de l'ID de la mouette.");
            ex.printStackTrace();
        }

        return idMouette;
    }
    
    // Update Mouette
    
    public void updateMouette(int id_mouette, double x, double y, boolean vaDroite, boolean oscillHaut){
        try {
            PreparedStatement requete = connexion.prepareStatement("UPDATE mouette SET  x = ?, y = ?, vaADroite = ?, oscillEnHaut = ?  WHERE id_mouette = ?");
            requete.setDouble(1, x);
            requete.setDouble(2, y);
            requete.setBoolean(3, vaDroite);
            requete.setBoolean(4, oscillHaut);
            requete.setInt(5, id_mouette);
            requete.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
  
}
