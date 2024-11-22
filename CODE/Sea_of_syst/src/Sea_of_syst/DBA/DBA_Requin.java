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
public class DBA_Requin {
    
     // Select Requin
    public String getRequin(int id_requin){
        Connection connexion = SQL.getConnection();
        try {
            PreparedStatement requete = connexion.prepareStatement("SELECT x, y FROM requin WHERE id_requin = ?");
            requete.setInt(1, id_requin);
            ResultSet resultat = requete.executeQuery();
            requete.close();
            connexion.close();
            if (resultat.next()){
                return resultat.getDouble(1)+ " " + resultat.getDouble(2);       
            }else{
                return "Impossible de trouvé le Requin";
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    // Delete Requin
    public void DeleteRequin(int id_requin){
        Connection connexion = SQL.getConnection();
        try {
            PreparedStatement requete = connexion.prepareStatement("DELETE FROM requin WHERE id_requin = ?");
            requete.setInt(1, id_requin);
            requete.executeUpdate();
            requete.close();
            connexion.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    // Update Requin
      public void UpdateRequin(int id_requin, double x, double y ){
        Connection connexion = SQL.getConnection();
        try {
            PreparedStatement requete = connexion.prepareStatement("UPDATE requin SET  x = ?" + ", y = ?  WHERE id_requin = ?");
            requete.setDouble(1, x);
            requete.setDouble(2, y);
            requete.setInt(3, id_requin);
            requete.executeUpdate();
            requete.close();
            connexion.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
  
    // Insert  Requiun
    public void InsertRequin( double x, double y ){
     Connection connexion = SQL.getConnection();
     try {
         PreparedStatement requete = connexion.prepareStatement("INSERT INTO requin ( x, y) VALUES ( ?, ? )");
         requete.setDouble(1, x);
         requete.setDouble(2, y);
         requete.executeUpdate();
         requete.close();
         connexion.close();
     } catch (SQLException ex) {
         ex.printStackTrace();
     }
    }
    
//    // test  insert
//    public static void main(String[] args){
//         DBA_Requin rq = new DBA_Requin();
//         rq.DeleteRequin(7,40,50);
//    }
    
    // test insert
//     public static void main(String[] args){
//         DBA_Requin rq = new DBA_Requin();
//         rq.DeleteRequin(6);
//    }
}

