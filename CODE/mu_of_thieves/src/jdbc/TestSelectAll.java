/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jdbc;

import outils.OutilsJDBC;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import mu_of_thieves.SQL;

/**
 *
 * @author guillaume.laurent
 */
public class TestSelectAll {

    public static void main(String[] args) {

        try {
            Connection con = SQL.getConnection();
            PreparedStatement requete = con.prepareStatement("SELECT * FROM joueur");
            ResultSet resultat = requete.executeQuery();
            OutilsJDBC.afficherResultSet(resultat);
            requete.close();
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

}
