/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Sea_of_syst.DBA;

import java.sql.Connection;
import Sea_of_syst.SingletonJDBC;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author PC
 */
public class DBA_Boulet_2_Canon {
    
    // Select Boulet_2_Canon
     public String getBoulet(int id_boulet){
        Connection connexion = SingletonJDBC.getInstance().getConnection();
        try {
            PreparedStatement requete = connexion.prepareStatement("SELECT x, y FROM boulet_de_canon WHERE id_boulet = ?");
            requete.setInt(1, id_boulet);
            ResultSet resultat = requete.executeQuery();
            if (resultat.next()){
                return resultat.getDouble(1)+ " " + resultat.getDouble(2);       
            }else{
                return "Impossible de trouvé le Boulet";
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    // Delete Boulet_2_Canon
     public void DeleteBoulet(int id_boulet){
        Connection connexion = SingletonJDBC.getInstance().getConnection();
        try {
            PreparedStatement requete = connexion.prepareStatement("DELETE FROM boulet WHERE id_boulet = ?");
            requete.setInt(1, id_boulet);
            requete.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
     
    // Update Boulet_2_Canon
       public void UpdateBoulet_2_Canon(int id_boulet, double x, double y ){
        Connection connexion = SingletonJDBC.getInstance().getConnection();
        try {
            PreparedStatement requete = connexion.prepareStatement("UPDATE boulet SET  x = ?" + ", y = ?  WHERE id_boulet = ?");
            requete.setDouble(1, x);
            requete.setDouble(2, y);
            requete.setInt(3, id_boulet);
            requete.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
     // Insert  boulet_de_canon
       public void InsertBoulet( double x, double y ){
        Connection connexion = SingletonJDBC.getInstance().getConnection();
        try {
            PreparedStatement requete = connexion.prepareStatement("INSERT INTO boulet ( x, y) VALUES ( ?, ? )");
            requete.setDouble(1, x);
            requete.setDouble(2, y);
            requete.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }   
}
