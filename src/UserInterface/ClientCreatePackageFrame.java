/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserInterface;

import client.Client;
import static deliverycompany.Data.listOfPackages;
import javax.swing.JOptionPane;

/**
 *
 * @author tom
 */
public class ClientCreatePackageFrame extends javax.swing.JFrame {

    /**
     * Creates new form ClientCreatePackageFrame
     */
    public ClientCreatePackageFrame() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        packageWeightField = new javax.swing.JTextField();
        finalStationField = new javax.swing.JTextField();
        recipientNameField = new javax.swing.JTextField();
        recipientSurnameField = new javax.swing.JTextField();
        sendPackageButton = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        initialStationField = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Firma kurierska");
        setName("Firma kurierska"); // NOI18N
        setPreferredSize(new java.awt.Dimension(450, 350));

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel1.setText("Wysyłanie paczki");

        jLabel2.setText("Waga paczki:");

        jLabel3.setText("Stacja docelowa:");

        jLabel4.setText("Imię adresata:");

        jLabel5.setText("Nazwisko adresata:");

        packageWeightField.setText("0.0");

        sendPackageButton.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        sendPackageButton.setText("Potwierdź");
        sendPackageButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendPackageButtonActionPerformed(evt);
            }
        });

        jLabel6.setText("Stacja początkowa:");

        initialStationField.setToolTipText("");
        initialStationField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                initialStationFieldActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 135, Short.MAX_VALUE)
                .addComponent(sendPackageButton, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(125, 125, 125))
            .addGroup(layout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel4)
                            .addGap(29, 29, 29)))
                    .addComponent(jLabel5))
                .addGap(33, 33, 33)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(recipientSurnameField, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(finalStationField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(initialStationField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(packageWeightField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(recipientNameField, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE))))
            .addGroup(layout.createSequentialGroup()
                .addGap(151, 151, 151)
                .addComponent(jLabel1))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(packageWeightField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(initialStationField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(finalStationField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(recipientNameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(recipientSurnameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36)
                .addComponent(sendPackageButton, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(37, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void sendPackageButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendPackageButtonActionPerformed
       String weight = this.packageWeightField.getText();
       String initStation = this.initialStationField.getText();
       String finalStation = this.finalStationField.getText();
       String name = this.recipientNameField.getText();
       String surname = this.recipientSurnameField.getText();
        
       Boolean ifMistake = false;
       
        if(weight.equals("") || initStation.equals("") || finalStation.equals("") || name.equals("") || surname.equals("")) {
            ifMistake = true;
            JOptionPane.showMessageDialog(this, "Uzupełnij puste pola", "Błąd", JOptionPane.ERROR_MESSAGE);
        }
        else {
            try {
                Double.parseDouble(weight);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Niepoprawna waga", "Błąd", JOptionPane.ERROR_MESSAGE);
            }
            if(Double.parseDouble(weight) > 200.0) {
                ifMistake = true;
                JOptionPane.showMessageDialog(this, "Paczka zbyt ciężka. Wpisz wartość poniżej 200kg", "Błąd", JOptionPane.ERROR_MESSAGE);
            }
            else if(Double.parseDouble(weight) <= 0) {
                ifMistake = true;
                JOptionPane.showMessageDialog(this, "Niepoprawna waga.", "Błąd", JOptionPane.ERROR_MESSAGE);
            }
            if(!Client.checkIfCorrect(initStation)) {
                ifMistake = true;
                JOptionPane.showMessageDialog(this, "Nie ma takiej stacji początkowej", "Błąd", JOptionPane.ERROR_MESSAGE);
            }
            if(!Client.checkIfCorrect(finalStation)) {
                ifMistake = true;
                JOptionPane.showMessageDialog(this, "Nie ma takiej stacji końcowej", "Błąd", JOptionPane.ERROR_MESSAGE);
            }
            
            if(!ifMistake) {
                listOfPackages.add(new deliverycompany.Package(Double.parseDouble(weight), initStation, finalStation, name, surname));

                JOptionPane.showMessageDialog(this, "Paczka została pomyślnie wysłana.\nNumer ID twojej paczki to: " + listOfPackages.get(listOfPackages.size() - 1).IdNumber);
                ClientFrame clientFrame = new ClientFrame();
                clientFrame.setVisible(true);
                this.setVisible(false);
            }
        }
        
        
    }//GEN-LAST:event_sendPackageButtonActionPerformed

    private void initialStationFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_initialStationFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_initialStationFieldActionPerformed

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
            java.util.logging.Logger.getLogger(ClientCreatePackageFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ClientCreatePackageFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ClientCreatePackageFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ClientCreatePackageFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ClientCreatePackageFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField finalStationField;
    private javax.swing.JTextField initialStationField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JTextField packageWeightField;
    private javax.swing.JTextField recipientNameField;
    private javax.swing.JTextField recipientSurnameField;
    private javax.swing.JButton sendPackageButton;
    // End of variables declaration//GEN-END:variables
}
