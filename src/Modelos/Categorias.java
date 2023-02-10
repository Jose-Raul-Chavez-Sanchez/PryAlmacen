/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;

import AccesoDatos.Conexion;
import Negocio.ECategorias;
import Negocio.EComboBox;
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


public class Categorias {
    
    Conexion objConex = new Conexion();
    CallableStatement stmt;
    ResultSet rs;
    Connection connect = null;
    
    public Categorias(){
        connect = objConex.getConexion();
    }
    
    public ArrayList getAll(){
        ArrayList arrayList = new ArrayList();
            
        try{
            
            stmt = connect.prepareCall("SELECT * FROM func_listar_categorias()");
            rs = stmt.executeQuery();
            
            while(rs.next()){
                ECategorias objCategoria = new ECategorias();
                objCategoria.setId(rs.getInt("id"));
                objCategoria.setDescripcion(rs.getString("descripcion"));
                objCategoria.setEstado(rs.getString("estado"));
                arrayList.add(objCategoria);
            }
            
        }catch(SQLException ex){
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return arrayList;
    }
    
    public ArrayList searchById(int id){
        ArrayList arrayList = new ArrayList();
            
        try{
            
            stmt = connect.prepareCall("SELECT * FROM func_buscar_categoria(?)");
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            
            if(rs.next()){
                ECategorias objCategoria = new ECategorias();
                objCategoria.setId(rs.getInt("id"));
                objCategoria.setDescripcion(rs.getString("descripcion"));
                objCategoria.setEstado(rs.getString("estado"));
                arrayList.add(objCategoria);
            }
            
        }catch(SQLException ex){
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return arrayList;
    }
    
    public ArrayList searchByName(String name){
        ArrayList arrayList = new ArrayList();
        
        try{
            
            stmt = connect.prepareCall("SELECT * FROM func_buscar_categoria_nombre(?)");
            stmt.setString(1, name);
            rs = stmt.executeQuery();
            
            while(rs.next()){
                ECategorias objCategoria = new ECategorias();
                objCategoria.setId(rs.getInt("id"));
                objCategoria.setDescripcion(rs.getString("descripcion"));
                objCategoria.setEstado(rs.getString("estado"));
                arrayList.add(objCategoria);
            }
            
        }catch(SQLException ex){
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return arrayList;
    }
    
    public String create(ECategorias objCategoria){
        String message = "";
        
        try{
            
            stmt = connect.prepareCall("call sp_registrar_categoria(?, ?, ?)");
            stmt.setString(1, objCategoria.getDescripcion());
            stmt.setString(2, objCategoria.getEstado());
            stmt.registerOutParameter(3, Types.VARCHAR);
            stmt.executeUpdate();
            message = stmt.getString(3);
                    
        }catch(SQLException ex){
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return message;
    }
    
    public String update(ECategorias objCategoria){
        String message = "";
        
        try{
            
            stmt = connect.prepareCall("call sp_actualizar_categoria(?, ?, ?, ?)");
            stmt.setInt(1, objCategoria.getId());
            stmt.setString(2, objCategoria.getDescripcion());
            stmt.setString(3, objCategoria.getEstado());
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
            ECategorias categoria = (ECategorias)arrayList.get(i);
            comboBox.addElement(new EComboBox(categoria.getId(), categoria.getDescripcion()));
        }
        
        return comboBox;
    }
}
