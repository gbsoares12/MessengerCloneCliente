/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.clientemessengerclone.view;

import com.mycompany.clientemessengerclone.controller.ComunicacaoServidorImpl;
import com.mycompany.clientemessengerclone.controller.Observador;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Gabriel Soares
 */
public class Login extends javax.swing.JFrame implements Observador {

    private static Login instance;
    private ComunicacaoServidorImpl controller = ComunicacaoServidorImpl.getInstance();

    public static Login getInstance() {
        if (instance == null) {
            instance = new Login();
        }

        return instance;
    }

    /**
     * Creates new form Principal
     */
    public Login() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.controller.addObservador(this);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSeparator1 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jTextFieldEmailLogin = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTextFieldSenhaLogin = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        jButtonEntrar = new javax.swing.JButton();
        jButtonRegistrar = new javax.swing.JButton();
        jButtonConfiguracao = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Verdana", 3, 18)); // NOI18N
        jLabel1.setText("MessengerClone");

        jLabel2.setText("Email:");

        jTextFieldEmailLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldEmailLoginActionPerformed(evt);
            }
        });

        jLabel3.setText("Senha:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(80, 80, 80)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextFieldEmailLogin)
                    .addComponent(jTextFieldSenhaLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(90, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextFieldEmailLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextFieldSenhaLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 22, Short.MAX_VALUE))
        );

        jButtonEntrar.setText("Entrar");
        jButtonEntrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEntrarActionPerformed(evt);
            }
        });

        jButtonRegistrar.setText("Registrar");
        jButtonRegistrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRegistrarActionPerformed(evt);
            }
        });

        jButtonConfiguracao.setText("Configuração");
        jButtonConfiguracao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonConfiguracaoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(jSeparator2)
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jButtonConfiguracao)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jButtonRegistrar)
                        .addGap(34, 34, 34)
                        .addComponent(jButtonEntrar, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(118, 118, 118))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(132, 132, 132))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jButtonConfiguracao)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonEntrar)
                    .addComponent(jButtonRegistrar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonEntrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEntrarActionPerformed

        try {
            if (this.controller.entrar(jTextFieldEmailLogin.getText(), jTextFieldSenhaLogin.getText())) {
                MenuPrincipal menuPrincipal = MenuPrincipal.getInstance();
                menuPrincipal.setVisible(true);
                this.setVisible(false);
            }
        } catch (IOException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }


    }//GEN-LAST:event_jButtonEntrarActionPerformed

    private void jButtonRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRegistrarActionPerformed
        MenuCadastro menuCadastro = MenuCadastro.getInstance();
        menuCadastro.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_jButtonRegistrarActionPerformed

    private void jButtonConfiguracaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonConfiguracaoActionPerformed
        MenuConfiguracao menuConfig = MenuConfiguracao.getInstance();
        menuConfig.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_jButtonConfiguracaoActionPerformed

    private void jTextFieldEmailLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldEmailLoginActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldEmailLoginActionPerformed

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
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonConfiguracao;
    private javax.swing.JButton jButtonEntrar;
    private javax.swing.JButton jButtonRegistrar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTextField jTextFieldEmailLogin;
    private javax.swing.JTextField jTextFieldSenhaLogin;
    // End of variables declaration//GEN-END:variables

    @Override
    public void notificaMensagemRespostaServidor(String responseDoServer) {
        JOptionPane.showMessageDialog(null, responseDoServer);
    }

    @Override
    public void exibeInfoClienteLogado() {}

    @Override
    public void atualizarListaContato() {}

    @Override
    public void exibeUserBuscado(String nome, String email) {}

    @Override
    public int escolhaAdicionar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void exibeMsg(String msg) {}

}
