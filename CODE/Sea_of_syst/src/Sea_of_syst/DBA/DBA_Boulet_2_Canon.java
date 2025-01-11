/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Sea_of_syst.DBA;

import Sea_of_syst.Boulet_2_canon;
import java.sql.Connection;
import Sea_of_syst.SingletonJDBC;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author PC
 */
public class DBA_Boulet_2_Canon {
    
    Connection connexion;
    
    public DBA_Boulet_2_Canon(){
        connexion = SingletonJDBC.getInstance().getConnection();
    }
    
    // Select Boulet_2_Canon
     public String getBoulet(int id_boulet){

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
     
    public List<Boulet_2_canon> getToutLesBouletsSaufANous(int idJoueurExclu){
        List<Boulet_2_canon> boulets = new ArrayList<>();
        String sql = "SELECT id_boulet, x, y, id_joueur FROM boulet_de_canon WHERE id_joueur != ?";
        try {
            PreparedStatement requete = connexion.prepareStatement(sql);

            // Paramétrer la requête pour exclure le joueur avec l'id spécifié
            requete.setInt(1, idJoueurExclu);

            // Exécuter la requête
            try (ResultSet resultat = requete.executeQuery()) {
                while (resultat.next()) {
                    // Créer un objet Boulet_2_canon pour chaque ligne récupérée
                    Boulet_2_canon boulet = new Boulet_2_canon(
                        resultat.getInt("id_boulet"),
                        resultat.getInt("x"),
                        resultat.getInt("y"),
                        resultat.getInt("id_joueur"));
                    boulets.add(boulet);  // Ajouter le joueur à la liste
                }
            }
        } catch (SQLException ex) {
            System.err.println("Erreur lors de la récupération des boulets");
            ex.printStackTrace();
        }
        return boulets;
    }
    
    public ArrayList<Integer> getIdBouletJoueur(int idJ){
        ArrayList<Integer> ids = new ArrayList<>();
        String sql = "SELECT id_boulet FROM boulet_de_canon WHERE boulet_de_canon.id_joueur = ?";
        try {
            PreparedStatement requete = connexion.prepareStatement(sql);

            // Paramétrer la requête pour exclure le joueur avec l'id spécifié
            requete.setInt(1, idJ);

            // Exécuter la requête
            try (ResultSet resultat = requete.executeQuery()) {
                while (resultat.next()) {
                    ids.add(resultat.getInt("id_joueur")); 
                }
            }
        } catch (SQLException ex) {
            System.err.println("Erreur lors de la récupération des boulets");
            ex.printStackTrace();
        }
        return ids;
    }
    
    // Delete Boulet_2_Canon
     public void DeleteBoulet(int id_boulet){

        try {
            PreparedStatement requete = connexion.prepareStatement("DELETE FROM boulet_de_canon WHERE id_boulet = ?");
            requete.setInt(1, id_boulet);
            requete.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
     
    // Update Boulet_2_Canon
    public void UpdateBoulet_2_Canon(int id_boulet, double x, double y ){

        try {
            PreparedStatement requete = connexion.prepareStatement("UPDATE boulet_de_canon SET  x = ?" + ", y = ?  WHERE id_boulet = ?");
            requete.setDouble(1, x);
            requete.setDouble(2, y);
            requete.setInt(3, id_boulet);
            requete.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
     // Insert  boulet_de_canon
    public void InsertBoulet(int x, int y, int idJ) {
        try {
            // Vérifiez si le joueur existe dans la table joueur
            String checkPlayerQuery = "SELECT COUNT(*) FROM joueur WHERE id_joueur = ?";
            PreparedStatement checkStmt = connexion.prepareStatement(checkPlayerQuery);
            checkStmt.setInt(1, idJ);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                // Si le joueur existe, insérez le boulet
                String insertQuery = "INSERT INTO boulet_de_canon (x, y, id_joueur) VALUES (?, ?, ?)";
                PreparedStatement insertStmt = connexion.prepareStatement(insertQuery);
                insertStmt.setInt(1, x);
                insertStmt.setInt(2, y);
                insertStmt.setInt(3, idJ);
                insertStmt.executeUpdate();
                System.out.println("Boulet inséré avec succès.");
            } else {
                // Si le joueur n'existe pas, affichez un message
                System.err.println("Erreur : Aucun joueur avec l'ID " + idJ + " n'existe.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
