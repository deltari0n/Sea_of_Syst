/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
*/
package mu_of_thieves;

//import outils.OutilsJDBC;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQL {
    
    //Attribut static on peut l'acceder avec le nom de la classe
    private static Connection connection = null;
    private static String username="etudiant";
    private static String url="jdbc:mariadb://nemrod.ens2m.fr:3306/2024-2025_s1_vs2_tp2_mu_of_thieves";
    private static String password="YTDTvj9TR3CDYCmP";
    //constructeurs privee pour respecter le principe de singleton 
    //Empeche les autres objets de creer un objet SQL
    private SQL(){ 
    }
    
    // Methode pour acceder a l'objet connection
    // Cette methode est la seule maniere pour acceder a l'objet Connection
    public static Connection getConnection(){
        // si objet deja exite return connection
        if (connection != null)
            return connection;
        // Sinon crrer l'objet connetion 
        else {
            try {
            // Connexion à la base de données
            connection = DriverManager.getConnection(url,username,password);
            System.out.println("Connecté à la base de données MariaDB.");
            return connection;
            //Si une execption de declanche
            } catch (SQLException ex) {
                ex.printStackTrace();
            } 
        }
        return null;
    }
    

    
    
}
    

