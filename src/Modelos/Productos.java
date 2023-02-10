/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;

import AccesoDatos.Conexion;
import com.mxrck.autocompleter.TextAutoCompleter;
import Negocio.ECategorias;
import Negocio.ELaboratorios;
import Negocio.EMarcas;
import Negocio.EProductos;
import Negocio.EUnidadesMedida;
import Negocio.ItemAutocomplete;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Productos {
    
    Conexion objConex = new Conexion();
    CallableStatement stmt;
    ResultSet rs;
    Connection connect = null;
    
    public Productos(){
        connect = objConex.getConexion();
    }
    
    public ArrayList getAll(){
        ArrayList arrayList = new ArrayList();
        
        try{
            
            stmt = connect.prepareCall("SELECT * FROM func_listar_productos()");
            rs = stmt.executeQuery();
            
            while(rs.next()){
                EProductos objProd = new EProductos();
                
                ELaboratorios objLab = new ELaboratorios();
                ECategorias objCat = new ECategorias();
                EMarcas objMar = new EMarcas();
                EUnidadesMedida objUnd = new EUnidadesMedida();
                
                objLab.setDescripcion(rs.getString("laboratorio"));
                objCat.setDescripcion(rs.getString("categoria"));
                objMar.setDescripcion(rs.getString("marca"));
                objUnd.setDescripcion(rs.getString("undmedida"));
                
                objProd.setId(rs.getInt("id"));
                objProd.setLaboratorio(objLab);
                objProd.setCategoria(objCat);
                objProd.setMarca(objMar);
                objProd.setUndMedida(objUnd);
                objProd.setDescripcion(rs.getString("descripcion"));
                objProd.setStockMinimo(rs.getInt("stock_minimo"));
                objProd.setStock(rs.getInt("stock"));
                objProd.setFechaVto(rs.getString("fecha_vto"));
                objProd.setPrecioCompra(rs.getDouble("precio_compra"));
                objProd.setUtilidad(rs.getDouble("utilidad"));
                objProd.setPrecioVenta(rs.getDouble("precio_venta"));
                objProd.setEstado(rs.getString("estado"));
                arrayList.add(objProd);
            }
            
        }catch(SQLException ex){
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return arrayList;
    }
    
    public ArrayList searchById(int id){
        ArrayList arrayList = new ArrayList();
        
        try{
            
            stmt = connect.prepareCall("SELECT * FROM func_buscar_producto(?)");
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            
            if(rs.next()){
                EProductos objProd = new EProductos();
                
                ELaboratorios objLab = new ELaboratorios();
                ECategorias objCat = new ECategorias();
                EMarcas objMar = new EMarcas();
                EUnidadesMedida objUnd = new EUnidadesMedida();
                
                objLab.setId(rs.getInt("idlaboratorio"));
                objCat.setId(rs.getInt("idcategoria"));
                objMar.setId(rs.getInt("idmarca"));
                objUnd.setId(rs.getInt("idunidad"));
                
                objProd.setId(rs.getInt("id"));
                objProd.setLaboratorio(objLab);
                objProd.setCategoria(objCat);
                objProd.setMarca(objMar);
                objProd.setUndMedida(objUnd);
                objProd.setDescripcion(rs.getString("descripcion"));
                objProd.setStockMinimo(rs.getInt("stock_minimo"));
                objProd.setStockMaximo(rs.getInt("stock_maximo"));
                objProd.setStock(rs.getInt("stock"));
                objProd.setFechaVto(rs.getString("fecha_vto"));
                objProd.setPrecioCompra(rs.getDouble("precio_compra"));
                objProd.setUtilidad(rs.getDouble("utilidad"));
                objProd.setPrecioVenta(rs.getDouble("precio_venta"));
                objProd.setEstado(rs.getString("estado"));
                arrayList.add(objProd);
            }
            
        }catch(SQLException ex){
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return arrayList;
    }
    
    public ArrayList searchByName(String name){
        ArrayList arrayList = new ArrayList();
        
        try{
            
            stmt = connect.prepareCall("SELECT * FROM func_buscar_producto_nombre(?)");
            stmt.setString(1, name);
            rs = stmt.executeQuery();
            
            while(rs.next()){
                EProductos objProd = new EProductos();
                
                ELaboratorios objLab = new ELaboratorios();
                ECategorias objCat = new ECategorias();
                EMarcas objMar = new EMarcas();
                EUnidadesMedida objUnd = new EUnidadesMedida();
                
                objLab.setDescripcion(rs.getString("laboratorio"));
                objCat.setDescripcion(rs.getString("categoria"));
                objMar.setDescripcion(rs.getString("marca"));
                objUnd.setDescripcion(rs.getString("undmedida"));
                
                objProd.setId(rs.getInt("id"));
                objProd.setLaboratorio(objLab);
                objProd.setCategoria(objCat);
                objProd.setMarca(objMar);
                objProd.setUndMedida(objUnd);
                objProd.setDescripcion(rs.getString("descripcion"));
                objProd.setStockMinimo(rs.getInt("stock_minimo"));
                objProd.setStock(rs.getInt("stock"));
                objProd.setFechaVto(rs.getString("fecha_vto"));
                objProd.setPrecioCompra(rs.getDouble("precio_compra"));
                objProd.setUtilidad(rs.getDouble("utilidad"));
                objProd.setPrecioVenta(rs.getDouble("precio_venta"));
                objProd.setEstado(rs.getString("estado"));
                arrayList.add(objProd);
            }
            
        }catch(SQLException ex){
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return arrayList;
    }
    
    public String create(EProductos objProducto){
        String message = "";
        
        try{
            
            stmt = connect.prepareCall("call sp_registrar_producto(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            stmt.setInt(1, objProducto.getLaboratorio().getId());
            stmt.setInt(2, objProducto.getCategoria().getId());
            stmt.setInt(3, objProducto.getMarca().getId());
            stmt.setInt(4, objProducto.getUndMedida().getId());
            stmt.setString(5, objProducto.getDescripcion());
            stmt.setInt(6, objProducto.getStockMinimo());
            stmt.setInt(7, objProducto.getStockMaximo());
            stmt.setInt(8, objProducto.getStock());
            stmt.setDate(9, Date.valueOf(objProducto.getFechaVto()));
            stmt.setBigDecimal(10, new BigDecimal(objProducto.getPrecioCompra()));
            stmt.setBigDecimal(11, new BigDecimal(objProducto.getUtilidad()));
            stmt.setBigDecimal(12, new BigDecimal(objProducto.getPrecioVenta()));
            stmt.setString(13, objProducto.getEstado());
            stmt.registerOutParameter(14, Types.VARCHAR, 100);
            stmt.executeUpdate();
            message = stmt.getString(14);
                    
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return message;
    }
    
    public String update(EProductos objProducto){
        String message = "";
        
        try{
            
            stmt = connect.prepareCall("call sp_actualizar_producto(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            stmt.setInt(1, objProducto.getId());
            stmt.setInt(2, objProducto.getLaboratorio().getId());
            stmt.setInt(3, objProducto.getCategoria().getId());
            stmt.setInt(4, objProducto.getMarca().getId());
            stmt.setInt(5, objProducto.getUndMedida().getId());
            stmt.setString(6, objProducto.getDescripcion());
            stmt.setInt(7, objProducto.getStockMinimo());
            stmt.setInt(8, objProducto.getStockMaximo());
            stmt.setInt(9, objProducto.getStock());
            stmt.setDate(10, Date.valueOf(objProducto.getFechaVto()));
            stmt.setBigDecimal(11, new BigDecimal(objProducto.getPrecioCompra()));
            stmt.setBigDecimal(12, new BigDecimal(objProducto.getUtilidad()));
            stmt.setBigDecimal(13, new BigDecimal(objProducto.getPrecioVenta()));
            stmt.setString(14, objProducto.getEstado());
            stmt.registerOutParameter(15, Types.VARCHAR, 100);
            stmt.executeUpdate();
            message = stmt.getString(15);
                    
        }catch(SQLException ex){
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return message;
    }
    
    public void loadAutocomplete(TextAutoCompleter completador){
        try{
            
            stmt = connect.prepareCall("SELECT * FROM func_listar_productos()");
            rs = stmt.executeQuery();
            
            while(rs.next()){
                completador.addItem(new ItemAutocomplete(rs.getInt("id"), rs.getString("descripcion")));
            }
            
        }catch(SQLException ex){
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public ArrayList searchByIdToCart(int id){
        ArrayList arrayList = new ArrayList();        
        
        try{
            
            stmt = connect.prepareCall("SELECT * FROM func_buscar_producto_carrito(?)");
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            
            if(rs.next()){
                EProductos objProd = new EProductos();
                
                EMarcas objMar = new EMarcas();
                EUnidadesMedida objUnd = new EUnidadesMedida();
                
                objMar.setDescripcion(rs.getString("marca"));
                objUnd.setDescripcion(rs.getString("undmedida"));
                
                objProd.setId(rs.getInt("id"));
                objProd.setMarca(objMar);
                objProd.setUndMedida(objUnd);
                objProd.setDescripcion(rs.getString("descripcion"));
                objProd.setStockMinimo(rs.getInt("stock_minimo"));
                objProd.setStock(rs.getInt("stock"));
                objProd.setFechaVto(rs.getString("fecha_vto"));
                objProd.setPrecioCompra(rs.getDouble("precio_compra"));
                objProd.setUtilidad(rs.getDouble("utilidad"));
                objProd.setPrecioVenta(rs.getDouble("precio_venta"));
                objProd.setEstado(rs.getString("estado"));
                arrayList.add(objProd);
            }
            
        }catch(SQLException ex){
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return arrayList;
    }
}
