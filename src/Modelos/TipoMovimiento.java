/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;

import AccesoDatos.Conexion;
import Negocio.EComboBox;
import Negocio.ETipoMovimiento;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;


public class TipoMovimiento {
    
    Conexion objConex = new Conexion();
    CallableStatement stmt;
    ResultSet rs;
    Connection connect = null;
    
    public TipoMovimiento(){
        connect = objConex.getConexion();
    }
    
    public ArrayList getAll(){
        ArrayList arrayList = new ArrayList();
            
        try{
            
            stmt = connect.prepareCall("SELECT * FROM func_listar_tipomovimiento()");
            rs = stmt.executeQuery();
            
            while(rs.next()){
                ETipoMovimiento objTipoMov = new ETipoMovimiento();
                objTipoMov.setId(rs.getInt("id"));
                objTipoMov.setDescripcion(rs.getString("descripcion"));
                arrayList.add(objTipoMov);
            }
            
        }catch(SQLException ex){
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return arrayList;
    }
    
    public ComboBoxModel fillCombobox(){
        DefaultComboBoxModel comboBox = new DefaultComboBoxModel();
        
        ArrayList arrayList = this.getAll();
        for (int i = 0; i < arrayList.size(); i++) {
            ETipoMovimiento tipoMov = (ETipoMovimiento)arrayList.get(i);
            comboBox.addElement(new EComboBox(tipoMov.getId(), tipoMov.getDescripcion()));
        }
        
        return comboBox;
    }
}
