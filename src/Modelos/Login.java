/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;

import AccesoDatos.Conexion;
import Negocio.ELogin;
import Negocio.EUsuarios;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Login {
    
    Conexion objConex = new Conexion();
    CallableStatement stmt;
    ResultSet rs;
    Connection connect = null;
    
    public Login() throws SQLException, ClassNotFoundException{
        connect = objConex.getConexion();
    }
    
    public ArrayList iniciarSesion(ELogin objLogin)
    {
        ArrayList arrayList = new ArrayList();
            
        try{
            
            stmt = connect.prepareCall("SELECT * FROM func_login(?, ?)");
            stmt.setString(1, objLogin.getUsername());
            stmt.setString(2, objLogin.getPassword());
            rs = stmt.executeQuery();
            
            if(rs.next()){
                EUsuarios objUser = new EUsuarios();
                objUser.setId(rs.getInt("id"));
                objUser.setNombres(rs.getString("nombres"));
                objUser.setUsuario(rs.getString("usuario"));
                objUser.setEstado(rs.getString("estado"));
                objUser.setTipoRol(rs.getString("role"));
                arrayList.add(objUser);
            }
            
        }catch(SQLException ex){
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return arrayList;
    }
}
