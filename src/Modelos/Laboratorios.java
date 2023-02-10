/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;

import AccesoDatos.Conexion;
import Negocio.EComboBox;
import Negocio.ELaboratorios;
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


public class Laboratorios {
    
    Conexion objConex = new Conexion();
    CallableStatement stmt;
    ResultSet rs;
    Connection connect = null;
    
    public Laboratorios(){
        connect = objConex.getConexion();
    }
    
    public ArrayList getAll(){
        ArrayList arrayList = new ArrayList();
            
        try{
            
            stmt = connect.prepareCall("SELECT * FROM func_listar_laboratorios()");
            rs = stmt.executeQuery();
            
            while(rs.next()){
                ELaboratorios objLaboratorio = new ELaboratorios();
                objLaboratorio.setId(rs.getInt("id"));
                objLaboratorio.setDescripcion(rs.getString("descripcion"));
                objLaboratorio.setEstado(rs.getString("estado"));
                arrayList.add(objLaboratorio);
            }
            
        }catch(SQLException ex){
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return arrayList;
    }
    
    public ArrayList searchById(int id){
        ArrayList arrayList = new ArrayList();
            
        try{
            
            stmt = connect.prepareCall("SELECT * FROM func_buscar_laboratorio(?)");
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            
            if(rs.next()){
                ELaboratorios objLaboratorio = new ELaboratorios();
                objLaboratorio.setId(rs.getInt("id"));
                objLaboratorio.setDescripcion(rs.getString("descripcion"));
                objLaboratorio.setEstado(rs.getString("estado"));
                arrayList.add(objLaboratorio);
            }
            
        }catch(SQLException ex){
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return arrayList;
    }
    
    public ArrayList searchByName(String name){
        ArrayList arrayList = new ArrayList();
        
        try{
            
            stmt = connect.prepareCall("SELECT * FROM sp_buscar_laboratorio_nombre(?)");
            stmt.setString(1, name);
            rs = stmt.executeQuery();
            
            while(rs.next()){
                ELaboratorios objLaboratorio = new ELaboratorios();
                objLaboratorio.setId(rs.getInt("id"));
                objLaboratorio.setDescripcion(rs.getString("descripcion"));
                objLaboratorio.setEstado(rs.getString("estado"));
                arrayList.add(objLaboratorio);
            }
            
        }catch(SQLException ex){
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return arrayList;
    }
    
    public String create(ELaboratorios objLaboratorio){
        String message = "";
        
        try{
            
            stmt = connect.prepareCall("call sp_registrar_laboratorio(?, ?, ?)");
            stmt.setString(1, objLaboratorio.getDescripcion());
            stmt.setString(2, objLaboratorio.getEstado());
            stmt.registerOutParameter(3, Types.VARCHAR, 100);
            stmt.executeUpdate();
            message = stmt.getString(3);
                    
        }catch(SQLException ex){
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return message;
    }
    
    public String update(ELaboratorios objLaboratorio){
        String message = "";
        
        try{
            
            stmt = connect.prepareCall("call sp_actualizar_laboratorio(?, ?, ?, ?)");
            stmt.setInt(1, objLaboratorio.getId());
            stmt.setString(2, objLaboratorio.getDescripcion());
            stmt.setString(3, objLaboratorio.getEstado());
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
            ELaboratorios laboratorio = (ELaboratorios)arrayList.get(i);
            comboBox.addElement(new EComboBox(laboratorio.getId(), laboratorio.getDescripcion()));
        }
        
        return comboBox;
    }
}
