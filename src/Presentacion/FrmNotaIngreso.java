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


public class FrmNotaIngreso extends javax.swing.JFrame implements IFormCart{
    
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
     * Creates new form FrmNotaIngreso
     */
    public FrmNotaIngreso() {
        initComponents();

        this.setLocationRelativeTo(this);
        this.setResizable(false);
        this.txtSerie.setEditable(false);
        this.txtNumero.setEditable(false);
        this.txtSerie.setText(Module.serieNI);
        
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
            
            int tipoMovId = 1; //1=NOTA DE INGRESO
            
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

        jTextField2 = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        btnAgregar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtSerie = new javax.swing.JTextField();
        txtNumero = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        dtpFechaIngreso = new com.toedter.calendar.JDateChooser();
        jLabel6 = new javax.swing.JLabel();
        lblUsuario = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtSearchProducts = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblItemCarrito = new javax.swing.JTable();
        btnCancelar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtObservaciones = new javax.swing.JTextArea();
        btnEliminar = new javax.swing.JButton();

        jTextField2.setText("jTextField2");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Nota de Ingreso");
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        btnAgregar.setBackground(new java.awt.Color(70, 70, 255));
        btnAgregar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnAgregar.setForeground(new java.awt.Color(255, 255, 255));
        btnAgregar.setText("GUARDAR");
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("NOTA DE INGRESO");
        jLabel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("TIPO DE NOTA");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("SERIE");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setText("NÚMERO");

        txtSerie.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtSerie.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        txtNumero.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtNumero.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText("FECHA INGRESO");

        dtpFechaIngreso.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setText("USUARIO");

        lblUsuario.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblUsuario.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblUsuario.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel8.setText("OBSERVACION");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel9.setText("PRODUCTO");

        txtSearchProducts.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtSearchProducts.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchProductsKeyReleased(evt);
            }
        });

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
        jScrollPane1.setViewportView(tblItemCarrito);

        btnCancelar.setBackground(new java.awt.Color(70, 70, 255));
        btnCancelar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnCancelar.setForeground(new java.awt.Color(255, 255, 255));
        btnCancelar.setText("CANCELAR");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        txtObservaciones.setColumns(20);
        txtObservaciones.setRows(5);
        jScrollPane2.setViewportView(txtObservaciones);

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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(33, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 703, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel3)
                                .addComponent(jLabel2)
                                .addComponent(jLabel8)
                                .addComponent(jLabel9))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(txtSearchProducts, javax.swing.GroupLayout.PREFERRED_SIZE, 363, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btnAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(txtSerie, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(26, 26, 26)
                                                .addComponent(jLabel4)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(txtNumero, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel5)
                                            .addComponent(jLabel6))
                                        .addGap(39, 39, 39)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(dtpFechaIngreso, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lblUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE))))))))
                .addGap(41, 41, 41))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dtpFechaIngreso, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2)
                        .addComponent(jLabel5)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(txtSerie, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4)
                        .addComponent(txtNumero, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel6))
                    .addComponent(lblUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(jLabel8)))
                .addGap(13, 13, 13)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAgregar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel9)
                        .addComponent(txtSearchProducts, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        // TODO add your handling code here:
        try{
            
            int lastInsertId;
            
            int tipoMovId = 1; //1=ENTRADA | 2=SALIDA
            String serie = this.txtSerie.getText().trim();
            String numero = clsMovimiento.generateNumber(tipoMovId); //this.txtNumero.getText().trim();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String fechaIngresoStr = formatter.format(dtpFechaIngreso.getDate());
            
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
                JOptionPane.showMessageDialog(null, "El registro de la nota de ingreso fue exitoso.", Module.titleMessage, JOptionPane.INFORMATION_MESSAGE);
            }else{
                JOptionPane.showMessageDialog(null, "No pudimos registrar la nota de ingreso.", Module.titleMessage, JOptionPane.CANCEL_OPTION);
            }
            
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex.toString(), Module.titleMessage, JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void txtSearchProductsKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchProductsKeyReleased
        // TODO add your handling code here:
        try{
            
            String txtNombre = this.txtSearchProducts.getText().trim();
            if(txtNombre.equals("")){
               // autocomplete.removeAllItems();
                return;
            }
            
            Productos clsProductos = new Productos();
            ArrayList arrayList = clsProductos.getAll();
                    
            //setAutocomplete(arrayList, autocomplete);
            
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex.toString(), Module.titleMessage, JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_txtSearchProductsKeyReleased

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
        listCart.clear();
        loadTableCart();
        Module.id = 0;
        Module.editCart = false;
    }//GEN-LAST:event_btnCancelarActionPerformed

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
    
    private void setAutocomplete(ArrayList arrayList, TextAutoCompleter autocomplete){
        autocomplete.removeAllItems();
        for (int i = 0; i < arrayList.size(); i++) {
            EProductos prod = (EProductos)arrayList.get(i);
            autocomplete.addItem(new ItemAutocomplete(prod.getId(), prod.getDescripcion()));
        }
    }
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
            java.util.logging.Logger.getLogger(FrmNotaIngreso.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmNotaIngreso.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmNotaIngreso.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmNotaIngreso.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmNotaIngreso().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEliminar;
    private com.toedter.calendar.JDateChooser dtpFechaIngreso;
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
    private javax.swing.JTextField jTextField2;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JTable tblItemCarrito;
    private javax.swing.JTextField txtNumero;
    private javax.swing.JTextArea txtObservaciones;
    private javax.swing.JTextField txtSearchProducts;
    private javax.swing.JTextField txtSerie;
    // End of variables declaration//GEN-END:variables
}
