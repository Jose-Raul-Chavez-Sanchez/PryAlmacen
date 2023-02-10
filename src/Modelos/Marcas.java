/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;

import AccesoDatos.Conexion;
import Negocio.EComboBox;
import Negocio.EMarcas;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;


public class Marcas {
    
    Conexion objConex = new Conexion();
    CallableStatement stmt;
    ResultSet rs;
    Connection connect = null;
    
    public Marcas(){
        connect = objConex.getConexion();
    }
    
    public ArrayList getAll(){
        ArrayList arrayList = new ArrayList();
            
        try{
            
            stmt = connect.prepareCall("SELECT * FROM func_listar_marcas()");
            rs = stmt.executeQuery();
            
            while(rs.next()){
                EMarcas objMarca = new EMarcas();
                objMarca.setId(rs.getInt("id"));
                objMarca.setDescripcion(rs.getString("descripcion"));
                objMarca.setEstado(rs.getString("estado"));
                arrayList.add(objMarca);
            }
            
        }catch(SQLException ex){
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return arrayList;
    }
    
    public ArrayList searchById(int id){
        ArrayList arrayList = new ArrayList();
            
        try{
            
            stmt = connect.prepareCall("SELECT * FROM func_buscar_marca(?)");
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            
            if(rs.next()){
                EMarcas objMarca = new EMarcas();
                objMarca.setId(rs.getInt("id"));
                objMarca.setDescripcion(rs.getString("descripcion"));
                objMarca.setEstado(rs.getString("estado"));
                arrayList.add(objMarca);
            }
            
        }catch(SQLException ex){
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return arrayList;
    }
    
    public ArrayList searchByName(String name){
        ArrayList arrayList = new ArrayList();
        
        try{
            
            stmt = connect.prepareCall("SELECT * FROM func_buscar_marca_nombre(?)");
            stmt.setString(1, name);
            rs = stmt.executeQuery();
            
            while(rs.next()){
                EMarcas objMarca = new EMarcas();
                objMarca.setId(rs.getInt("id"));
                objMarca.setDescripcion(rs.getString("descripcion"));
                objMarca.setEstado(rs.getString("estado"));
                arrayList.add(objMarca);
            }
            
        }catch(SQLException ex){
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return arrayList;
    }
    
    public String create(EMarcas objMarca){
        String message = "";
        
        try{
            
            stmt = connect.prepareCall("call sp_registrar_marca(?, ?, ?)");
            stmt.setString(1, objMarca.getDescripcion());
            stmt.setString(2, objMarca.getEstado());
            stmt.registerOutParameter(3, Types.VARCHAR, 100);
            stmt.executeUpdate();
            message = stmt.getString(3);
                    
        }catch(SQLException ex){
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return message;
    }
    
    public String update(EMarcas objMarca){
        String message = "";
        
        try{
            
            stmt = connect.prepareCall("call sp_actualizar_marca(?, ?, ?, ?)");
            stmt.setInt(1, objMarca.getId());
            stmt.setString(2, objMarca.getDescripcion());
            stmt.setString(3, objMarca.getEstado());
            stmt.registerOutParameter(4, Types.VARCHAR, 100);
            stmt.executeUpdate();
            message = stmt.getString(4);
                    
        }catch(SQLException ex){
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return message;
    }
    
    public ComboBoxModel fillCombobox(){
        DefaultComboBoxModel comboBox = new DefaultComboBoxModel();
        
        ArrayList arrayList = this.getAll();
        for (int i = 0; i < arrayList.size(); i++) {
            EMarcas marca = (EMarcas)arrayList.get(i);
            comboBox.addElement(new EComboBox(marca.getId(), marca.getDescripcion()));
        }
        
        return comboBox;
    }
}
