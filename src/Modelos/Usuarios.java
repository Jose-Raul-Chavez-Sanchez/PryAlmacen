/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;

import AccesoDatos.Conexion;
import Negocio.EUsuarios;
/*import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;*/
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Usuarios {
    
    Conexion objConex = new Conexion();
    
    CallableStatement stmt;
    ResultSet rs;
    Connection connect = null;
    
    public Usuarios(){
        connect = objConex.getConexion();
    }
    
    public ArrayList getAll(){
        ArrayList arrayList = new ArrayList();
           try{
            stmt = connect.prepareCall("SELECT * FROM func_listar_usuarios()");
            rs = stmt.executeQuery();
            
            while(rs.next()){
                EUsuarios objUsu = new EUsuarios();
                objUsu.setId(rs.getInt("id"));
                objUsu.setRolId(rs.getInt("idrole"));
                objUsu.setTipoRol(rs.getString("role"));
                objUsu.setNombres(rs.getString("nombres"));
                objUsu.setUsuario(rs.getString("usuario"));
                objUsu.setCorreo(rs.getString("correo"));
                objUsu.setEstado(rs.getString("estado"));
                arrayList.add(objUsu);
            }
            
        }catch(SQLException ex){
            Logger.getLogger(Login.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
        
        return arrayList;
    }
    
    public ArrayList searchById(int id){
        ArrayList arrayList = new ArrayList();
            
        try{
      //INDICAMOS FUNCION A UTILIZAR      
     stmt = connect.prepareCall("SELECT * FROM func_buscar_usuario_id(?)");
     //ENVIAMOS PARAMETRO QUE REQUIERE LA FUNCION       
     stmt.setInt(1, id);
     //EJECUTAMOS FUNCION Y RECIBIMOS LAS FILAS EN UN RESULTSET
            rs = stmt.executeQuery();
            
            if(rs.next()){//SI EXISTE UN REGISTRO
                EUsuarios objUsu = new EUsuarios();
                objUsu.setId(rs.getInt("id"));
                objUsu.setRolId(rs.getInt("idrole"));
                objUsu.setTipoRol(rs.getString("role"));
                objUsu.setNombres(rs.getString("nombres"));
                objUsu.setUsuario(rs.getString("usuario"));
                objUsu.setClave(rs.getString("clave"));
                objUsu.setToken(rs.getString("token"));
                objUsu.setCorreo(rs.getString("correo"));
                objUsu.setEstado(rs.getString("estado"));
                arrayList.add(objUsu);
            }
            
        }catch(SQLException ex){
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return arrayList;
    }
    
    public ArrayList searchByName(String name){
        ArrayList arrayList = new ArrayList();
            
        try{
            
     stmt = connect.prepareCall("SELECT * FROM func_buscar_usuario_nombre(?)");
            stmt.setString(1, name);
            rs = stmt.executeQuery();
            
            while(rs.next()){
                EUsuarios objUsu = new EUsuarios();
                objUsu.setId(rs.getInt("id"));
                objUsu.setRolId(rs.getInt("idrole"));
                objUsu.setNombres(rs.getString("nombres"));
                objUsu.setUsuario(rs.getString("usuario"));
                objUsu.setClave(rs.getString("clave"));
                objUsu.setToken(rs.getString("token"));
                objUsu.setCorreo(rs.getString("correo"));
                objUsu.setEstado(rs.getString("estado"));
                arrayList.add(objUsu);
            }
            
        }catch(SQLException ex){
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }        
        return arrayList;
    }
    
    public String create(EUsuarios objUsuario){
        String message = "";
          try{
stmt = connect.prepareCall("call sp_registrar_usuario(?, ?, ?, ?, ?, ?, ?)");
            stmt.setInt(1, objUsuario.getRolId());
            stmt.setString(2, objUsuario.getNombres());
            stmt.setString(3, objUsuario.getUsuario());
            stmt.setString(4, objUsuario.getClave());
            stmt.setString(5, objUsuario.getCorreo());
            stmt.setString(6, objUsuario.getEstado());
   stmt.registerOutParameter(7, Types.VARCHAR, 100);
            stmt.executeUpdate();
            message = stmt.getString(7);
                    
        }catch(SQLException ex){
Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return message;
    }
    
    public String update(EUsuarios objUsuario){
        String message = "";
        
        try{
            
stmt = connect.prepareCall("call sp_actualizar_usuario(?, ?, ?, ?, ?, ?, ?, ?)");
            stmt.setInt(1, objUsuario.getId());
            stmt.setInt(2, objUsuario.getRolId());
            stmt.setString(3, objUsuario.getNombres());
            stmt.setString(4, objUsuario.getUsuario());
            stmt.setString(5, objUsuario.getClave());
            stmt.setString(6, objUsuario.getCorreo());
            stmt.setString(7, objUsuario.getEstado());
            stmt.registerOutParameter(8, Types.VARCHAR, 100);
            stmt.executeUpdate();
            message = stmt.getString(8);
                    
        }catch(SQLException ex){
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return message;
    }
    
    public ArrayList searchByEmail(String email){
        ArrayList arrayList = new ArrayList();
           
        try{
            
stmt = connect.prepareCall("SELECT * FROM func_buscar_usuario_email(?)");
            stmt.setString(1, email);
            rs = stmt.executeQuery();
            
            if(rs.next()){
                EUsuarios objUsu = new EUsuarios();
                objUsu.setId(rs.getInt("id"));
                objUsu.setRolId(rs.getInt("idrole"));
                objUsu.setNombres(rs.getString("nombres"));
                objUsu.setUsuario(rs.getString("usuario"));
                objUsu.setClave(rs.getString("clave"));
                objUsu.setToken(rs.getString("token"));
                objUsu.setCorreo(rs.getString("correo"));
                objUsu.setEstado(rs.getString("estado"));
                arrayList.add(objUsu);
            }
            
        }catch(SQLException ex){
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return arrayList;
    }
    
    public String updateToken(EUsuarios objUsuario){
        String message = "";
        
        try{
            
            stmt = connect.prepareCall("call sp_actualizar_usuario_token(?, ?, ?)");
            stmt.setInt(1, objUsuario.getId());
            stmt.setString(2, objUsuario.getToken());
            stmt.registerOutParameter(3, Types.VARCHAR, 100);
            stmt.executeUpdate();
            message = stmt.getString(3);
                    
        }catch(SQLException ex){
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return message;
    }
    
    public ArrayList searchByToken(String token){
        ArrayList arrayList = new ArrayList();
            
        try{
            
            stmt = connect.prepareCall("SELECT * FROM func_buscar_usuario_token(?)");
            stmt.setString(1, token);
            rs = stmt.executeQuery();
            
            if(rs.next()){
                EUsuarios objUsu = new EUsuarios();
                objUsu.setId(rs.getInt("id"));
                objUsu.setRolId(rs.getInt("idrole"));
                objUsu.setNombres(rs.getString("nombres"));
                objUsu.setUsuario(rs.getString("usuario"));
                objUsu.setClave(rs.getString("clave"));
                objUsu.setToken(rs.getString("token"));
                objUsu.setCorreo(rs.getString("correo"));
                objUsu.setEstado(rs.getString("estado"));
                arrayList.add(objUsu);
            }
            
        }catch(SQLException ex){
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return arrayList;
    }
    
    public String changePassword(EUsuarios objUsuario){
        String message = "";
        
        try{
            
   stmt = connect.prepareCall("call sp_actualizar_usuario_clave(?, ?, ?)");
            stmt.setInt(1, objUsuario.getId());
            stmt.setString(2, objUsuario.getClave());
   stmt.registerOutParameter(3, Types.VARCHAR, 100);
            stmt.executeUpdate();
            message = stmt.getString(3);
                    
        }catch(SQLException ex){
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }        
        return message;
    }
}
