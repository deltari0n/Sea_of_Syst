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
public class DBA_Requin {
    
    Connection connexion;
    
    public DBA_Requin(){
        connexion = SingletonJDBC.getInstance().getConnection();
    }
    
     // Select Requin
    public int[] getRequin(int id_requin){
        int[] pos = new int[2];
        try {
            PreparedStatement requete = connexion.prepareStatement("SELECT x, y FROM requin WHERE id_requin = ?");
            requete.setInt(1, id_requin);
            ResultSet resultat = requete.executeQuery();
            if (resultat.next()){
                pos[0] = resultat.getInt(1);
                pos[1] = resultat.getInt(2);
                return pos;       
            }else{
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    // Delete Requin
    public void DeleteRequin(int id_requin){
        try {
            PreparedStatement requete = connexion.prepareStatement("DELETE FROM requin WHERE id_requin = ?");
            requete.setInt(1, id_requin);
            requete.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public int getNombreRequin() {
        String sql = "SELECT COUNT(*) AS total FROM requin";
        int nombreDeRequin = 0; // Initialisation par défaut

        try (PreparedStatement requete = connexion.prepareStatement(sql);
             ResultSet resultat = requete.executeQuery()) {
            if (resultat.next()) {
                nombreDeRequin = resultat.getInt("total"); // Récupère le résultat
            }
        } catch (SQLException ex) {
            System.err.println("Erreur lors de la récupération du nombre de joueurs.");
            ex.printStackTrace();
        }

        return nombreDeRequin; // Retourne le nombre de joueurs
    }
    
    
    //Méthode pour obtenir l'id du reuqin, on considère qu'il y'en a un seul et
    //qu'il se situe donc sur la première ligne de la BdD
    public int getPremierIdRequin() {
        String sql = "SELECT id_requin FROM requin ORDER BY id_requin ASC LIMIT 1";
        int idRequin = -1; // Valeur par défaut si aucun requin n'est trouvé

        try (PreparedStatement requete = connexion.prepareStatement(sql);
             ResultSet resultat = requete.executeQuery()) {
            if (resultat.next()) {
                idRequin = resultat.getInt("id_requin"); // Récupère l'ID du premier requin
            }
        } catch (SQLException ex) {
            System.err.println("Erreur lors de la récupération de l'ID du premier joueur.");
            ex.printStackTrace();
        }

        return idRequin;
    }
    
    // Update Requin
      public void UpdateRequin(int id_requin, int x, int y ){
        try {
            PreparedStatement requete = connexion.prepareStatement("UPDATE requin SET  x = ?" + ", y = ?  WHERE id_requin = ?");
            requete.setInt(1, x);
            requete.setInt(2, y);
            requete.setInt(3, id_requin);
            requete.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
  
    // Insert  Requiun
    public void InsertRequin( int x, int y ){
     try {
         PreparedStatement requete = connexion.prepareStatement("INSERT INTO requin ( x, y) VALUES ( ?, ? )");
         requete.setInt(1, x);
         requete.setInt(2, y);
         requete.executeUpdate();
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

