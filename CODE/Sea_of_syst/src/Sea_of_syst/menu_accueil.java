/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Sea_of_syst;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 *
 * @author vruche
 */
public class menu_accueil extends javax.swing.JFrame {
    
    private Font font1;
    private JPanel startPanel;
    /**
     * Creates new form menu_accueil
     */
    public menu_accueil() {
        initComponents();
        
        // Initialisation du panneau principal
        startPanel = new JPanel();
        startPanel.setLayout(new BorderLayout()); // Définir un layout, ici BorderLayout comme exemple
        startPanel.add(getContentPane(), BorderLayout.CENTER); // Ajouter les composants initiaux

        try{
            File fontStyle = new File("src/ressources/font/Windlass.ttf");
            font1 = Font.createFont(Font.TRUETYPE_FONT, fontStyle).deriveFont(20f);
        } catch(Exception e){
            e.printStackTrace();
        }
        setContentPane(startPanel);
        setTitle("Sea of Syst");
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setSize(1216, 832);                     //taille de la fenêtre
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroupChooseTeam = new javax.swing.ButtonGroup();
        ButtonJoin = new javax.swing.JButton();
        TextFieldPseudo = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        RadioButtonBlue = new javax.swing.JRadioButton();
        RadioButtonRed = new javax.swing.JRadioButton();
        LabelBackground = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sea of Syst menu");
        setBounds(new java.awt.Rectangle(0, 0, 800, 550));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        ButtonJoin.setFont(font1);
        ButtonJoin.setText("Rejoindre la partie");
        ButtonJoin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonJoinActionPerformed(evt);
            }
        });
        getContentPane().add(ButtonJoin, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 650, 164, 56));
        getContentPane().add(TextFieldPseudo, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 270, 186, 31));

        jLabel1.setFont(font1);
        jLabel1.setText("Choisir un pseudo");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 230, 110, 40));

        buttonGroupChooseTeam.add(RadioButtonBlue);
        RadioButtonBlue.setText("Team Blue");
        getContentPane().add(RadioButtonBlue, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 610, -1, 30));
        RadioButtonBlue.getAccessibleContext().setAccessibleDescription("");

        buttonGroupChooseTeam.add(RadioButtonRed);
        RadioButtonRed.setText("Team Red");
        getContentPane().add(RadioButtonRed, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 610, -1, 30));

        LabelBackground.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ressources/background.png"))); // NOI18N
        getContentPane().add(LabelBackground, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1210, 830));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ButtonJoinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonJoinActionPerformed
        // Vérification du champ pseudo
        String pseudo = TextFieldPseudo.getText().trim();
        if (pseudo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez entrer un pseudo avant de rejoindre la partie.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Vérification de la sélection de l'équipe
        if (!RadioButtonBlue.isSelected() && !RadioButtonRed.isSelected()) {
            JOptionPane.showMessageDialog(this, "Veuillez choisir une équipe avant de rejoindre la partie.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Déterminer si l'équipe choisie est Team Blue
        boolean teamBlue = RadioButtonBlue.isSelected();
        
        // Si tout est valide, afficher un message de confirmation (ou effectuer l'action correspondante)
        //String equipe = RadioButtonBlue.isSelected() ? "Team Blue" : "Team Red";
        //JOptionPane.showMessageDialog(this, "Pseudo : " + pseudo + "\nÉquipe : " + equipe + "\nRejoindre la partie...", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
        
        // Lancer FenetreDeJeu
        this.setVisible(false); // Cache la fenêtre actuelle
        FenetreDeJeu fenetre = new FenetreDeJeu(pseudo, teamBlue);
        fenetre.setVisible(true);
    }//GEN-LAST:event_ButtonJoinActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(menu_accueil.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(menu_accueil.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(menu_accueil.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(menu_accueil.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {    
            public void run() {
                new menu_accueil().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ButtonJoin;
    private javax.swing.JLabel LabelBackground;
    private javax.swing.JRadioButton RadioButtonBlue;
    private javax.swing.JRadioButton RadioButtonRed;
    private javax.swing.JTextField TextFieldPseudo;
    private javax.swing.ButtonGroup buttonGroupChooseTeam;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}
