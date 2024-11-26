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
public class DBA_Mouette {
    // Select Mouette
     public String getMouette(int id_mouette){
        Connection connexion = SingletonJDBC.getInstance().getConnection();
        try {
            PreparedStatement requete = connexion.prepareStatement("SELECT x, y FROM mouette WHERE id_mouette = ?");
            requete.setInt(1, id_mouette);
            ResultSet resultat = requete.executeQuery();
            if (resultat.next()){
                return resultat.getDouble(1)+ " " + resultat.getDouble(2);       
            }else{
                return "Impossible de trouvé le Mouette";
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
     
    // Delete Mouette
     public void DeleteMouette(int id_mouette){
        Connection connexion = SingletonJDBC.getInstance().getConnection();
        try {
            PreparedStatement requete = connexion.prepareStatement("DELETE FROM mouette WHERE id_mouette = ?");
            requete.setInt(1, id_mouette);
            requete.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    // Update Mouette
    
    public void UpdateMouette(int id_mouette, double x, double y ){
        Connection connexion = SingletonJDBC.getInstance().getConnection();
        try {
            PreparedStatement requete = connexion.prepareStatement("UPDATE mouette SET  x = ?" + ", y = ?  WHERE id_mouette = ?");
            requete.setDouble(1, x);
            requete.setDouble(2, y);
            requete.setInt(3, id_mouette);
            requete.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
  
}
