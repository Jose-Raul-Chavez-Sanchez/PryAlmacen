/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Presentacion;

import com.mxrck.autocompleter.AutoCompleterCallback;
import com.mxrck.autocompleter.TextAutoCompleter;
import Negocio.EDetalleMovimiento;
import Negocio.EMovimientos;
import Negocio.EProductos;
import Negocio.ETipoMovimiento;
import Negocio.EUsuarios;
import Negocio.ItemAutocomplete;
import Negocio.ItemsCarrito;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import Modelos.Movimientos;
import Modelos.Productos;


public class FrmNotaSalida extends javax.swing.JFrame implements IFormCart{
    
    EForm formActive;
    Movimientos clsMovimiento = new Movimientos();   
    TextAutoCompleter autocomplete;
    Productos clsProducto = new Productos();
    ArrayList<ItemsCarrito> listCart = new ArrayList<>();
    
    DefaultTableModel defaultTable =new DefaultTableModel(){
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    
    /**
     * Creates new form FrmNotaSalida
     */
    public FrmNotaSalida() {
        initComponents();
        
        this.setLocationRelativeTo(this);
        this.setResizable(false);
        this.txtSerie.setEditable(false);
        this.txtNumero.setEditable(false);
        this.txtSerie.setText(Module.serieNS);
        
        formActive = Module.formActive;
        
        lblUsuario.setText(Module.userName);
        EFormCart itemCart = Module.itemCart;
        itemCart.setCaller(this);
                
        autocomplete = new TextAutoCompleter(txtSearchProducts, new AutoCompleterCallback() {
            @Override
            public void callback(Object selectedItem) {
                Object obj = autocomplete.findItem(selectedItem);
                ItemAutocomplete item = (ItemAutocomplete) obj;
                
                Module.id = item.getId();
                Module.editCart = false;
                FrmAsignarCantidad formAsignar = new FrmAsignarCantidad();
                formAsignar.setVisible(true);
                //addItemCart(item.getId());
            }
        });
        //autocomplete = new TextAutoCompleter(txtSearchProducts);
        clsProducto.loadAutocomplete(autocomplete);
        
        this.defaultTable.addColumn("Id");
        this.defaultTable.addColumn("Producto");
        this.defaultTable.addColumn("Und. Medida");
        this.defaultTable.addColumn("Cantidad");
        this.defaultTable.addColumn("Precio");
        
        this.tblItemCarrito.setModel(defaultTable);
        
        TableColumnModel columnModel = tblItemCarrito.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(15);
        columnModel.getColumn(1).setPreferredWidth(180);
        columnModel.getColumn(2).setPreferredWidth(50);
        columnModel.getColumn(3).setPreferredWidth(30);
        columnModel.getColumn(4).setPreferredWidth(50);
        tblItemCarrito.setRowHeight(25);
        
        loadTableCart();
        showNumberNote();
    }
    
    private void showNumberNote(){
        try{
            
            int tipoMovId = 2; //2=NOTA DE SALIDA
            
            String number = clsMovimiento.generateNumber(tipoMovId);
            this.txtNumero.setText(number);
            
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex.toString(), Module.titleMessage, JOptionPane.ERROR_MESSAGE);
        }
    }

    public void addItemCart(int productId, int quantity){
        try{
            
            ArrayList arrayList = clsProducto.searchByIdToCart(productId);
            EProductos producto = (EProductos)arrayList.get(0);
            int stock = producto.getStock();
            if(stock < quantity){
                JOptionPane.showMessageDialog(null, "El producto: "+producto.getDescripcion()+" no tiene stock", Module.titleMessage, JOptionPane.CANCEL_OPTION);
                return;
            }
            
            if(!Module.editCart){
                if(existItem(productId) >= 0){
                    this.txtSearchProducts.setText("");
                    this.txtSearchProducts.requestFocus();
                    JOptionPane.showMessageDialog(null, "El producto: "+producto.getDescripcion()+" ya existe en la lista", Module.titleMessage, JOptionPane.CANCEL_OPTION);
                    return; 
                }
                
                ItemsCarrito item = new ItemsCarrito();
                item.setProductoId(producto.getId());
                item.setProducto(producto.getDescripcion());
                item.setUndMedida(producto.getUndMedida().getDescripcion());
                item.setCantidad(quantity);
                item.setPrecio(producto.getPrecioVenta());
                listCart.add(item);
            }else{
                int pos = existItem(productId);
                listCart.get(pos).setCantidad(quantity);
            }
            
            Module.id = 0;
            Module.editCart = false;
            loadTableCart();
            this.txtSearchProducts.setText("");
            this.txtSearchProducts.requestFocus();
            
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex.toString(), Module.titleMessage, JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private int existItem(int productId){
        int i = 0;
        for (ItemsCarrito e : listCart) {
            if(e.getProductoId() == productId){
                return i;
            }
            i++;
        }
        
        return -1;
    }
    
    private void loadTableCart(){
        try{
            
            for (int i=this.defaultTable.getRowCount()-1;i>=0;i--){
                this.defaultTable.removeRow(i);
            }
            
            DecimalFormat formatter = new DecimalFormat("0.00");
            
            for (int i = 0; i < listCart.size(); i++) {
                ItemsCarrito itemCart = (ItemsCarrito)listCart.get(i);
                this.defaultTable.addRow(new Object[]{});
                this.defaultTable.setValueAt(itemCart.getProductoId(), i, 0);
                this.defaultTable.setValueAt(itemCart.getProducto(), i, 1);
                this.defaultTable.setValueAt(itemCart.getUndMedida(), i, 2);
                this.defaultTable.setValueAt(itemCart.getCantidad(), i, 3);
                this.defaultTable.setValueAt(formatter.format(itemCart.getPrecio()), i, 4);
            }
            
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex.toString(), Module.titleMessage, JOptionPane.ERROR_MESSAGE);
        }
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
        jLabel9 = new javax.swing.JLabel();
        txtSerie = new javax.swing.JTextField();
        txtSearchProducts = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        btnCancelar = new javax.swing.JButton();
        txtNumero = new javax.swing.JTextField();
        btnGuardar = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblItemCarrito = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        dtpFechaSalida = new com.toedter.calendar.JDateChooser();
        lblUsuario = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtObservaciones = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        btnEliminar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Nota de Salida");
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel9.setText("PRODUCTO");

        txtSerie.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        txtSearchProducts.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setText("NÚMERO");

        btnCancelar.setBackground(new java.awt.Color(70, 70, 255));
        btnCancelar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnCancelar.setForeground(new java.awt.Color(255, 255, 255));
        btnCancelar.setText("CANCELAR");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        txtNumero.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        btnGuardar.setBackground(new java.awt.Color(70, 70, 255));
        btnGuardar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnGuardar.setForeground(new java.awt.Color(255, 255, 255));
        btnGuardar.setText("GUARDAR");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText("FECHA SALIDA");

        tblItemCarrito.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "PRODUCTO", "UND. MEDIDA", "CANTIDAD", "PRECIO"
            }
        ));
        tblItemCarrito.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblItemCarritoMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblItemCarrito);

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setText("USUARIO");

        dtpFechaSalida.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        lblUsuario.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblUsuario.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblUsuario.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel8.setText("OBSERVACIONES");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel1.setText("TIPO DE NOTA");

        txtObservaciones.setColumns(20);
        txtObservaciones.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtObservaciones.setRows(5);
        jScrollPane1.setViewportView(txtObservaciones);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("NOTA DE SALIDA");
        jLabel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("SERIE");

        btnEliminar.setBackground(new java.awt.Color(70, 70, 255));
        btnEliminar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnEliminar.setForeground(new java.awt.Color(255, 255, 255));
        btnEliminar.setText("ELIMINAR");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jScrollPane2)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel9)
                                .addComponent(jLabel8)
                                .addComponent(jLabel3)
                                .addComponent(jLabel1))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addComponent(txtSerie, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(26, 26, 26)
                                            .addComponent(jLabel4)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(txtNumero, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(43, 43, 43)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel5)
                                        .addComponent(jLabel6))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(lblUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(dtpFechaSalida, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addComponent(jScrollPane1)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(txtSearchProducts, javax.swing.GroupLayout.PREFERRED_SIZE, 385, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(btnCancelar)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(42, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtSerie, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)
                            .addComponent(txtNumero, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(jLabel5)
                        .addGap(28, 28, 28)
                        .addComponent(jLabel6))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(dtpFechaSalida, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel9)
                        .addComponent(txtSearchProducts, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(38, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
        listCart.clear();
        loadTableCart();
        Module.id = 0;
        Module.editCart = false;
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        // TODO add your handling code here:
        try{
            
            int lastInsertId;
            
            int tipoMovId = 2; //1=ENTRADA | 2=SALIDA
            String serie = this.txtSerie.getText().trim();
            String numero = clsMovimiento.generateNumber(tipoMovId); //this.txtNumero.getText().trim();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String fechaIngresoStr = formatter.format(dtpFechaSalida.getDate());
            
            String observaciones = this.txtObservaciones.getText().trim();
            
            if(serie.equals("")){
                JOptionPane.showMessageDialog(null, "No existe la serie de la nota de ingreso", Module.titleMessage, JOptionPane.CANCEL_OPTION);
                return;
            }
            if(numero.equals("")){
                JOptionPane.showMessageDialog(null, "No existe el número de la nota de ingreso", Module.titleMessage, JOptionPane.CANCEL_OPTION);
                return;
            }
            if(listCart.isEmpty()){
                JOptionPane.showMessageDialog(null, "No existe items para guardar la nota de ingreso", Module.titleMessage, JOptionPane.CANCEL_OPTION);
                return;
            }
            
            ETipoMovimiento objTipoMov = new ETipoMovimiento();
            EUsuarios objUsu = new EUsuarios();
            
            objTipoMov.setId(tipoMovId);
            objUsu.setId(Module.userId); //Id del usuario logueado
            
            EMovimientos objMovimiento = new EMovimientos();
            objMovimiento.setTipoMovimiento(objTipoMov);
            objMovimiento.setUsuario(objUsu);
            objMovimiento.setSerie(serie);
            objMovimiento.setNumero(numero);
            objMovimiento.setFechaRegistro(fechaIngresoStr);
            objMovimiento.setObservaciones(observaciones);
            objMovimiento.setEstado("ACTIVO");
            
            ArrayList<EDetalleMovimiento> listDetails = new ArrayList<>();
            
            for (ItemsCarrito item : listCart) {
                EDetalleMovimiento objDetail = new EDetalleMovimiento();
                objDetail.setProductoId(item.getProductoId());
                objDetail.setCantidad(item.getCantidad());
                listDetails.add(objDetail);
            }
            
            lastInsertId = clsMovimiento.createTransact(objMovimiento, listDetails);

            if(lastInsertId != 0){
                showNumberNote();
                this.txtObservaciones.setText("");
                this.txtSearchProducts.setText("");
                listCart.clear();
                Module.id = 0;
                Module.editCart = false;
                loadTableCart();
                formActive.getCaller().loadTable();
                JOptionPane.showMessageDialog(null, "El registro de la nota de salida fue exitoso.", Module.titleMessage, JOptionPane.INFORMATION_MESSAGE);
            }else{
                JOptionPane.showMessageDialog(null, "No pudimos registrar la nota de salida.", Module.titleMessage, JOptionPane.CANCEL_OPTION);
            }
            
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex.toString(), Module.titleMessage, JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void tblItemCarritoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblItemCarritoMouseClicked
        // TODO add your handling code here:
        if(evt.getClickCount()==2){
            int id = (int) tblItemCarrito.getValueAt(tblItemCarrito.getSelectedRow(), 0);
            Module.id = id;
            Module.editCart = true;
            EFormCart itemCart = Module.itemCart;
            itemCart.setCaller(this);
            
            FrmAsignarCantidad formAsignar = new FrmAsignarCantidad();
            formAsignar.setVisible(true);
        }
    }//GEN-LAST:event_tblItemCarritoMouseClicked

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        // TODO add your handling code here:
        if(listCart.isEmpty()){
            JOptionPane.showMessageDialog(null, "No existe items para eliminar", Module.titleMessage, JOptionPane.CANCEL_OPTION);
            return;
        }
        if(tblItemCarrito.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(null, "Seleccione un item para eliminar", Module.titleMessage, JOptionPane.CANCEL_OPTION);
            return;
        }
        
        int pos = tblItemCarrito.getSelectedRow();
        defaultTable.removeRow(pos);
        listCart.remove(pos);
    }//GEN-LAST:event_btnEliminarActionPerformed

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
            java.util.logging.Logger.getLogger(FrmNotaSalida.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmNotaSalida.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmNotaSalida.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmNotaSalida.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmNotaSalida().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private com.toedter.calendar.JDateChooser dtpFechaSalida;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JTable tblItemCarrito;
    private javax.swing.JTextField txtNumero;
    private javax.swing.JTextArea txtObservaciones;
    private javax.swing.JTextField txtSearchProducts;
    private javax.swing.JTextField txtSerie;
    // End of variables declaration//GEN-END:variables
}
