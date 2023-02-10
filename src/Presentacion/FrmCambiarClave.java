/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Presentacion;

import Negocio.EUsuarios;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import AccesoDatos.Hash;
import Modelos.Usuarios;


public class FrmCambiarClave extends javax.swing.JFrame {
    
    Usuarios clsUsuario = new Usuarios();
    private int id;
    
    /**
     * Creates new form FrmCambiarClave
     */
    public FrmCambiarClave() {
        initComponents();
        this.setLocationRelativeTo(this);
        this.setResizable(false);
        
        this.txtClave.setEnabled(false);
        this.txtClave2.setEnabled(false);
        this.btnCambiarClave.setEnabled(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtToken = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        btnValidar = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        btnCambiarClave = new javax.swing.JButton();
        txtClave = new javax.swing.JPasswordField();
        txtClave2 = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cambiar contraseña");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel1.setText("TOKEN");

        txtToken.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("NUEVA CONTRASEÑA");

        btnValidar.setBackground(new java.awt.Color(70, 70, 255));
        btnValidar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnValidar.setForeground(new java.awt.Color(255, 255, 255));
        btnValidar.setText("VALIDAR");
        btnValidar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnValidarActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("REPETIR CONTRASEÑA");

        btnCambiarClave.setBackground(new java.awt.Color(70, 70, 255));
        btnCambiarClave.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnCambiarClave.setForeground(new java.awt.Color(255, 255, 255));
        btnCambiarClave.setText("CAMBIAR CONTRASEÑA");
        btnCambiarClave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCambiarClaveActionPerformed(evt);
            }
        });

        txtClave.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        txtClave2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnCambiarClave)
                    .addComponent(txtClave, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtToken, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33)
                        .addComponent(btnValidar))
                    .addComponent(txtClave2, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(50, 50, 50))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtToken, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnValidar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtClave, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtClave2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnCambiarClave, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCambiarClaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCambiarClaveActionPerformed
        // TODO add your handling code here:

        try{

            String message;
            String clave1 = new String(this.txtClave.getPassword()).trim();
            String clave2 = new String(this.txtClave2.getPassword()).trim();

            if(clave1.equals("")){
                this.txtClave.requestFocus();
                JOptionPane.showMessageDialog(null, "Ingrese la nueva contraseña", Module.titleMessage, JOptionPane.CANCEL_OPTION);
            }else if(clave2.equals("")){
                this.txtClave2.requestFocus();
                JOptionPane.showMessageDialog(null, "Repita la nueva contraseña", Module.titleMessage, JOptionPane.CANCEL_OPTION);
            }else if(!clave1.equals(clave2)){
                JOptionPane.showMessageDialog(null, "Las contraseñas no coinciden", Module.titleMessage, JOptionPane.CANCEL_OPTION);
            }else{

                String passwordEncrypt = Hash.encrypt(clave1);

                EUsuarios objUsu = new EUsuarios();
                objUsu.setId(id);
                objUsu.setClave(passwordEncrypt);
                message = clsUsuario.changePassword(objUsu);

                if(message.equals("Actualizado correctamente")){
                    this.txtToken.setText("");
                    this.txtClave.setText("");
                    this.txtClave2.setText("");
                    this.btnValidar.setEnabled(true);
                    JOptionPane.showMessageDialog(null, "La contraseña se ha actualizado correctamente", Module.titleMessage, JOptionPane.INFORMATION_MESSAGE);
                    this.dispose();
                }else{
                    JOptionPane.showMessageDialog(null, message, Module.titleMessage, JOptionPane.CANCEL_OPTION);
                }
            }

        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex.toString(), Module.titleMessage, JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnCambiarClaveActionPerformed

    private void btnValidarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnValidarActionPerformed
        // TODO add your handling code here:
        try{

            String token = this.txtToken.getText().trim();
            if (token.equals("")) {
                this.txtToken.requestFocus();
                JOptionPane.showMessageDialog(null, "Ingrese el token enviado a su correo electrónico", Module.titleMessage, JOptionPane.CANCEL_OPTION);
                return;
            }
            if(token.length() != 6){
                this.txtToken.requestFocus();
                JOptionPane.showMessageDialog(null, "Ingrese un token válido", Module.titleMessage, JOptionPane.CANCEL_OPTION);
                return;
            }

            ArrayList arrayList = clsUsuario.searchByToken(token);

            if(arrayList.isEmpty()){
                JOptionPane.showMessageDialog(null, "No existe el token en el sistema", Module.titleMessage, JOptionPane.CANCEL_OPTION);
                return;
            }

            EUsuarios usuario = (EUsuarios)arrayList.get(0);
            id = usuario.getId();

            this.txtToken.setEditable(false);
            this.txtClave.setEnabled(true);
            this.txtClave2.setEnabled(true);
            this.btnCambiarClave.setEnabled(true);
            this.btnValidar.setEnabled(false);
            this.txtClave.requestFocus();

        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex.toString(), Module.titleMessage, JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnValidarActionPerformed

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
            java.util.logging.Logger.getLogger(FrmCambiarClave.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmCambiarClave.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmCambiarClave.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmCambiarClave.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmCambiarClave().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCambiarClave;
    private javax.swing.JButton btnValidar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPasswordField txtClave;
    private javax.swing.JPasswordField txtClave2;
    private javax.swing.JTextField txtToken;
    // End of variables declaration//GEN-END:variables
}
