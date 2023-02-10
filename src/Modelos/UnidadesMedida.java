 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;

import AccesoDatos.Conexion;
import Negocio.EComboBox;
import Negocio.EUnidadesMedida;
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


public class UnidadesMedida {
    
    Conexion objConex = new Conexion();
    CallableStatement stmt;
    ResultSet rs;
    Connection connect = null;
    
    public UnidadesMedida(){
        connect = objConex.getConexion();
    }
    
    public ArrayList getAll(){
        ArrayList arrayList = new ArrayList();
            
        try{
            
            stmt = connect.prepareCall("SELECT * FROM func_listar_unidadesmedida()");
            rs = stmt.executeQuery();
            
            while(rs.next()){
                EUnidadesMedida objUndMedida = new EUnidadesMedida();
                objUndMedida.setId(rs.getInt("id"));
                objUndMedida.setDescripcion(rs.getString("descripcion"));
                arrayList.add(objUndMedida);
            }
            
        }catch(SQLException ex){
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return arrayList;
    }
    
    public ArrayList searchById(int id){
        ArrayList arrayList = new ArrayList();
            
        try{
            
            stmt = connect.prepareCall("SELECT * FROM func_buscar_unidadmedida(?)");
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            
            if(rs.next()){
                EUnidadesMedida objUndMedida = new EUnidadesMedida();
                objUndMedida.setId(rs.getInt("id"));
                objUndMedida.setDescripcion(rs.getString("descripcion"));
                arrayList.add(objUndMedida);
            }
            
        }catch(SQLException ex){
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return arrayList;
    }
    
    public ArrayList searchByName(String name){
        ArrayList arrayList = new ArrayList();
        
        try{
            
            stmt = connect.prepareCall("SELECT * FROM func_buscar_unidadmedida_nombre(?)");
            stmt.setString(1, name);
            rs = stmt.executeQuery();
            
            while(rs.next()){
                EUnidadesMedida objUndMedida = new EUnidadesMedida();
                objUndMedida.setId(rs.getInt("id"));
                objUndMedida.setDescripcion(rs.getString("descripcion"));
                arrayList.add(objUndMedida);
            }
            
        }catch(SQLException ex){
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return arrayList;
    }
    
    public String create(EUnidadesMedida objUndMedida){
        String message = "";
        
        try{
            
            stmt = connect.prepareCall("call sp_registrar_unidadmedida(?, ?)");
            stmt.setString(1, objUndMedida.getDescripcion());
            stmt.registerOutParameter(2, Types.VARCHAR, 100);
            stmt.executeUpdate();
            message = stmt.getString(2);
                    
        }catch(SQLException ex){
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return message;
    }
    
    public String update(EUnidadesMedida objUndMedida){
        String message = "";
        
        try{
            
            stmt = connect.prepareCall("call sp_actualizar_unidadmedida(?, ?, ?)");
            stmt.setInt(1, objUndMedida.getId());
            stmt.setString(2, objUndMedida.getDescripcion());
            stmt.registerOutParameter(3, Types.VARCHAR, 100);
            stmt.executeUpdate();
            message = stmt.getString(3);
                    
        }catch(SQLException ex){
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return message;
    }
    
    public ComboBoxModel fillCombobox(){
        DefaultComboBoxModel comboBox = new DefaultComboBoxModel();
        
        ArrayList arrayList = this.getAll();
        for (int i = 0; i < arrayList.size(); i++) {
            EUnidadesMedida unidadMedida = (EUnidadesMedida)arrayList.get(i);
            comboBox.addElement(new EComboBox(unidadMedida.getId(), unidadMedida.getDescripcion()));
        }
        
        return comboBox;
    }
}
