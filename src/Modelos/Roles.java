/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;

import AccesoDatos.Conexion;
import Negocio.EComboBox;
import Negocio.ERoles;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;


public class Roles {
    
    Conexion objConex = new Conexion();
    CallableStatement stmt;
    ResultSet rs;
    Connection connect = null;
    
    public Roles(){
        connect = objConex.getConexion();
    }
    
    public ArrayList getAll(){
        ArrayList arrayList = new ArrayList();
            
        try{
            
            stmt = connect.prepareCall("SELECT * FROM func_listar_roles()");
            rs = stmt.executeQuery();
            
            while(rs.next()){
                ERoles objRol = new ERoles();
                objRol.setId(rs.getInt("id"));
                objRol.setNombre(rs.getString("nombre"));
                objRol.setEstado(rs.getString("estado"));
                arrayList.add(objRol);
            }
            
        }catch(SQLException ex){
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return arrayList;
    }
    
    public ArrayList searchById(int id){
        ArrayList arrayList = new ArrayList();
            
        try{
            
            stmt = connect.prepareCall("SELECT * FROM func_buscar_role(?)");
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            
            if(rs.next()){
                ERoles objRol = new ERoles();
                objRol.setId(rs.getInt("id"));
                objRol.setNombre(rs.getString("nombre"));
                objRol.setEstado(rs.getString("estado"));
                arrayList.add(objRol);
            }
            
        }catch(SQLException ex){
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return arrayList;
    }
    
    public ArrayList searchByName(String name){
        ArrayList arrayList = new ArrayList();
        
        try{
            
            stmt = connect.prepareCall("SELECT * FROM func_buscar_role_nombre(?)");
            stmt.setString(1, name);
            rs = stmt.executeQuery();
            
            while(rs.next()){
                ERoles objRol = new ERoles();
                objRol.setId(rs.getInt("id"));
                objRol.setNombre(rs.getString("nombre"));
                objRol.setEstado(rs.getString("estado"));
                arrayList.add(objRol);
            }
            
        }catch(SQLException ex){
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return arrayList;
    }
    
    public String create(ERoles objRoles){
        String message = "";
        
        try{
            
            stmt = connect.prepareCall("call sp_registrar_role(?, ?, ?)");
            stmt.setString(1, objRoles.getNombre());
            stmt.setString(2, objRoles.getEstado());
            stmt.registerOutParameter(3, Types.VARCHAR, 100);
            stmt.executeUpdate();
            message = stmt.getString(3);
                    
        }catch(SQLException ex){
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return message;
    }
    
    public String update(ERoles objRoles){
        String message = "";
        
        try{
            
            stmt = connect.prepareCall("call sp_actualizar_role(?, ?, ?, ?)");
            stmt.setInt(1, objRoles.getId());
            stmt.setString(2, objRoles.getNombre());
            stmt.setString(3, objRoles.getEstado());
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
            ERoles role = (ERoles)arrayList.get(i);
            comboBox.addElement(new EComboBox(role.getId(), role.getNombre()));
        }
        
        return comboBox;
    }
}
