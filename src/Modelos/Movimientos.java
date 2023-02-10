/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;

import AccesoDatos.Conexion;
import Negocio.EDetalleMovimiento;
import Negocio.EMovimientos;
import Negocio.EUsuarios;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Movimientos {
    
    Conexion objConex = new Conexion();
    CallableStatement stmt;
    ResultSet rs;
    Connection connect = null;
    
    public Movimientos(){
        connect = objConex.getConexion();
    }
    
    public String generateNumber(int tipoMovId){
        String number = "";
        
        try{
            
            stmt = connect.prepareCall("call sp_generar_numero_notas(?, ?)");
            stmt.setInt(1, tipoMovId);
            stmt.registerOutParameter(2, Types.VARCHAR, 8);
            stmt.executeUpdate();
            number = stmt.getString(2);
            
        }catch(SQLException ex){
            Logger.getLogger(Movimientos.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return number;
    }
    
    public ArrayList getById(int id){
        ArrayList arrayList = new ArrayList();
            
        try{
            
            stmt = connect.prepareCall("SELECT * FROM func_buscar_nota_id(?)");
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            
            if(rs.next()){
                EUsuarios objUsu = new EUsuarios();
                objUsu.setNombres(rs.getString("usuario"));
                
                EMovimientos objMov = new EMovimientos();
                objMov.setId(rs.getInt("id"));
                objMov.setFechaRegistro(rs.getString("fecha_ingreso"));
                objMov.setSerie(rs.getString("serie"));
                objMov.setNumero(rs.getString("numero"));
                objMov.setObservaciones(rs.getString("observaciones"));
                objMov.setEstado(rs.getString("estado"));
                objMov.setUsuario(objUsu);
                arrayList.add(objMov);
            }
            
        }catch(SQLException ex){
            Logger.getLogger(Movimientos.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return arrayList;
    }
    
    public ArrayList getAllByIdMov(int tipoMovId){
        ArrayList arrayList = new ArrayList();
            
        try{
            
            stmt = connect.prepareCall("SELECT * FROM func_listar_notas(?)");
            stmt.setInt(1, tipoMovId);
            rs = stmt.executeQuery();
            
            while(rs.next()){
                EUsuarios objUsu = new EUsuarios();
                objUsu.setNombres(rs.getString("usuario"));
                
                EMovimientos objMov = new EMovimientos();
                objMov.setId(rs.getInt("id"));
                objMov.setFechaRegistro(rs.getString("fecha_ingreso"));
                objMov.setSerie(rs.getString("serie"));
                objMov.setNumero(rs.getString("numero"));
                objMov.setObservaciones(rs.getString("observaciones"));
                objMov.setEstado(rs.getString("estado"));
                objMov.setUsuario(objUsu);
                arrayList.add(objMov);
            }
            
        }catch(SQLException ex){
            Logger.getLogger(Movimientos.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return arrayList;
    }
    
    public ArrayList getAllByDates(int tipoMovId, String fecha1, String fecha2){
        ArrayList arrayList = new ArrayList();
            
        try{
            
            stmt = connect.prepareCall("SELECT * FROM func_buscar_notas_fechas(?, ?, ?)");
            stmt.setInt(1, tipoMovId);
            stmt.setDate(2, Date.valueOf(fecha1));
            stmt.setDate(3, Date.valueOf(fecha2));
            rs = stmt.executeQuery();
            
            while(rs.next()){
                EUsuarios objUsu = new EUsuarios();
                objUsu.setNombres(rs.getString("usuario"));
                
                EMovimientos objMov = new EMovimientos();
                objMov.setId(rs.getInt("id"));
                objMov.setFechaRegistro(rs.getString("fecha_ingreso"));
                objMov.setSerie(rs.getString("serie"));
                objMov.setNumero(rs.getString("numero"));
                objMov.setObservaciones(rs.getString("observaciones"));
                objMov.setEstado(rs.getString("estado"));
                objMov.setUsuario(objUsu);
                arrayList.add(objMov);
            }
            
        }catch(SQLException ex){
            Logger.getLogger(Movimientos.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return arrayList;
    }
    
    public ArrayList filterNotes(int tipoMovId, String data){
        ArrayList arrayList = new ArrayList();
            
        try{
            
            stmt = connect.prepareCall("SELECT * FROM func_filtrar_notas(?, ?)");
            stmt.setInt(1, tipoMovId);
            stmt.setString(2, data);
            rs = stmt.executeQuery();
            
            while(rs.next()){
                EUsuarios objUsu = new EUsuarios();
                objUsu.setNombres(rs.getString("usuario"));
                
                EMovimientos objMov = new EMovimientos();
                objMov.setId(rs.getInt("id"));
                objMov.setFechaRegistro(rs.getString("fecha_ingreso"));
                objMov.setSerie(rs.getString("serie"));
                objMov.setNumero(rs.getString("numero"));
                objMov.setObservaciones(rs.getString("observaciones"));
                objMov.setEstado(rs.getString("estado"));
                objMov.setUsuario(objUsu);
                arrayList.add(objMov);
            }
            
        }catch(SQLException ex){
            Logger.getLogger(Movimientos.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return arrayList;
    }
    
    public int createTransact(EMovimientos objMov, ArrayList<EDetalleMovimiento> listItems) throws SQLException, Exception{
        
        int lastIsertId = 0;
        
        try{
            
            int tipoMovId = objMov.getTipoMovimiento().getId();
            
            connect.setAutoCommit(false);
            
            String queryNota = "INSERT INTO movimientos (idtipo, idusuario, fecha_registro, serie, numero, observaciones, estado) VALUES (?, ?, ?, ?, ?, ?, ?)";
            String queryDetalleNota = "INSERT INTO detallemovimiento (idmovimiento, idproducto, cantidad) VALUES(?, ?, ?)";
            String queryUpdateStock;
            if(tipoMovId == 1){
                queryUpdateStock = "UPDATE productos SET stock=stock+? WHERE id=?";
            }else{
                queryUpdateStock = "UPDATE productos SET stock=stock-? WHERE id=?";
            }
            
            PreparedStatement pstmt;
            
            pstmt = connect.prepareStatement(queryNota, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, objMov.getTipoMovimiento().getId());
            pstmt.setInt(2, objMov.getUsuario().getId());
            pstmt.setDate(3, Date.valueOf(objMov.getFechaRegistro()));
            pstmt.setString(4, objMov.getSerie());
            pstmt.setString(5, objMov.getNumero());
            pstmt.setString(6, objMov.getObservaciones());
            pstmt.setString(7, objMov.getEstado());
            pstmt.executeUpdate();
            
            rs = pstmt.getGeneratedKeys();
            if(!rs.next()){
                throw new Exception("No pudimos recuperar el id del movimiento");
            }
            
            lastIsertId = rs.getInt(1);
            
            for (EDetalleMovimiento detail : listItems) {
                pstmt = connect.prepareStatement(queryDetalleNota);
                pstmt.setInt(1, lastIsertId);
                pstmt.setInt(2, detail.getProductoId());
                pstmt.setInt(3, detail.getCantidad());
                pstmt.executeUpdate();
                
                //Actualizamos el stock de cada item
                pstmt = connect.prepareStatement(queryUpdateStock);
                pstmt.setInt(1, detail.getCantidad());
                pstmt.setInt(2, detail.getProductoId());
                pstmt.executeUpdate();
            }
            
            connect.commit();
            
        }catch(SQLException ex){
            connect.rollback();
            Logger.getLogger(Movimientos.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return lastIsertId;
    }
    
    public String anularMovimiento(EMovimientos objMov) throws SQLException, Exception{
        
        String message = "";
        
        try{
            
            int tipoMovId = objMov.getTipoMovimiento().getId();
            connect.setAutoCommit(false);
            
            String queryNota = "UPDATE movimientos SET fecha_anulado=?, motivo_anulado=?, estado=? WHERE id=?";
            String queryDetalleNota = "SELECT * FROM detallemovimiento WHERE idmovimiento=?";
            
            String queryUpdateStock;
            if(tipoMovId == 1){
                queryUpdateStock = "UPDATE productos SET stock=stock-? WHERE id=?";
            }else{
                queryUpdateStock = "UPDATE productos SET stock=stock+? WHERE id=?";
            }
            
            PreparedStatement pstmt;
            
            pstmt = connect.prepareStatement(queryNota);
            pstmt.setObject(1, objMov.getFechaAnulado());
            pstmt.setString(2, objMov.getMotivoAnulado());
            pstmt.setString(3, objMov.getEstado());
            pstmt.setInt(4, objMov.getId());
            pstmt.executeUpdate();
            
            //Traemos todos los productos del movimiento
            pstmt = connect.prepareStatement(queryDetalleNota);
            pstmt.setInt(1, objMov.getId());
            rs = pstmt.executeQuery();
            
            while(rs.next()){
                //Devolvemos el stock de cada item
                pstmt = connect.prepareStatement(queryUpdateStock);
                pstmt.setInt(1, rs.getInt("cantidad"));
                pstmt.setInt(2, rs.getInt("idproducto"));
                pstmt.executeUpdate();
            }
            
            message = "Anulado correctamente";
            
            connect.commit();
            
        }catch(SQLException ex){
            connect.rollback();
            Logger.getLogger(Movimientos.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return message;
    }
}
